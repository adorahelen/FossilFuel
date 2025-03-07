package dcu.fossilfuel.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalRequestLoggingFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(GlobalRequestLoggingFilter.class);

    // 더 정밀한 SQL Injection 패턴 (단어 경계 추가)
    private static final Pattern SUSPICIOUS_PATTERN = Pattern.compile("(?i)\\b(union|select|drop|--|;|<|>)\\b");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String clientIP = req.getHeader("X-Forwarded-For");
        if (clientIP == null || clientIP.isEmpty()) {
            clientIP = req.getRemoteAddr();
        }

        int port = req.getLocalPort();
        String protocol = req.getScheme();
        String requestURI = req.getRequestURI();
        String method = req.getMethod();
        String userAgent = req.getHeader("User-Agent");
        String queryString = req.getQueryString(); // 쿼리 파라미터 검사용

        // ✅ ELB Health Check 요청 제외 (userAgent가 null일 가능성 대비)
        if (userAgent != null && userAgent.equalsIgnoreCase("ELB-HealthChecker/2.0") && "/".equals(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        long startTime = System.currentTimeMillis();

        // 🔍 의심스러운 패턴 검사 (쿼리 파라미터도 포함)
        if (SUSPICIOUS_PATTERN.matcher(requestURI).find() ||
                (queryString != null && SUSPICIOUS_PATTERN.matcher(queryString).find())) {
            log.warn("🚨 Potential attack attempt from IP: {} - {} {} - Query: {} - Port: {} - Protocol: {}",
                    clientIP, method, requestURI, queryString, port, protocol);
        }

        // 🔍 특정 URI 패턴 로그 (민감한 경로 접근 감지)
        if (requestURI.contains("sensitive-path")) {
            log.warn("⚠️ Sensitive path accessed from IP: {} - {} {} - Port: {} - Protocol: {}",
                    clientIP, method, requestURI, port, protocol);
        }

        chain.doFilter(request, response);
        long responseTime = System.currentTimeMillis() - startTime;

        int statusCode = resp.getStatus();
        log.info("📌 Request from IP: {} - {} {} - User-Agent: {} - Time: {}ms - Status: {} - Port: {} - Protocol: {}",
                clientIP, method, requestURI, userAgent, responseTime, statusCode, port, protocol);
    }
}
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
    private static final Pattern SUSPICIOUS_PATTERN = Pattern.compile("(?i)(union|select|drop|--|;|<|>)");


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 클라이언트 IP 가져오기
        String clientIP = req.getHeader("X-Forwarded-For");
        if (clientIP == null || clientIP.isEmpty()) {
            clientIP = req.getRemoteAddr();
        }

        // 포트 번호와 프로토콜 (HTTP/HTTPS)
        int port = req.getLocalPort();  // 클라이언트가 요청을 보낸 포트 번호
        String protocol = req.getScheme(); // 요청한 프로토콜 (HTTP/HTTPS)

        // 요청 URI, HTTP 메서드 및 사용자 에이전트
        String requestURI = req.getRequestURI();
        String method = req.getMethod();
        String userAgent = req.getHeader("User-Agent");

        // 요청 시간 기록
        long requestTime = System.currentTimeMillis();

        // 의심스러운 패턴 검사
        if (SUSPICIOUS_PATTERN.matcher(requestURI).find()) {
            log.warn("Potential attack attempt from IP: {} - {} {} - Port: {} - Protocol: {} - Time: {}",
                    clientIP, method, requestURI, port, protocol, requestTime);
        }

        // 특정 URI 패턴 로그 남기기
        if (requestURI.contains("sensitive-path")) {
            log.warn("Sensitive path accessed from IP: {} - {} {} - Port: {} - Protocol: {} - Time: {}",
                    clientIP, method, requestURI, port, protocol, requestTime);
        }

        // 요청 처리 후 응답 코드 기록
        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        long responseTime = System.currentTimeMillis() - startTime;

        int statusCode = resp.getStatus();
        log.info("Request from IP: {} - {} {} - User-Agent: {} - Time: {}ms - Status: {} - Port: {} - Protocol: {}",
                clientIP, method, requestURI, userAgent, responseTime, statusCode, port, protocol);
    }
}
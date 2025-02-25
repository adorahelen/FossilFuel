package dcu.fossilfuel.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

// 클라이언트 IP 까지 따와서 로깅을 남김
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalRequestLoggingFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(GlobalRequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String clientIP = req.getRemoteAddr(); // 클라이언트 IP 가져오기
        log.info("Request from IP: {} - {} {}", clientIP, req.getMethod(), req.getRequestURI());
        chain.doFilter(request, response);
    }
}
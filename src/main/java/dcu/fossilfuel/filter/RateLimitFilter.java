package dcu.fossilfuel.filter;

import io.github.bucket4j.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.Duration;

@Component
public class RateLimitFilter implements Filter {

    // 핵심 동작: 1분에 100개의 요청만 허용하고, 초과 시 429 상태 코드를 반환합니다.
    // HttpServletResponse.SC_TOO_MANY_REQUESTS 대신 429 직접 사용.
    // RateLimitFilter → Bucket4J로 완전 전환 (API 요청 제한)

    private final Bucket bucket;

    public RateLimitFilter() {
        Bandwidth limit = Bandwidth.classic(100, Refill.intervally(100, Duration.ofMinutes(1))); // 1분에 100개 요청 허용
        this.bucket = Bucket.builder().addLimit(limit).build();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(429); // 🚀 오류 해결: 상수 올바르게 사용
            res.getWriter().write("Too Many Requests - Please try again later.");
        }
    }
}
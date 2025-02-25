package dcu.fossilfuel;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

public class RateLimitFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 로직
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 요청 처리 로직
        // 예: 요청 수 제한 로직 구현
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 종료 로직
    }
}
// LateLimit의 경우 더이상 지원되지 않기에 ->  Bucket4j 로 완전히 전환하기
/*

Refill : 일정 시간마다 충전할 Token의 개수 지정
Bandwidth : Bucket의 총 크기를 지정
Bucket : 실제 트래픽 제어에 사용

@RestController
public class Controller {

  private final Bucket bucket;

  public Controller() {
    // 충전 간격을 10초로 지정하며, 한 번 충전할 때마다 2개의 토큰을 충전한다.
    final Refill refill = Refill.intervally(2, Duration.ofSeconds(10));

    // Bucket의 총 크기는 3으로 지정
    final Bandwidth limit = Bandwidth.classic(3, refill);

    // 총 크기는 3이며 10초마다 2개의 토큰을 충전하는 Bucket
    this.bucket = Bucket.builder()
        .addLimit(limit)
        .build();

  }

  @GetMapping(value = "/api/test")
  public ResponseEntity<String> test() {

  	// API 호출시 토큰 1개를 소비
    if (bucket.tryConsume(1)) {
      return ResponseEntity.ok("success!");
    }

    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
  }

}

 */

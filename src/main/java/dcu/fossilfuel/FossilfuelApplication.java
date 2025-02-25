package dcu.fossilfuel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FossilfuelApplication {

    public static void main(String[] args) {
        SpringApplication.run(FossilfuelApplication.class, args);
    }

    // API 사용량 제한 (Rate Limiting)
    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilter() {
        FilterRegistrationBean<RateLimitFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RateLimitFilter());
        return registrationBean;
    }
}

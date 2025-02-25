package dcu.fossilfuel.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler; // 🔥 커스텀 로그인 실패 핸들러

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/img/**", "/css/**", "/js/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()) // 기본 HTTP 인증 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/signup", "/chatbot", "/api/**", "/error", "/login", "/login/**",
                                "/api/commits/**", "/graphql/**"
                        ).permitAll()  // 특정 URL은 인증 없이 접근 가능
                        .anyRequest().authenticated()
                )

                // ✅ HTTPS 강제 (HSTS 적용)
                .headers(headers -> headers
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .preload(true)
                                .maxAgeInSeconds(31536000)  // 1년 (1년 동안 HTTPS 강제)
                        )
                        .frameOptions(frame -> frame.sameOrigin())  // iframe 보안 설정 (필요한 경우)
                )

                // ✅ 로그인 설정
                .formLogin(login -> login
                        .loginPage("/login")  // 사용자 정의 로그인 페이지
                        .loginProcessingUrl("/api/auth/login") // 로그인 요청 URL
                        .defaultSuccessUrl("/dashboard", true) // 로그인 성공 후 리디렉션
                        .failureHandler(customAuthenticationFailureHandler) // 🔥 커스텀 핸들러 등록
                        .permitAll()
                )

                // ✅ 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessUrl("/login?logout=true") // 로그아웃 후 리디렉션
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                // ✅ 세션 정책 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        return http.build();
    }

    // ❗ AuthenticationManager 자동 주입 활용
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

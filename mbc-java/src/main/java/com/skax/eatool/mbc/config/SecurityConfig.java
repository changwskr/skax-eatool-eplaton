package com.skax.eatool.mbc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Spring Security Configuration
 * 
 * @author SKAX Project Team
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("=== SecurityConfig.filterChain START ===");
        
        try {
            http
                    .authorizeHttpRequests(authorize -> authorize
                            // 공개 접근 가능한 경로
                            .antMatchers("/", "/home", "/mbc").permitAll()
                            .antMatchers("/mbc/eplaton/**").permitAll() // EPlaton API 허용
                            .antMatchers("/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                            .antMatchers("/h2-console/**").permitAll()
                            .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                            .antMatchers("/actuator/**").permitAll()
                            // API 엔드포인트
                            .antMatchers("/api/**").permitAll()
                            // 기타 모든 요청은 허용 (개발 환경)
                            .anyRequest().permitAll()
                    )
                    .csrf(csrf -> csrf
                            .ignoringAntMatchers("/h2-console/**")
                            .ignoringAntMatchers("/mbc/eplaton/**") // EPlaton API CSRF 제외
                            .ignoringAntMatchers("/api/**")
                    )
                    .headers(headers -> headers
                            .frameOptions().disable() // For H2 console
                    )
                    .formLogin(form -> form.disable()) // 로그인 페이지 비활성화
                    .httpBasic(basic -> basic.disable()); // HTTP Basic 인증 비활성화
            
            logger.info("=== SecurityConfig.filterChain END ===");
            return http.build();
            
        } catch (Exception e) {
            logger.error("SecurityConfig 설정 중 오류 발생: " + e.getMessage(), e);
            throw e;
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("=== SecurityConfig.passwordEncoder START ===");
        logger.info("=== SecurityConfig.passwordEncoder END ===");
        return new BCryptPasswordEncoder();
    }
}
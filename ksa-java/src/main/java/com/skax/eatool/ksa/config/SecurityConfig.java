package com.skax.eatool.ksa.config;

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
 * KSA Spring Security Configuration
 * 
 * Gateway 환경에서 API 서비스로 사용되므로 모든 요청을 허용합니다.
 * 
 * @author SKAX Project Team
 * @version 1.0.0
 * @since 2024
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("=== KSA SecurityConfig.filterChain START ===");
        
        try {
            http
                    .authorizeHttpRequests(authorize -> authorize
                            .anyRequest().permitAll() // 모든 요청 허용 (API 서비스)
                    )
                    .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                    .headers(headers -> headers
                            .frameOptions().disable() // H2 콘솔 지원
                    )
                    .formLogin(form -> form.disable()) // 로그인 페이지 비활성화
                    .httpBasic(basic -> basic.disable()); // HTTP Basic 인증 비활성화
            
            logger.info("=== KSA SecurityConfig.filterChain END ===");
            return http.build();
            
        } catch (Exception e) {
            logger.error("KSA SecurityConfig 설정 중 오류 발생: " + e.getMessage(), e);
            throw e;
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("=== KSA SecurityConfig.passwordEncoder START ===");
        logger.info("=== KSA SecurityConfig.passwordEncoder END ===");
        return new BCryptPasswordEncoder();
    }
} 
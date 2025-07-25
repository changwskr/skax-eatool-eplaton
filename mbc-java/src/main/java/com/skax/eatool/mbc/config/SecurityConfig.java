package com.skax.eatool.mbc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
    
    // JWT 필터는 개발 환경에서 비활성화
    // @Autowired
    // private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("=== SecurityConfig.filterChain START ===");
        
        try {
            http
                    .authorizeHttpRequests(authorize -> authorize
                            // 모든 요청 허용 (개발 환경용)
                            .anyRequest().permitAll()
                    )
                    .csrf(csrf -> csrf.disable())
                    .headers(headers -> headers
                            .frameOptions().disable() // For H2 console
                    )
                    .formLogin(form -> form.disable())
                    .httpBasic(basic -> basic.disable());
            
            logger.info("=== SecurityConfig.filterChain END - 모든 요청 허용 (개발 환경) ===");
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
package com.skax.eatool.kji.config;

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
 * KJI Security Configuration
 * 
 * KJI 서비스의 보안 설정을 관리합니다.
 * 
 * @author EPlaton Framework Team
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("==================[KJI SecurityConfig.filterChain START]");
        
        try {
            http
                .authorizeHttpRequests(authorize -> authorize
                    // 공개 접근 가능한 경로
                    .antMatchers("/", "/home").permitAll()
                    .antMatchers("/api/**").permitAll() // API 엔드포인트 허용
                    .antMatchers("/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .antMatchers("/actuator/**").permitAll()
                    // 모든 요청 허용 (개발 환경)
                    .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf
                    .ignoringAntMatchers("/h2-console/**")
                    .ignoringAntMatchers("/api/**")
                    .ignoringAntMatchers("/kji/tpm/**") // KJI TPM API CSRF 제외
                )
                .headers(headers -> headers
                    .frameOptions().sameOrigin() // H2 콘솔을 위한 설정
                )
                .formLogin(form -> form.disable()) // 로그인 페이지 비활성화
                .httpBasic(basic -> basic.disable()); // HTTP Basic 인증 비활성화
            
            logger.info("==================[KJI SecurityConfig.filterChain END] - 설정 완료");
            return http.build();
            
        } catch (Exception e) {
            logger.error("==================[KJI SecurityConfig.filterChain ERROR] - {}", e.getMessage(), e);
            throw e;
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("==================[KJI SecurityConfig.passwordEncoder] - BCryptPasswordEncoder 생성");
        return new BCryptPasswordEncoder();
    }
} 
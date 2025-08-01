package com.skax.eatool.mbb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // 개발 환경에서는 모든 요청 허용
                .anyRequest().permitAll()
            .and()
                .csrf().disable() // CSRF 비활성화
                .headers().frameOptions().disable() // H2 콘솔용
            .and()
                .formLogin().disable() // 폼 로그인 비활성화
                .httpBasic().disable(); // HTTP Basic 인증 비활성화

        return http.build();
    }
} 
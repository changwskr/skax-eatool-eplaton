package com.skax.eatool.mba.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * MBA 보안 설정 클래스
 * 
 * Spring Security 설정을 관리합니다.
 * 
 * @author KBSTAR
 * @version 1.0.0
 * @since 2024
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * HTTP 보안 설정
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // 공개 접근 가능한 경로
                .antMatchers("/", "/home", "/auth/login", "/register").permitAll()
                .antMatchers("/mba/login").permitAll() // 로그인 처리 URL 허용
                .antMatchers("/mba/auth/login").permitAll() // Gateway를 통한 로그인 페이지 접근 허용
                .antMatchers("/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                // API 엔드포인트
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/**").authenticated()
                // 기타 모든 요청은 인증 필요
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/mba/login")
                .defaultSuccessUrl("/home")
                .failureUrl("/auth/login?error=true")
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            .and()
            .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .ignoringAntMatchers("/mba/api/**")
            .and()
            .headers()
                .frameOptions()
                .sameOrigin(); // H2 콘솔을 위한 설정
    }

    /**
     * 인증 관리자 설정
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    /**
     * 비밀번호 인코더 빈 설정
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 
package com.skax.eatool.mba.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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

    /**
     * HTTP 보안 설정
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // 공개 접근 가능한 경로
                .antMatchers("/", "/mba/home", "/mba/login", "/mba/register").permitAll()
                .antMatchers("/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                // API 엔드포인트
                .antMatchers("/mba/api/public/**").permitAll()
                .antMatchers("/mba/api/admin/**").hasRole("ADMIN")
                .antMatchers("/mba/api/**").authenticated()
                // 기타 모든 요청은 인증 필요
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/mba/login")
                .loginProcessingUrl("/mba/login")
                .defaultSuccessUrl("/mba/dashboard")
                .failureUrl("/mba/login?error=true")
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/mba/logout")
                .logoutSuccessUrl("/mba/home")
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
     * 비밀번호 인코더 빈 설정
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 
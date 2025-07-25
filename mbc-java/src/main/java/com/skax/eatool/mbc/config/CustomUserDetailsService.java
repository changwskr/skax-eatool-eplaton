package com.skax.eatool.mbc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 개발 환경용 UserDetailsService
 * 
 * JWT 토큰 기반 인증을 위한 사용자 정보를 제공합니다.
 * 
 * @author SKAX Project Team
 * @version 1.0.0
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("사용자 정보 로드: {}", username);
        
        // 개발 환경에서는 기본 사용자 정보 반환
        if ("dev-user".equals(username)) {
            return User.builder()
                    .username("dev-user")
                    .password("$2a$10$dummy.password.hash") // 실제로는 사용되지 않음
                    .authorities(Arrays.asList(
                            new SimpleGrantedAuthority("ROLE_USER"),
                            new SimpleGrantedAuthority("ROLE_API_USER")
                    ))
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        }
        
        // 알 수 없는 사용자
        logger.warn("알 수 없는 사용자: {}", username);
        throw new UsernameNotFoundException("User not found: " + username);
    }
} 
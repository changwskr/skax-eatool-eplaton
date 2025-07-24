package com.skax.eatool.mba.config;

import com.skax.eatool.mba.dc.usermgmtdc.entity.UserEntity;
import com.skax.eatool.mba.dc.usermgmtdc.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Spring Security 사용자 인증을 위한 커스텀 UserDetailsService
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("=== CustomUserDetailsService.loadUserByUsername START ===");
        logger.info("인증 요청 사용자명: {}", username);
        
        try {
            // 사용자 조회
            UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("사용자를 찾을 수 없습니다: {}", username);
                    return new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
                });
            
            // 사용자 상태 확인
            if (!"ACTIVE".equals(userEntity.getStatus())) {
                logger.error("비활성화된 사용자입니다: {}", username);
                throw new UsernameNotFoundException("비활성화된 사용자입니다: " + username);
            }
            
            // 권한 설정
            String role = userEntity.getRole() != null ? userEntity.getRole() : "USER";
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
            
            // UserDetails 객체 생성
            UserDetails userDetails = User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(Collections.singletonList(authority))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
            
            logger.info("인증 성공 - 사용자명: {}, 역할: {}", username, role);
            logger.info("=== CustomUserDetailsService.loadUserByUsername END ===");
            
            return userDetails;
            
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("사용자 인증 중 오류 발생: {}", e.getMessage(), e);
            throw new UsernameNotFoundException("사용자 인증 중 오류가 발생했습니다: " + username, e);
        }
    }
} 
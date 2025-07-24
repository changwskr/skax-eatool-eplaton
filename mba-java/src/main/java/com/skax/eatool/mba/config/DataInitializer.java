package com.skax.eatool.mba.config;

import com.skax.eatool.mba.dc.usermgmtdc.entity.UserEntity;
import com.skax.eatool.mba.dc.usermgmtdc.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

/**
 * 애플리케이션 시작 시 기본 데이터를 초기화하는 클래스
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("=== DataInitializer.run START ===");
        initializeUsers();
        logger.info("=== DataInitializer.run END ===");
    }
    
    /**
     * 기본 사용자 데이터 초기화
     */
    private void initializeUsers() {
        logger.info("기본 사용자 데이터 초기화 시작...");
        
        try {
            // admin 사용자 생성
            if (!userRepository.existsByUsername("admin")) {
                UserEntity adminUser = new UserEntity();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin123"));
                adminUser.setEmail("admin@skax.com");
                adminUser.setFullName("시스템 관리자");
                adminUser.setRole("ADMIN");
                adminUser.setStatus("ACTIVE");
                adminUser.setCreatedAt(LocalDateTime.now());
                adminUser.setUpdatedAt(LocalDateTime.now());
                
                userRepository.save(adminUser);
                logger.info("admin 사용자 생성 완료");
            } else {
                logger.info("admin 사용자가 이미 존재합니다.");
            }
            
            // manager 사용자 생성
            if (!userRepository.existsByUsername("manager")) {
                UserEntity managerUser = new UserEntity();
                managerUser.setUsername("manager");
                managerUser.setPassword(passwordEncoder.encode("admin123"));
                managerUser.setEmail("manager@skax.com");
                managerUser.setFullName("매니저");
                managerUser.setRole("MANAGER");
                managerUser.setStatus("ACTIVE");
                managerUser.setCreatedAt(LocalDateTime.now());
                managerUser.setUpdatedAt(LocalDateTime.now());
                
                userRepository.save(managerUser);
                logger.info("manager 사용자 생성 완료");
            } else {
                logger.info("manager 사용자가 이미 존재합니다.");
            }
            
            // user 사용자 생성
            if (!userRepository.existsByUsername("user")) {
                UserEntity userUser = new UserEntity();
                userUser.setUsername("user");
                userUser.setPassword(passwordEncoder.encode("admin123"));
                userUser.setEmail("user@skax.com");
                userUser.setFullName("일반 사용자");
                userUser.setRole("USER");
                userUser.setStatus("ACTIVE");
                userUser.setCreatedAt(LocalDateTime.now());
                userUser.setUpdatedAt(LocalDateTime.now());
                
                userRepository.save(userUser);
                logger.info("user 사용자 생성 완료");
            } else {
                logger.info("user 사용자가 이미 존재합니다.");
            }
            
            logger.info("기본 사용자 데이터 초기화 완료");
            
        } catch (Exception e) {
            logger.error("기본 사용자 데이터 초기화 중 오류 발생: {}", e.getMessage(), e);
        }
    }
} 
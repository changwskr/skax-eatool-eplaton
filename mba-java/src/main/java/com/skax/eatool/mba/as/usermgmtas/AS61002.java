package com.skax.eatool.mba.as.usermgmtas;

import com.skax.eatool.mba.as.usermgmtas.dto.ApiResponseDto;
import com.skax.eatool.mba.as.usermgmtas.dto.UserDto;
import com.skax.eatool.mba.pc.usermgmtpc.UserPC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 사용자 상세 조회 이벤트 서비스
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class AS61002 {
    
    private static final Logger logger = LoggerFactory.getLogger(AS61002.class);
    
    @Autowired
    private UserPC userPC;
    
    /**
     * 사용자 ID로 상세 조회 이벤트 처리
     * 
     * @param userId 사용자 ID
     * @return 사용자 상세 정보 응답
     */
    public ApiResponseDto<UserDto> getUserById(Long userId) {
        logger.info("=== AS61002.getUserById START ===");
        logger.info("조회할 사용자 ID: {}", userId);
        
        try {
            // 입력 검증
            if (userId == null || userId <= 0) {
                logger.error("유효하지 않은 사용자 ID: {}", userId);
                return ApiResponseDto.error("400", "유효한 사용자 ID가 필요합니다.");
            }
            
            // PC 호출하여 사용자 상세 조회
            UserDto user = userPC.getUserById(userId);
            
            if (user == null) {
                logger.warn("사용자를 찾을 수 없습니다. ID: {}", userId);
                return ApiResponseDto.error("404", "사용자를 찾을 수 없습니다.");
            }
            
            logger.info("조회 성공 - 사용자명: {}", user.getUsername());
            logger.info("=== AS61002.getUserById END ===");
            
            return ApiResponseDto.success(user, "사용자 상세 조회가 완료되었습니다.");
            
        } catch (Exception e) {
            logger.error("AS61002.getUserById 중 오류 발생: {}", e.getMessage(), e);
            return ApiResponseDto.error("500", "사용자 상세 조회 중 오류가 발생했습니다.", e.getMessage());
        }
    }
    
    /**
     * 사용자명으로 상세 조회 이벤트 처리
     * 
     * @param username 사용자명
     * @return 사용자 상세 정보 응답
     */
    public ApiResponseDto<UserDto> getUserByUsername(String username) {
        logger.info("=== AS61002.getUserByUsername START ===");
        logger.info("조회할 사용자명: {}", username);
        
        try {
            // 입력 검증
            if (username == null || username.trim().isEmpty()) {
                logger.error("사용자명이 비어있습니다.");
                return ApiResponseDto.error("400", "사용자명이 필요합니다.");
            }
            
            // PC 호출하여 사용자 상세 조회
            UserDto user = userPC.getUserByUsername(username);
            
            if (user == null) {
                logger.warn("사용자를 찾을 수 없습니다. 사용자명: {}", username);
                return ApiResponseDto.error("404", "사용자를 찾을 수 없습니다.");
            }
            
            logger.info("조회 성공 - 사용자명: {}", user.getUsername());
            logger.info("=== AS61002.getUserByUsername END ===");
            
            return ApiResponseDto.success(user, "사용자 상세 조회가 완료되었습니다.");
            
        } catch (Exception e) {
            logger.error("AS61002.getUserByUsername 중 오류 발생: {}", e.getMessage(), e);
            return ApiResponseDto.error("500", "사용자 상세 조회 중 오류가 발생했습니다.", e.getMessage());
        }
    }
    
    /**
     * 이메일로 상세 조회 이벤트 처리
     * 
     * @param email 이메일
     * @return 사용자 상세 정보 응답
     */
    public ApiResponseDto<UserDto> getUserByEmail(String email) {
        logger.info("=== AS61002.getUserByEmail START ===");
        logger.info("조회할 이메일: {}", email);
        
        try {
            // 입력 검증
            if (email == null || email.trim().isEmpty()) {
                logger.error("이메일이 비어있습니다.");
                return ApiResponseDto.error("400", "이메일이 필요합니다.");
            }
            
            // PC 호출하여 사용자 상세 조회
            UserDto user = userPC.getUserByEmail(email);
            
            if (user == null) {
                logger.warn("사용자를 찾을 수 없습니다. 이메일: {}", email);
                return ApiResponseDto.error("404", "사용자를 찾을 수 없습니다.");
            }
            
            logger.info("조회 성공 - 사용자명: {}", user.getUsername());
            logger.info("=== AS61002.getUserByEmail END ===");
            
            return ApiResponseDto.success(user, "사용자 상세 조회가 완료되었습니다.");
            
        } catch (Exception e) {
            logger.error("AS61002.getUserByEmail 중 오류 발생: {}", e.getMessage(), e);
            return ApiResponseDto.error("500", "사용자 상세 조회 중 오류가 발생했습니다.", e.getMessage());
        }
    }
} 
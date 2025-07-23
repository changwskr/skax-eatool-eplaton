package com.skax.eatool.mba.as.usermgmtas;

import com.skax.eatool.mba.as.usermgmtas.dto.ApiResponseDto;
import com.skax.eatool.mba.as.usermgmtas.dto.CreateUserRequestDto;
import com.skax.eatool.mba.as.usermgmtas.dto.UpdateUserRequestDto;
import com.skax.eatool.mba.as.usermgmtas.dto.UserDto;
import com.skax.eatool.mba.pc.usermgmtpc.UserPC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 사용자 생성/수정/삭제 이벤트 서비스
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class AS61003 {
    
    private static final Logger logger = LoggerFactory.getLogger(AS61003.class);
    
    @Autowired
    private UserPC userPC;
    
    /**
     * 사용자 생성 이벤트 처리
     * 
     * @param createRequest 사용자 생성 요청
     * @return 생성된 사용자 정보 응답
     */
    public ApiResponseDto<UserDto> createUser(CreateUserRequestDto createRequest) {
        logger.info("=== AS61003.createUser START ===");
        logger.info("생성 요청: {}", createRequest);
        
        try {
            // 입력 검증
            if (createRequest == null) {
                logger.error("생성 요청이 NULL입니다.");
                return ApiResponseDto.error("400", "사용자 생성 정보가 필요합니다.");
            }
            
            if (createRequest.getUsername() == null || createRequest.getUsername().trim().isEmpty()) {
                logger.error("사용자명이 비어있습니다.");
                return ApiResponseDto.error("400", "사용자명이 필요합니다.");
            }
            
            if (createRequest.getEmail() == null || createRequest.getEmail().trim().isEmpty()) {
                logger.error("이메일이 비어있습니다.");
                return ApiResponseDto.error("400", "이메일이 필요합니다.");
            }
            
            if (createRequest.getPassword() == null || createRequest.getPassword().trim().isEmpty()) {
                logger.error("비밀번호가 비어있습니다.");
                return ApiResponseDto.error("400", "비밀번호가 필요합니다.");
            }
            
            // PC 호출하여 사용자 생성
            UserDto createdUser = userPC.createUser(createRequest);
            
            if (createdUser == null) {
                logger.error("사용자 생성에 실패했습니다.");
                return ApiResponseDto.error("500", "사용자 생성에 실패했습니다.");
            }
            
            logger.info("사용자 생성 성공 - ID: {}, 사용자명: {}", 
                createdUser.getId(), createdUser.getUsername());
            logger.info("=== AS61003.createUser END ===");
            
            return ApiResponseDto.success(createdUser, "사용자가 성공적으로 생성되었습니다.");
            
        } catch (Exception e) {
            logger.error("AS61003.createUser 중 오류 발생: {}", e.getMessage(), e);
            return ApiResponseDto.error("500", "사용자 생성 중 오류가 발생했습니다.", e.getMessage());
        }
    }
    
    /**
     * 사용자 수정 이벤트 처리
     * 
     * @param userId 사용자 ID
     * @param updateRequest 사용자 수정 요청
     * @return 수정된 사용자 정보 응답
     */
    public ApiResponseDto<UserDto> updateUser(Long userId, UpdateUserRequestDto updateRequest) {
        logger.info("=== AS61003.updateUser START ===");
        logger.info("수정할 사용자 ID: {}", userId);
        logger.info("수정 요청: {}", updateRequest);
        
        try {
            // 입력 검증
            if (userId == null || userId <= 0) {
                logger.error("유효하지 않은 사용자 ID: {}", userId);
                return ApiResponseDto.error("400", "유효한 사용자 ID가 필요합니다.");
            }
            
            if (updateRequest == null) {
                logger.error("수정 요청이 NULL입니다.");
                return ApiResponseDto.error("400", "사용자 수정 정보가 필요합니다.");
            }
            
            // PC 호출하여 사용자 수정
            UserDto updatedUser = userPC.updateUser(userId, updateRequest);
            
            if (updatedUser == null) {
                logger.warn("수정할 사용자를 찾을 수 없습니다. ID: {}", userId);
                return ApiResponseDto.error("404", "수정할 사용자를 찾을 수 없습니다.");
            }
            
            logger.info("사용자 수정 성공 - ID: {}, 사용자명: {}", 
                updatedUser.getId(), updatedUser.getUsername());
            logger.info("=== AS61003.updateUser END ===");
            
            return ApiResponseDto.success(updatedUser, "사용자가 성공적으로 수정되었습니다.");
            
        } catch (Exception e) {
            logger.error("AS61003.updateUser 중 오류 발생: {}", e.getMessage(), e);
            return ApiResponseDto.error("500", "사용자 수정 중 오류가 발생했습니다.", e.getMessage());
        }
    }
    
    /**
     * 사용자 삭제 이벤트 처리
     * 
     * @param userId 사용자 ID
     * @return 삭제 결과 응답
     */
    public ApiResponseDto<Boolean> deleteUser(Long userId) {
        logger.info("=== AS61003.deleteUser START ===");
        logger.info("삭제할 사용자 ID: {}", userId);
        
        try {
            // 입력 검증
            if (userId == null || userId <= 0) {
                logger.error("유효하지 않은 사용자 ID: {}", userId);
                return ApiResponseDto.error("400", "유효한 사용자 ID가 필요합니다.");
            }
            
            // PC 호출하여 사용자 삭제
            boolean isDeleted = userPC.deleteUser(userId);
            
            if (!isDeleted) {
                logger.warn("삭제할 사용자를 찾을 수 없습니다. ID: {}", userId);
                return ApiResponseDto.error("404", "삭제할 사용자를 찾을 수 없습니다.");
            }
            
            logger.info("사용자 삭제 성공 - ID: {}", userId);
            logger.info("=== AS61003.deleteUser END ===");
            
            return ApiResponseDto.success(true, "사용자가 성공적으로 삭제되었습니다.");
            
        } catch (Exception e) {
            logger.error("AS61003.deleteUser 중 오류 발생: {}", e.getMessage(), e);
            return ApiResponseDto.error("500", "사용자 삭제 중 오류가 발생했습니다.", e.getMessage());
        }
    }
} 
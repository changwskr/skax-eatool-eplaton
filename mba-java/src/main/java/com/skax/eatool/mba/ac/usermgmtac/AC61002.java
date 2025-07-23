package com.skax.eatool.mba.ac.usermgmtac;

import com.skax.eatool.mba.as.usermgmtas.dto.ApiResponseDto;
import com.skax.eatool.mba.as.usermgmtas.dto.UserDto;
import com.skax.eatool.mba.as.usermgmtas.AS61002;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 상세 조회 컨트롤러
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Detail Management", description = "사용자 상세 조회 API")
public class AC61002 {
    
    private static final Logger logger = LoggerFactory.getLogger(AC61002.class);
    
    @Autowired
    private AS61002 as61002; // 사용자 상세 조회 이벤트 서비스
    
    /**
     * 사용자 ID로 상세 조회
     * 
     * @param userId 사용자 ID
     * @return 사용자 상세 정보 응답
     */
    @GetMapping("/{userId}")
    @Operation(summary = "사용자 상세 조회", description = "사용자 ID로 사용자 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponseDto<UserDto>> getUserById(
            @Parameter(description = "사용자 ID") @PathVariable Long userId) {
        
        logger.info("=== AC61002.getUserById START ===");
        logger.info("요청 파라미터 - userId: {}", userId);
        
        try {
            ApiResponseDto<UserDto> response = as61002.getUserById(userId);
            
            logger.info("응답 상태: {}", response.isSuccess() ? "성공" : "실패");
            logger.info("=== AC61002.getUserById END ===");
            
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            logger.error("AC61002.getUserById 중 오류 발생: {}", e.getMessage(), e);
            ApiResponseDto<UserDto> errorResponse = 
                ApiResponseDto.error("500", "사용자 상세 조회 중 오류가 발생했습니다.", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 사용자명으로 상세 조회
     * 
     * @param username 사용자명
     * @return 사용자 상세 정보 응답
     */
    @GetMapping("/username/{username}")
    @Operation(summary = "사용자명으로 상세 조회", description = "사용자명으로 사용자 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponseDto<UserDto>> getUserByUsername(
            @Parameter(description = "사용자명") @PathVariable String username) {
        
        logger.info("=== AC61002.getUserByUsername START ===");
        logger.info("요청 파라미터 - username: {}", username);
        
        try {
            ApiResponseDto<UserDto> response = as61002.getUserByUsername(username);
            
            logger.info("응답 상태: {}", response.isSuccess() ? "성공" : "실패");
            logger.info("=== AC61002.getUserByUsername END ===");
            
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            logger.error("AC61002.getUserByUsername 중 오류 발생: {}", e.getMessage(), e);
            ApiResponseDto<UserDto> errorResponse = 
                ApiResponseDto.error("500", "사용자 상세 조회 중 오류가 발생했습니다.", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 이메일로 상세 조회
     * 
     * @param email 이메일
     * @return 사용자 상세 정보 응답
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "이메일로 상세 조회", description = "이메일로 사용자 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponseDto<UserDto>> getUserByEmail(
            @Parameter(description = "이메일") @PathVariable String email) {
        
        logger.info("=== AC61002.getUserByEmail START ===");
        logger.info("요청 파라미터 - email: {}", email);
        
        try {
            ApiResponseDto<UserDto> response = as61002.getUserByEmail(email);
            
            logger.info("응답 상태: {}", response.isSuccess() ? "성공" : "실패");
            logger.info("=== AC61002.getUserByEmail END ===");
            
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            logger.error("AC61002.getUserByEmail 중 오류 발생: {}", e.getMessage(), e);
            ApiResponseDto<UserDto> errorResponse = 
                ApiResponseDto.error("500", "사용자 상세 조회 중 오류가 발생했습니다.", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
} 
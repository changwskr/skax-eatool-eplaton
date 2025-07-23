package com.skax.eatool.mba.ac.usermgmtac;

import com.skax.eatool.mba.as.usermgmtas.dto.*;
import com.skax.eatool.mba.as.usermgmtas.AS61003;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 생성/수정/삭제 컨트롤러
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User CRUD Management", description = "사용자 생성/수정/삭제 API")
public class AC61003 {
    
    private static final Logger logger = LoggerFactory.getLogger(AC61003.class);
    
    @Autowired
    private AS61003 as61003; // 사용자 생성/수정/삭제 이벤트 서비스
    
    /**
     * 사용자 생성
     * 
     * @param createRequest 사용자 생성 요청
     * @return 생성된 사용자 정보 응답
     */
    @PostMapping
    @Operation(summary = "사용자 생성", description = "새로운 사용자를 생성합니다.")
    public ResponseEntity<ApiResponseDto<UserDto>> createUser(
            @Parameter(description = "사용자 생성 정보") @RequestBody CreateUserRequestDto createRequest) {
        
        logger.info("=== AC61003.createUser START ===");
        logger.info("요청 파라미터: {}", createRequest);
        
        try {
            ApiResponseDto<UserDto> response = as61003.createUser(createRequest);
            
            logger.info("응답 상태: {}", response.isSuccess() ? "성공" : "실패");
            logger.info("=== AC61003.createUser END ===");
            
            if (response.isSuccess()) {
                return ResponseEntity.created(null).body(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            logger.error("AC61003.createUser 중 오류 발생: {}", e.getMessage(), e);
            ApiResponseDto<UserDto> errorResponse = 
                ApiResponseDto.error("500", "사용자 생성 중 오류가 발생했습니다.", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 사용자 수정
     * 
     * @param userId 사용자 ID
     * @param updateRequest 사용자 수정 요청
     * @return 수정된 사용자 정보 응답
     */
    @PutMapping("/{userId}")
    @Operation(summary = "사용자 수정", description = "기존 사용자 정보를 수정합니다.")
    public ResponseEntity<ApiResponseDto<UserDto>> updateUser(
            @Parameter(description = "사용자 ID") @PathVariable Long userId,
            @Parameter(description = "사용자 수정 정보") @RequestBody UpdateUserRequestDto updateRequest) {
        
        logger.info("=== AC61003.updateUser START ===");
        logger.info("요청 파라미터 - userId: {}, updateRequest: {}", userId, updateRequest);
        
        try {
            ApiResponseDto<UserDto> response = as61003.updateUser(userId, updateRequest);
            
            logger.info("응답 상태: {}", response.isSuccess() ? "성공" : "실패");
            logger.info("=== AC61003.updateUser END ===");
            
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else if ("404".equals(response.getCode())) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            logger.error("AC61003.updateUser 중 오류 발생: {}", e.getMessage(), e);
            ApiResponseDto<UserDto> errorResponse = 
                ApiResponseDto.error("500", "사용자 수정 중 오류가 발생했습니다.", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 사용자 삭제
     * 
     * @param userId 사용자 ID
     * @return 삭제 결과 응답
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다.")
    public ResponseEntity<ApiResponseDto<Boolean>> deleteUser(
            @Parameter(description = "사용자 ID") @PathVariable Long userId) {
        
        logger.info("=== AC61003.deleteUser START ===");
        logger.info("요청 파라미터 - userId: {}", userId);
        
        try {
            ApiResponseDto<Boolean> response = as61003.deleteUser(userId);
            
            logger.info("응답 상태: {}", response.isSuccess() ? "성공" : "실패");
            logger.info("=== AC61003.deleteUser END ===");
            
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else if ("404".equals(response.getCode())) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            logger.error("AC61003.deleteUser 중 오류 발생: {}", e.getMessage(), e);
            ApiResponseDto<Boolean> errorResponse = 
                ApiResponseDto.error("500", "사용자 삭제 중 오류가 발생했습니다.", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 사용자 상태 변경
     * 
     * @param userId 사용자 ID
     * @param statusRequest 상태 변경 요청
     * @return 상태 변경 결과 응답
     */
    @PatchMapping("/{userId}/status")
    @Operation(summary = "사용자 상태 변경", description = "사용자의 상태를 변경합니다.")
    public ResponseEntity<ApiResponseDto<UserDto>> updateUserStatus(
            @Parameter(description = "사용자 ID") @PathVariable Long userId,
            @Parameter(description = "상태 변경 정보") @RequestBody UpdateUserStatusRequestDto statusRequest) {
        
        logger.info("=== AC61003.updateUserStatus START ===");
        logger.info("요청 파라미터 - userId: {}, statusRequest: {}", userId, statusRequest);
        
        try {
            // UpdateUserRequestDto로 변환
            UpdateUserRequestDto updateRequest = new UpdateUserRequestDto();
            updateRequest.setStatus(statusRequest.getStatus());
            
            ApiResponseDto<UserDto> response = as61003.updateUser(userId, updateRequest);
            
            logger.info("응답 상태: {}", response.isSuccess() ? "성공" : "실패");
            logger.info("=== AC61003.updateUserStatus END ===");
            
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else if ("404".equals(response.getCode())) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            logger.error("AC61003.updateUserStatus 중 오류 발생: {}", e.getMessage(), e);
            ApiResponseDto<UserDto> errorResponse = 
                ApiResponseDto.error("500", "사용자 상태 변경 중 오류가 발생했습니다.", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
} 
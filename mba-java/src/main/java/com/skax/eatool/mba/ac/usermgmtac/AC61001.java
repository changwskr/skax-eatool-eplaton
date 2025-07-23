package com.skax.eatool.mba.ac.usermgmtac;

import com.skax.eatool.mba.as.usermgmtas.dto.*;
import com.skax.eatool.mba.as.usermgmtas.AS61001;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 목록 관리 컨트롤러
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User List Management", description = "사용자 목록 관리 API")
public class AC61001 {
    
    private static final Logger logger = LoggerFactory.getLogger(AC61001.class);
    
    @Autowired
    private AS61001 as61001; // 사용자 목록 조회 이벤트 서비스
    
    /**
     * 사용자 목록 조회
     * 
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 사용자 목록 응답
     */
    @GetMapping
    @Operation(summary = "사용자 목록 조회", description = "페이지네이션을 지원하는 사용자 목록을 조회합니다.")
    public ResponseEntity<ApiResponseDto<PageResponseDto<UserDto>>> getUserList(
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size) {
        
        logger.info("=== AC61001.getUserList START ===");
        logger.info("요청 파라미터 - page: {}, size: {}", page, size);
        
        try {
            PageRequestDto pageRequest = new PageRequestDto(page, size);
            ApiResponseDto<PageResponseDto<UserDto>> response = as61001.getUserList(pageRequest);
            
            logger.info("응답 상태: {}", response.isSuccess() ? "성공" : "실패");
            logger.info("=== AC61001.getUserList END ===");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("AC61001.getUserList 중 오류 발생: {}", e.getMessage(), e);
            ApiResponseDto<PageResponseDto<UserDto>> errorResponse = 
                ApiResponseDto.error("500", "사용자 목록 조회 중 오류가 발생했습니다.", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 사용자 검색
     * 
     * @param keyword 검색 키워드
     * @param searchField 검색 필드
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 검색 결과 응답
     */
    @GetMapping("/search")
    @Operation(summary = "사용자 검색", description = "키워드와 검색 필드를 사용하여 사용자를 검색합니다.")
    public ResponseEntity<ApiResponseDto<PageResponseDto<UserDto>>> searchUsers(
            @Parameter(description = "검색 키워드") @RequestParam String keyword,
            @Parameter(description = "검색 필드 (username, email, fullName, department)") 
            @RequestParam(defaultValue = "all") String searchField,
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size) {
        
        logger.info("=== AC61001.searchUsers START ===");
        logger.info("요청 파라미터 - keyword: {}, searchField: {}, page: {}, size: {}", 
            keyword, searchField, page, size);
        
        try {
            PageRequestDto pageRequest = new PageRequestDto(page, size);
            ApiResponseDto<PageResponseDto<UserDto>> response = as61001.searchUsers(keyword, searchField, pageRequest);
            
            logger.info("응답 상태: {}", response.isSuccess() ? "성공" : "실패");
            logger.info("=== AC61001.searchUsers END ===");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("AC61001.searchUsers 중 오류 발생: {}", e.getMessage(), e);
            ApiResponseDto<PageResponseDto<UserDto>> errorResponse = 
                ApiResponseDto.error("500", "사용자 검색 중 오류가 발생했습니다.", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 부서별 사용자 목록 조회
     * 
     * @param department 부서명
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 부서별 사용자 목록 응답
     */
    @GetMapping("/department/{department}")
    @Operation(summary = "부서별 사용자 목록 조회", description = "특정 부서의 사용자 목록을 조회합니다.")
    public ResponseEntity<ApiResponseDto<PageResponseDto<UserDto>>> getUsersByDepartment(
            @Parameter(description = "부서명") @PathVariable String department,
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size) {
        
        logger.info("=== AC61001.getUsersByDepartment START ===");
        logger.info("요청 파라미터 - department: {}, page: {}, size: {}", department, page, size);
        
        try {
            PageRequestDto pageRequest = PageRequestDto.forSearch(page, size, department, "department");
            ApiResponseDto<PageResponseDto<UserDto>> response = as61001.getUserList(pageRequest);
            
            logger.info("응답 상태: {}", response.isSuccess() ? "성공" : "실패");
            logger.info("=== AC61001.getUsersByDepartment END ===");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("AC61001.getUsersByDepartment 중 오류 발생: {}", e.getMessage(), e);
            ApiResponseDto<PageResponseDto<UserDto>> errorResponse = 
                ApiResponseDto.error("500", "부서별 사용자 목록 조회 중 오류가 발생했습니다.", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 역할별 사용자 목록 조회
     * 
     * @param role 역할
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 역할별 사용자 목록 응답
     */
    @GetMapping("/role/{role}")
    @Operation(summary = "역할별 사용자 목록 조회", description = "특정 역할의 사용자 목록을 조회합니다.")
    public ResponseEntity<ApiResponseDto<PageResponseDto<UserDto>>> getUsersByRole(
            @Parameter(description = "역할") @PathVariable String role,
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size) {
        
        logger.info("=== AC61001.getUsersByRole START ===");
        logger.info("요청 파라미터 - role: {}, page: {}, size: {}", role, page, size);
        
        try {
            PageRequestDto pageRequest = PageRequestDto.forSearch(page, size, role, "role");
            ApiResponseDto<PageResponseDto<UserDto>> response = as61001.getUserList(pageRequest);
            
            logger.info("응답 상태: {}", response.isSuccess() ? "성공" : "실패");
            logger.info("=== AC61001.getUsersByRole END ===");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("AC61001.getUsersByRole 중 오류 발생: {}", e.getMessage(), e);
            ApiResponseDto<PageResponseDto<UserDto>> errorResponse = 
                ApiResponseDto.error("500", "역할별 사용자 목록 조회 중 오류가 발생했습니다.", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
} 
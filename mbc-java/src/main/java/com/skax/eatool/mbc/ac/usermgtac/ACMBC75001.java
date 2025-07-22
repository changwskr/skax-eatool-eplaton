package com.skax.eatool.mbc.ac.usermgtac;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;
import com.skax.eatool.mbc.as.usermgtas.ASMBC75001;
import com.skax.eatool.mbc.pc.dto.UserPDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 사용자 관리 Application Control
 * 
 * 프로그램명: ACMBC75001.java
 * 설명: 사용자 관리 REST API 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 사용자 목록 조회 API
 * - 사용자 상세 조회 API
 * - 사용자 등록/수정/삭제 API
 * - AS 호출 및 결과 반환
 * 
 * @version 1.0
 */
@RestController
@RequestMapping({ "/api/user", "/mbc/user" })
@Tag(name = "사용자 관리", description = "사용자 관리 관련 API")
public class ACMBC75001 {

    private static final NewIKesaLogger logger = NewKesaLogHelper.getBiz();

    @Autowired
    private ASMBC75001 asMbc75001;

    /**
     * 사용자 목록 조회 API
     */
    @GetMapping("/list")
    @Operation(summary = "사용자 목록 조회", description = "사용자 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, Object>> getUserList(
            @Parameter(description = "검색 키워드") @RequestParam(required = false) String searchKeyword,
            @Parameter(description = "검색 타입 (userId, userName, email, phone)") @RequestParam(required = false) String searchType,
            @Parameter(description = "역할 필터") @RequestParam(required = false) String roleFilter,
            @Parameter(description = "상태 필터") @RequestParam(required = false) String statusFilter,
            @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") Integer size) {
        
        logger.info("=== ACMBC75001.getUserList START ===", "ACMBC75001");
        logger.info("입력 파라미터 - searchKeyword: " + (searchKeyword != null ? searchKeyword : "NULL"), "ACMBC75001");
        logger.info("입력 파라미터 - searchType: " + (searchType != null ? searchType : "NULL"), "ACMBC75001");
        logger.info("입력 파라미터 - roleFilter: " + (roleFilter != null ? roleFilter : "NULL"), "ACMBC75001");
        logger.info("입력 파라미터 - statusFilter: " + (statusFilter != null ? statusFilter : "NULL"), "ACMBC75001");
        logger.info("입력 파라미터 - page: " + (page != null ? page : "NULL"), "ACMBC75001");
        logger.info("입력 파라미터 - size: " + (size != null ? size : "NULL"), "ACMBC75001");
        
        try {
            // NULL 체크 및 기본값 설정
            if (page == null) page = 1;
            if (size == null) size = 10;
            
            // 검색 조건 설정
            UserPDTO searchCondition = new UserPDTO();
            searchCondition.setSearchKeyword(searchKeyword);
            searchCondition.setSearchType(searchType);
            searchCondition.setRoleFilter(roleFilter);
            searchCondition.setStatusFilter(statusFilter);
            searchCondition.setPage(page);
            searchCondition.setSize(size);
            
            logger.info("생성된 UserPDTO - searchKeyword: " + (searchCondition.getSearchKeyword() != null ? searchCondition.getSearchKeyword() : "NULL"), "ACMBC75001");
            logger.info("생성된 UserPDTO - searchType: " + (searchCondition.getSearchType() != null ? searchCondition.getSearchType() : "NULL"), "ACMBC75001");
            logger.info("생성된 UserPDTO - roleFilter: " + (searchCondition.getRoleFilter() != null ? searchCondition.getRoleFilter() : "NULL"), "ACMBC75001");
            logger.info("생성된 UserPDTO - statusFilter: " + (searchCondition.getStatusFilter() != null ? searchCondition.getStatusFilter() : "NULL"), "ACMBC75001");
            logger.info("생성된 UserPDTO - page: " + (searchCondition.getPage() != null ? searchCondition.getPage() : "NULL"), "ACMBC75001");
            logger.info("생성된 UserPDTO - size: " + (searchCondition.getSize() != null ? searchCondition.getSize() : "NULL"), "ACMBC75001");
            
            // AS 호출
            NewKBData reqData = new NewKBData();
            reqData.getInputGenericDto().put("command", "LIST");
            reqData.getInputGenericDto().put("UserPDTO", searchCondition);
            
            logger.info("AS 호출 전 NewKBData - command: LIST", "ACMBC75001");
            logger.info("AS 호출 전 NewKBData - UserPDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"), "ACMBC75001");
            
            NewKBData resultData = asMbc75001.execute(reqData);
            
            logger.info("AS 호출 후 NewKBData: " + (resultData != null ? "NOT NULL" : "NULL"), "ACMBC75001");
            
            // 결과 추출
            @SuppressWarnings("unchecked")
            List<UserPDTO> userList = (List<UserPDTO>) resultData.getOutputGenericDto().get("UserPDTOList");
            
            logger.info("추출된 UserPDTOList: " + (userList != null ? "NOT NULL, 크기: " + userList.size() : "NULL"), "ACMBC75001");
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "사용자 목록 조회가 완료되었습니다.");
            response.put("data", userList);
            response.put("totalCount", userList != null ? userList.size() : 0);
            response.put("page", page);
            response.put("size", size);
            
            logger.info("응답 데이터 - success: true", "ACMBC75001");
            logger.info("응답 데이터 - message: 사용자 목록 조회가 완료되었습니다.", "ACMBC75001");
            logger.info("응답 데이터 - totalCount: " + (userList != null ? userList.size() : 0), "ACMBC75001");
            logger.info("응답 데이터 - page: " + page, "ACMBC75001");
            logger.info("응답 데이터 - size: " + size, "ACMBC75001");
            logger.info("조회된 사용자 수: " + (userList != null ? userList.size() : 0), "ACMBC75001");
            logger.info("=== ACMBC75001.getUserList END ===", "ACMBC75001");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("사용자 목록 조회 중 오류 발생: " + e.getMessage(), "ACMBC75001");
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "사용자 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false", "ACMBC75001");
            logger.info("오류 응답 데이터 - message: " + e.getMessage(), "ACMBC75001");
            logger.info("=== ACMBC75001.getUserList END (ERROR) ===", "ACMBC75001");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * 사용자 상세 조회 API
     */
    @GetMapping("/detail/{userId}")
    @Operation(summary = "사용자 상세 조회", description = "특정 사용자의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, Object>> getUserDetail(
            @Parameter(description = "사용자 ID", required = true) @PathVariable String userId) {
        
        logger.info("=== ACMBC75001.getUserDetail START ===", "ACMBC75001");
        logger.info("입력 파라미터 - userId: " + (userId != null ? userId : "NULL"), "ACMBC75001");
        
        try {
            // NULL 체크
            if (userId == null || userId.trim().isEmpty()) {
                logger.error("사용자 ID가 NULL이거나 비어있습니다.", "ACMBC75001");
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "사용자 ID는 필수입니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // AS 호출
            NewKBData reqData = new NewKBData();
            reqData.getInputGenericDto().put("command", "READ");
            
            UserPDTO userPDTO = new UserPDTO();
            userPDTO.setUserId(userId);
            reqData.getInputGenericDto().put("UserPDTO", userPDTO);
            
            logger.info("AS 호출 전 NewKBData - command: READ", "ACMBC75001");
            logger.info("AS 호출 전 NewKBData - UserPDTO.userId: " + (userPDTO.getUserId() != null ? userPDTO.getUserId() : "NULL"), "ACMBC75001");
            
            NewKBData resultData = asMbc75001.execute(reqData);
            
            logger.info("AS 호출 후 NewKBData: " + (resultData != null ? "NOT NULL" : "NULL"), "ACMBC75001");
            
            // 결과 추출
            UserPDTO user = (UserPDTO) resultData.getOutputGenericDto().get("UserPDTO");
            
            logger.info("추출된 UserPDTO: " + (user != null ? "NOT NULL" : "NULL"), "ACMBC75001");
            if (user != null) {
                logger.info("추출된 UserPDTO - userId: " + (user.getUserId() != null ? user.getUserId() : "NULL"), "ACMBC75001");
                logger.info("추출된 UserPDTO - userName: " + (user.getUserName() != null ? user.getUserName() : "NULL"), "ACMBC75001");
                logger.info("추출된 UserPDTO - email: " + (user.getEmail() != null ? user.getEmail() : "NULL"), "ACMBC75001");
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "사용자 조회가 완료되었습니다.");
            response.put("data", user);
            
            logger.info("응답 데이터 - success: true", "ACMBC75001");
            logger.info("응답 데이터 - message: 사용자 조회가 완료되었습니다.", "ACMBC75001");
            logger.info("사용자 조회 완료: " + (user != null ? user.getUserName() : "null"), "ACMBC75001");
            logger.info("=== ACMBC75001.getUserDetail END ===", "ACMBC75001");
            
            return ResponseEntity.ok(response);
            
        } catch (NewBusinessException e) {
            logger.error("사용자 조회 중 비즈니스 오류: " + e.getMessage(), "ACMBC75001");
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "사용자를 찾을 수 없습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false", "ACMBC75001");
            logger.info("오류 응답 데이터 - message: " + e.getMessage(), "ACMBC75001");
            logger.info("=== ACMBC75001.getUserDetail END (BUSINESS_ERROR) ===", "ACMBC75001");
            
            return ResponseEntity.status(404).body(errorResponse);
            
        } catch (Exception e) {
            logger.error("사용자 조회 중 오류 발생: " + e.getMessage(), "ACMBC75001");
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "사용자 조회 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false", "ACMBC75001");
            logger.info("오류 응답 데이터 - message: " + e.getMessage(), "ACMBC75001");
            logger.info("=== ACMBC75001.getUserDetail END (ERROR) ===", "ACMBC75001");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * 사용자 등록 API
     */
    @PostMapping("/create")
    @Operation(summary = "사용자 등록", description = "새로운 사용자를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, Object>> createUser(
            @Parameter(description = "사용자 정보", required = true) @RequestBody UserPDTO userPDTO) {
        
        logger.info("=== ACMBC75001.createUser START ===", "ACMBC75001");
        logger.info("입력 파라미터 - userPDTO: " + (userPDTO != null ? "NOT NULL" : "NULL"), "ACMBC75001");
        
        if (userPDTO != null) {
            logger.info("입력 파라미터 - userId: " + (userPDTO.getUserId() != null ? userPDTO.getUserId() : "NULL"), "ACMBC75001");
            logger.info("입력 파라미터 - userName: " + (userPDTO.getUserName() != null ? userPDTO.getUserName() : "NULL"), "ACMBC75001");
            logger.info("입력 파라미터 - email: " + (userPDTO.getEmail() != null ? userPDTO.getEmail() : "NULL"), "ACMBC75001");
            logger.info("입력 파라미터 - phone: " + (userPDTO.getPhone() != null ? userPDTO.getPhone() : "NULL"), "ACMBC75001");
            logger.info("입력 파라미터 - role: " + (userPDTO.getRole() != null ? userPDTO.getRole() : "NULL"), "ACMBC75001");
            logger.info("입력 파라미터 - status: " + (userPDTO.getStatus() != null ? userPDTO.getStatus() : "NULL"), "ACMBC75001");
        }
        
        try {
            // NULL 체크
            if (userPDTO == null) {
                logger.error("사용자 정보가 NULL입니다.", "ACMBC75001");
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "사용자 정보는 필수입니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            if (userPDTO.getUserId() == null || userPDTO.getUserId().trim().isEmpty()) {
                logger.error("사용자 ID가 NULL이거나 비어있습니다.", "ACMBC75001");
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "사용자 ID는 필수입니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // AS 호출
            NewKBData reqData = new NewKBData();
            reqData.getInputGenericDto().put("command", "CREATE");
            reqData.getInputGenericDto().put("UserPDTO", userPDTO);
            
            logger.info("AS 호출 전 NewKBData - command: CREATE", "ACMBC75001");
            logger.info("AS 호출 전 NewKBData - UserPDTO: " + (userPDTO != null ? "NOT NULL" : "NULL"), "ACMBC75001");
            
            NewKBData resultData = asMbc75001.execute(reqData);
            
            logger.info("AS 호출 후 NewKBData: " + (resultData != null ? "NOT NULL" : "NULL"), "ACMBC75001");
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "사용자 등록이 완료되었습니다.");
            response.put("data", userPDTO);
            
            logger.info("응답 데이터 - success: true", "ACMBC75001");
            logger.info("응답 데이터 - message: 사용자 등록이 완료되었습니다.", "ACMBC75001");
            logger.info("사용자 등록 완료: " + userPDTO.getUserName(), "ACMBC75001");
            logger.info("=== ACMBC75001.createUser END ===", "ACMBC75001");
            
            return ResponseEntity.ok(response);
            
        } catch (NewBusinessException e) {
            logger.error("사용자 등록 중 비즈니스 오류: " + e.getMessage(), "ACMBC75001");
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "사용자 등록 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false", "ACMBC75001");
            logger.info("오류 응답 데이터 - message: " + e.getMessage(), "ACMBC75001");
            logger.info("=== ACMBC75001.createUser END (BUSINESS_ERROR) ===", "ACMBC75001");
            
            return ResponseEntity.badRequest().body(errorResponse);
            
        } catch (Exception e) {
            logger.error("사용자 등록 중 오류 발생: " + e.getMessage(), "ACMBC75001");
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "사용자 등록 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false", "ACMBC75001");
            logger.info("오류 응답 데이터 - message: " + e.getMessage(), "ACMBC75001");
            logger.info("=== ACMBC75001.createUser END (ERROR) ===", "ACMBC75001");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * 사용자 수정 API
     */
    @PutMapping("/{userId}")
    @Operation(summary = "사용자 수정", description = "기존 사용자 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, Object>> updateUser(
            @Parameter(description = "사용자 ID", required = true) @PathVariable String userId,
            @Parameter(description = "수정할 사용자 정보", required = true) @RequestBody UserPDTO userPDTO) {
        
        logger.info("=== ACMBC75001.updateUser START ===", "ACMBC75001");
        logger.info("입력 파라미터 - userId: " + (userId != null ? userId : "NULL"), "ACMBC75001");
        logger.info("입력 파라미터 - userPDTO: " + (userPDTO != null ? "NOT NULL" : "NULL"), "ACMBC75001");
        
        if (userPDTO != null) {
            logger.info("입력 파라미터 - userName: " + (userPDTO.getUserName() != null ? userPDTO.getUserName() : "NULL"), "ACMBC75001");
            logger.info("입력 파라미터 - email: " + (userPDTO.getEmail() != null ? userPDTO.getEmail() : "NULL"), "ACMBC75001");
            logger.info("입력 파라미터 - phone: " + (userPDTO.getPhone() != null ? userPDTO.getPhone() : "NULL"), "ACMBC75001");
            logger.info("입력 파라미터 - role: " + (userPDTO.getRole() != null ? userPDTO.getRole() : "NULL"), "ACMBC75001");
            logger.info("입력 파라미터 - status: " + (userPDTO.getStatus() != null ? userPDTO.getStatus() : "NULL"), "ACMBC75001");
        }
        
        try {
            // NULL 체크
            if (userId == null || userId.trim().isEmpty()) {
                logger.error("사용자 ID가 NULL이거나 비어있습니다.", "ACMBC75001");
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "사용자 ID는 필수입니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            if (userPDTO == null) {
                logger.error("사용자 정보가 NULL입니다.", "ACMBC75001");
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "사용자 정보는 필수입니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // userId 설정
            userPDTO.setUserId(userId);
            
            // AS 호출
            NewKBData reqData = new NewKBData();
            reqData.getInputGenericDto().put("command", "UPDATE");
            reqData.getInputGenericDto().put("UserPDTO", userPDTO);
            
            logger.info("AS 호출 전 NewKBData - command: UPDATE", "ACMBC75001");
            logger.info("AS 호출 전 NewKBData - UserPDTO.userId: " + (userPDTO.getUserId() != null ? userPDTO.getUserId() : "NULL"), "ACMBC75001");
            
            NewKBData resultData = asMbc75001.execute(reqData);
            
            logger.info("AS 호출 후 NewKBData: " + (resultData != null ? "NOT NULL" : "NULL"), "ACMBC75001");
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "사용자 수정이 완료되었습니다.");
            response.put("data", userPDTO);
            
            logger.info("응답 데이터 - success: true", "ACMBC75001");
            logger.info("응답 데이터 - message: 사용자 수정이 완료되었습니다.", "ACMBC75001");
            logger.info("사용자 수정 완료: " + userPDTO.getUserName(), "ACMBC75001");
            logger.info("=== ACMBC75001.updateUser END ===", "ACMBC75001");
            
            return ResponseEntity.ok(response);
            
        } catch (NewBusinessException e) {
            logger.error("사용자 수정 중 비즈니스 오류: " + e.getMessage(), "ACMBC75001");
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "사용자 수정 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false", "ACMBC75001");
            logger.info("오류 응답 데이터 - message: " + e.getMessage(), "ACMBC75001");
            logger.info("=== ACMBC75001.updateUser END (BUSINESS_ERROR) ===", "ACMBC75001");
            
            return ResponseEntity.badRequest().body(errorResponse);
            
        } catch (Exception e) {
            logger.error("사용자 수정 중 오류 발생: " + e.getMessage(), "ACMBC75001");
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "사용자 수정 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false", "ACMBC75001");
            logger.info("오류 응답 데이터 - message: " + e.getMessage(), "ACMBC75001");
            logger.info("=== ACMBC75001.updateUser END (ERROR) ===", "ACMBC75001");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * 사용자 삭제 API
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, Object>> deleteUser(
            @Parameter(description = "사용자 ID", required = true) @PathVariable String userId) {
        
        logger.info("=== ACMBC75001.deleteUser START ===", "ACMBC75001");
        logger.info("입력 파라미터 - userId: " + (userId != null ? userId : "NULL"), "ACMBC75001");
        
        try {
            // NULL 체크
            if (userId == null || userId.trim().isEmpty()) {
                logger.error("사용자 ID가 NULL이거나 비어있습니다.", "ACMBC75001");
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "사용자 ID는 필수입니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // AS 호출
            NewKBData reqData = new NewKBData();
            reqData.getInputGenericDto().put("command", "DELETE");
            
            UserPDTO userPDTO = new UserPDTO();
            userPDTO.setUserId(userId);
            reqData.getInputGenericDto().put("UserPDTO", userPDTO);
            
            logger.info("AS 호출 전 NewKBData - command: DELETE", "ACMBC75001");
            logger.info("AS 호출 전 NewKBData - UserPDTO.userId: " + (userPDTO.getUserId() != null ? userPDTO.getUserId() : "NULL"), "ACMBC75001");
            
            NewKBData resultData = asMbc75001.execute(reqData);
            
            logger.info("AS 호출 후 NewKBData: " + (resultData != null ? "NOT NULL" : "NULL"), "ACMBC75001");
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "사용자 삭제가 완료되었습니다.");
            response.put("data", userId);
            
            logger.info("응답 데이터 - success: true", "ACMBC75001");
            logger.info("응답 데이터 - message: 사용자 삭제가 완료되었습니다.", "ACMBC75001");
            logger.info("사용자 삭제 완료: " + userId, "ACMBC75001");
            logger.info("=== ACMBC75001.deleteUser END ===", "ACMBC75001");
            
            return ResponseEntity.ok(response);
            
        } catch (NewBusinessException e) {
            logger.error("사용자 삭제 중 비즈니스 오류: " + e.getMessage(), "ACMBC75001");
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "사용자 삭제 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false", "ACMBC75001");
            logger.info("오류 응답 데이터 - message: " + e.getMessage(), "ACMBC75001");
            logger.info("=== ACMBC75001.deleteUser END (BUSINESS_ERROR) ===", "ACMBC75001");
            
            return ResponseEntity.badRequest().body(errorResponse);
            
        } catch (Exception e) {
            logger.error("사용자 삭제 중 오류 발생: " + e.getMessage(), "ACMBC75001");
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "사용자 삭제 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false", "ACMBC75001");
            logger.info("오류 응답 데이터 - message: " + e.getMessage(), "ACMBC75001");
            logger.info("=== ACMBC75001.deleteUser END (ERROR) ===", "ACMBC75001");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
} 
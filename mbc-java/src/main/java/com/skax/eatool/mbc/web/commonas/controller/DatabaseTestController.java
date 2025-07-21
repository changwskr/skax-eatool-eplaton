package com.skax.eatool.mbc.web.commonas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 데이터베이스 테스트 컨트롤러
 * 
 * @author SKAX Project Team
 * @version 1.0
 */
@RestController
@RequestMapping("/database/test")
@Tag(name = "데이터베이스 테스트", description = "데이터베이스 상태 및 테스트 데이터 확인 API")
public class DatabaseTestController {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseTestController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 데이터베이스 상태 확인
     */
    @GetMapping("/status")
    @Operation(summary = "데이터베이스 상태 확인", description = "데이터베이스 연결 상태와 테이블 정보를 확인합니다.")
    public ResponseEntity<Map<String, Object>> getDatabaseStatus() {
        logger.info("=== DatabaseTestController.getDatabaseStatus START ===");
        Map<String, Object> response = new HashMap<>();

        try {
            // 연결 테스트
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            response.put("connection", "정상");

            // 테이블 목록 조회
            List<Map<String, Object>> tables = jdbcTemplate.queryForList(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'"
            );
            response.put("tables", tables);
            response.put("tableCount", tables.size());

            // 사용자 테이블 데이터 수 조회
            try {
                int userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USER_INFO", Integer.class);
                response.put("userCount", userCount);
            } catch (Exception e) {
                response.put("userCount", 0);
            }

            try {
                int accountCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ACCOUNT", Integer.class);
                response.put("accountCount", accountCount);
            } catch (Exception e) {
                response.put("accountCount", 0);
            }

            response.put("status", "정상");
            response.put("message", "데이터베이스 연결이 정상입니다.");

        } catch (Exception e) {
            response.put("status", "오류");
            response.put("message", "데이터베이스 연결에 실패했습니다.");
            response.put("error", e.getMessage());
        }
        logger.info("=== DatabaseTestController.getDatabaseStatus END ===");
        return ResponseEntity.ok(response);
    }

    /**
     * 데이터 통계
     */
    @GetMapping("/statistics")
    @Operation(summary = "데이터 통계", description = "각 테이블의 데이터 통계를 확인합니다.")
    public ResponseEntity<Map<String, Object>> getDatabaseStatistics() {
        logger.info("=== DatabaseTestController.getDatabaseStatistics START ===");
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> statistics = new HashMap<>();

        try {
            // 각 테이블의 레코드 수 조회
            String[] tables = {"USER_INFO", "ACCOUNT", "TRANSACTION", "SYSTEM_CODE", "SYSTEM_LOG"};
            
            for (String table : tables) {
                try {
                    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + table, Integer.class);
                    statistics.put(table, count);
                } catch (Exception e) {
                    statistics.put(table, 0);
                }
            }

            response.put("statistics", statistics);
            response.put("status", "정상");

        } catch (Exception e) {
            response.put("status", "오류");
            response.put("error", e.getMessage());
        }
        logger.info("=== DatabaseTestController.getDatabaseStatistics END ===");
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자 목록 조회
     */
    @GetMapping("/users")
    @Operation(summary = "사용자 목록 조회", description = "사용자 정보 목록을 조회합니다.")
    public ResponseEntity<Map<String, Object>> getUsers(
            @Parameter(description = "페이지 크기", required = false) @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "오프셋", required = false) @RequestParam(defaultValue = "0") int offset) {

        logger.info("=== DatabaseTestController.getUsers START ===");
        Map<String, Object> response = new HashMap<>();

        try {
            String sql = "SELECT * FROM USER_INFO ORDER BY CREATED_DATE DESC LIMIT ? OFFSET ?";
            List<Map<String, Object>> users = jdbcTemplate.queryForList(sql, limit, offset);
            
            response.put("users", users);
            response.put("count", users.size());
            response.put("limit", limit);
            response.put("offset", offset);
            response.put("status", "정상");

        } catch (Exception e) {
            response.put("status", "오류");
            response.put("error", e.getMessage());
        }
        logger.info("=== DatabaseTestController.getUsers END ===");
        return ResponseEntity.ok(response);
    }

    /**
     * 계정 목록 조회
     */
    @GetMapping("/accounts")
    @Operation(summary = "계정 목록 조회", description = "계정 정보 목록을 조회합니다.")
    public ResponseEntity<Map<String, Object>> getAccounts(
            @Parameter(description = "페이지 크기", required = false) @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "오프셋", required = false) @RequestParam(defaultValue = "0") int offset) {

        logger.info("=== DatabaseTestController.getAccounts START ===");
        Map<String, Object> response = new HashMap<>();

        try {
            String sql = "SELECT * FROM ACCOUNT ORDER BY CREATED_DATE DESC LIMIT ? OFFSET ?";
            List<Map<String, Object>> accounts = jdbcTemplate.queryForList(sql, limit, offset);
            
            response.put("accounts", accounts);
            response.put("count", accounts.size());
            response.put("limit", limit);
            response.put("offset", offset);
            response.put("status", "정상");

        } catch (Exception e) {
            response.put("status", "오류");
            response.put("error", e.getMessage());
        }
        logger.info("=== DatabaseTestController.getAccounts END ===");
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자-계정 매핑 조회
     */
    @GetMapping("/user-accounts")
    @Operation(summary = "사용자-계정 매핑 조회", description = "사용자와 계정의 매핑 관계를 조회합니다.")
    public ResponseEntity<Map<String, Object>> getUserAccounts() {
        logger.info("=== DatabaseTestController.getUserAccounts START ===");
        Map<String, Object> response = new HashMap<>();

        try {
            String sql = "SELECT u.USER_NAME, a.ACCOUNT_NUMBER, a.ACCOUNT_TYPE " +
                        "FROM USER_INFO u " +
                        "LEFT JOIN ACCOUNT a ON u.USER_ID = a.USER_ID " +
                        "ORDER BY u.USER_NAME";
            
            List<Map<String, Object>> mappings = jdbcTemplate.queryForList(sql);
            response.put("mappings", mappings);
            response.put("count", mappings.size());
            response.put("status", "정상");

        } catch (Exception e) {
            response.put("status", "오류");
            response.put("error", e.getMessage());
        }
        logger.info("=== DatabaseTestController.getUserAccounts END ===");
        return ResponseEntity.ok(response);
    }

    /**
     * 시스템 코드 조회
     */
    @GetMapping("/system-codes")
    @Operation(summary = "시스템 코드 조회", description = "시스템 코드 정보를 조회합니다.")
    public ResponseEntity<Map<String, Object>> getSystemCodes(
            @Parameter(description = "코드 그룹", required = false) @RequestParam(required = false) String codeGroup) {

        logger.info("=== DatabaseTestController.getSystemCodes START ===");
        Map<String, Object> response = new HashMap<>();

        try {
            String sql = "SELECT * FROM SYSTEM_CODE";
            if (codeGroup != null && !codeGroup.isEmpty()) {
                sql += " WHERE CODE_GROUP = ?";
                List<Map<String, Object>> codes = jdbcTemplate.queryForList(sql, codeGroup);
                response.put("codes", codes);
            } else {
                List<Map<String, Object>> codes = jdbcTemplate.queryForList(sql);
                response.put("codes", codes);
            }
            
            response.put("count", response.get("codes") != null ? ((List<?>) response.get("codes")).size() : 0);
            response.put("status", "정상");

        } catch (Exception e) {
            response.put("status", "오류");
            response.put("error", e.getMessage());
        }
        logger.info("=== DatabaseTestController.getSystemCodes END ===");
        return ResponseEntity.ok(response);
    }

    /**
     * 시스템 로그 조회
     */
    @GetMapping("/system-logs")
    @Operation(summary = "시스템 로그 조회", description = "시스템 로그 정보를 조회합니다.")
    public ResponseEntity<Map<String, Object>> getSystemLogs(
            @Parameter(description = "페이지 크기", required = false) @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "오프셋", required = false) @RequestParam(defaultValue = "0") int offset) {

        logger.info("=== DatabaseTestController.getSystemLogs START ===");
        Map<String, Object> response = new HashMap<>();

        try {
            String sql = "SELECT * FROM SYSTEM_LOG ORDER BY LOG_DATE DESC LIMIT ? OFFSET ?";
            List<Map<String, Object>> logs = jdbcTemplate.queryForList(sql, limit, offset);
            
            response.put("logs", logs);
            response.put("count", logs.size());
            response.put("limit", limit);
            response.put("offset", offset);
            response.put("status", "정상");

        } catch (Exception e) {
            response.put("status", "오류");
            response.put("error", e.getMessage());
        }
        logger.info("=== DatabaseTestController.getSystemLogs END ===");
        return ResponseEntity.ok(response);
    }
} 
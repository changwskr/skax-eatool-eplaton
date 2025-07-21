package com.skax.eatool.mbc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 데이터베이스 테스트 컨트롤러
 * 
 * 프로그램명: DatabaseTestController.java
 * 설명: 데이터베이스 상태 및 테스트 데이터 확인용 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 데이터베이스 연결 상태 확인
 * - 테이블별 데이터 조회
 * - 초기화 데이터 통계
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("/api/database")
@CrossOrigin(origins = "*")
@Tag(name = "데이터베이스 테스트", description = "데이터베이스 상태 및 테스트 데이터 확인 API")
public class DatabaseTestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 데이터베이스 연결 상태 확인
     */
    @GetMapping("/status")
    @Operation(summary = "데이터베이스 상태 확인", description = "데이터베이스 연결 상태와 테이블 정보를 확인합니다.")
    public ResponseEntity<Map<String, Object>> getDatabaseStatus() {
        Map<String, Object> response = new HashMap<>();

        try {
            // 데이터베이스 연결 테스트
            String dbVersion = jdbcTemplate.queryForObject("SELECT H2VERSION()", String.class);

            // 테이블 목록 조회
            List<Map<String, Object>> tables = jdbcTemplate.queryForList(
                    "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'");

            response.put("success", true);
            response.put("database", "H2 Database");
            response.put("version", dbVersion);
            response.put("tables", tables);
            response.put("tableCount", tables.size());

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 테이블별 데이터 통계
     */
    @GetMapping("/statistics")
    @Operation(summary = "데이터 통계", description = "각 테이블의 데이터 통계를 확인합니다.")
    public ResponseEntity<Map<String, Object>> getDatabaseStatistics() {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> statistics = new HashMap<>();

        try {
            String[] tables = { "ACCOUNT", "USER_INFO", "USER_ACCOUNT", "SYSTEM_CODE", "SYSTEM_LOG" };

            for (String table : tables) {
                try {
                    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + table, Integer.class);
                    statistics.put(table, count);
                } catch (Exception e) {
                    statistics.put(table, "Error: " + e.getMessage());
                }
            }

            response.put("success", true);
            response.put("statistics", statistics);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 사용자 데이터 조회
     */
    @GetMapping("/users")
    @Operation(summary = "사용자 데이터 조회", description = "등록된 사용자 목록을 조회합니다.")
    public ResponseEntity<Map<String, Object>> getUsers(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        Map<String, Object> response = new HashMap<>();

        try {
            String sql = "SELECT USER_ID, USER_NAME, EMAIL, DEPARTMENT, ROLE, STATUS FROM USER_INFO ORDER BY USER_ID LIMIT ? OFFSET ?";
            List<Map<String, Object>> users = jdbcTemplate.queryForList(sql, limit, offset);

            int totalCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USER_INFO", Integer.class);

            response.put("success", true);
            response.put("users", users);
            response.put("totalCount", totalCount);
            response.put("limit", limit);
            response.put("offset", offset);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 계정 데이터 조회
     */
    @GetMapping("/accounts")
    @Operation(summary = "계정 데이터 조회", description = "등록된 계정 목록을 조회합니다.")
    public ResponseEntity<Map<String, Object>> getAccounts(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        Map<String, Object> response = new HashMap<>();

        try {
            String sql = "SELECT ACCOUNT_ID, ACCOUNT_NAME, ACCOUNT_TYPE, BALANCE, STATUS FROM ACCOUNT ORDER BY ACCOUNT_ID LIMIT ? OFFSET ?";
            List<Map<String, Object>> accounts = jdbcTemplate.queryForList(sql, limit, offset);

            int totalCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ACCOUNT", Integer.class);

            response.put("success", true);
            response.put("accounts", accounts);
            response.put("totalCount", totalCount);
            response.put("limit", limit);
            response.put("offset", offset);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 사용자-계정 매핑 조회
     */
    @GetMapping("/user-accounts")
    @Operation(summary = "사용자-계정 매핑 조회", description = "사용자와 계정의 매핑 관계를 조회합니다.")
    public ResponseEntity<Map<String, Object>> getUserAccounts() {
        Map<String, Object> response = new HashMap<>();

        try {
            String sql = "SELECT " +
                    "u.USER_NAME, " +
                    "a.ACCOUNT_NAME, " +
                    "ua.RELATIONSHIP_TYPE, " +
                    "a.ACCOUNT_TYPE, " +
                    "a.BALANCE " +
                    "FROM USER_ACCOUNT ua " +
                    "JOIN USER_INFO u ON ua.USER_ID = u.USER_ID " +
                    "JOIN ACCOUNT a ON ua.ACCOUNT_ID = a.ACCOUNT_ID " +
                    "ORDER BY u.USER_NAME, a.ACCOUNT_NAME";

            List<Map<String, Object>> mappings = jdbcTemplate.queryForList(sql);

            response.put("success", true);
            response.put("mappings", mappings);
            response.put("totalCount", mappings.size());

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 시스템 코드 조회
     */
    @GetMapping("/codes")
    @Operation(summary = "시스템 코드 조회", description = "시스템 코드 목록을 조회합니다.")
    public ResponseEntity<Map<String, Object>> getSystemCodes(
            @RequestParam(required = false) String codeGroup) {

        Map<String, Object> response = new HashMap<>();

        try {
            String sql;
            List<Map<String, Object>> codes;

            if (codeGroup != null && !codeGroup.trim().isEmpty()) {
                sql = "SELECT CODE_GROUP, CODE_VALUE, CODE_NAME, CODE_DESC, SORT_ORDER FROM SYSTEM_CODE WHERE CODE_GROUP = ? ORDER BY SORT_ORDER";
                codes = jdbcTemplate.queryForList(sql, codeGroup);
            } else {
                sql = "SELECT CODE_GROUP, CODE_VALUE, CODE_NAME, CODE_DESC, SORT_ORDER FROM SYSTEM_CODE ORDER BY CODE_GROUP, SORT_ORDER";
                codes = jdbcTemplate.queryForList(sql);
            }

            response.put("success", true);
            response.put("codes", codes);
            response.put("totalCount", codes.size());

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 시스템 로그 조회
     */
    @GetMapping("/logs")
    @Operation(summary = "시스템 로그 조회", description = "시스템 로그를 조회합니다.")
    public ResponseEntity<Map<String, Object>> getSystemLogs(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        Map<String, Object> response = new HashMap<>();

        try {
            String sql = "SELECT LOG_LEVEL, LOG_MESSAGE, LOG_CLASS, USER_ID, IP_ADDRESS, CREATED_DATE FROM SYSTEM_LOG ORDER BY CREATED_DATE DESC LIMIT ? OFFSET ?";
            List<Map<String, Object>> logs = jdbcTemplate.queryForList(sql, limit, offset);

            int totalCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM SYSTEM_LOG", Integer.class);

            response.put("success", true);
            response.put("logs", logs);
            response.put("totalCount", totalCount);
            response.put("limit", limit);
            response.put("offset", offset);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}
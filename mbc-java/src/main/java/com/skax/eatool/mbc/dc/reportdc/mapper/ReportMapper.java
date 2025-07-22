package com.skax.eatool.mbc.dc.reportdc.mapper;

import com.skax.eatool.mbc.dc.reportdc.Report;
import com.skax.eatool.mbc.dc.reportdc.dto.ReportDDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 보고서 Mapper
 * 
 * 프로그램명: ReportMapper.java
 * 설명: 보고서 관련 데이터베이스 접근을 담당하는 Mapper
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 보고서 기본 정보 CRUD
 * - 통계 데이터 조회
 * - 차트 데이터 조회
 * 
 * @version 1.0
 */
@Repository
public class ReportMapper {
    
    private static final Logger logger = LoggerFactory.getLogger(ReportMapper.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 보고서 목록 조회
     */
    public List<Report> findBySearchCondition(ReportDDTO searchCondition) {
        logger.info("=== ReportMapper.findBySearchCondition START ===");
        logger.info("입력 ReportDDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"));
        
        try {
            if (searchCondition == null) {
                logger.warn("ReportDDTO가 NULL입니다. 기본 조회를 수행합니다.");
                return findAll();
            }
            
            StringBuilder sql = new StringBuilder();
            List<Object> params = new ArrayList<>();
            
            sql.append("SELECT REPORT_ID, REPORT_TYPE, REPORT_NAME, REPORT_DESCRIPTION, ");
            sql.append("REPORT_STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, ");
            sql.append("REPORT_CONFIG, REPORT_TEMPLATE ");
            sql.append("FROM REPORT_INFO WHERE 1=1");
            
            // 보고서 타입 필터
            if (searchCondition.getReportType() != null && !searchCondition.getReportType().trim().isEmpty()) {
                sql.append(" AND REPORT_TYPE = ?");
                params.add(searchCondition.getReportType());
                logger.info("REPORT_TYPE 필터 추가: " + searchCondition.getReportType());
            }
            
            // 정렬
            sql.append(" ORDER BY CREATED_DATE DESC");
            
            logger.info("생성된 SQL: " + sql.toString());
            logger.info("SQL 파라미터 개수: " + params.size());
            
            List<Report> result = jdbcTemplate.query(sql.toString(), params.toArray(), new ReportRowMapper());
            
            logger.info("쿼리 실행 결과: " + (result != null ? "NOT NULL, 크기: " + result.size() : "NULL"));
            logger.info("=== ReportMapper.findBySearchCondition END ===");
            
            return result;
            
        } catch (Exception e) {
            logger.error("ReportMapper.findBySearchCondition 중 오류 발생: " + e.getMessage());
            logger.info("=== ReportMapper.findBySearchCondition END (ERROR) ===");
            throw e;
        }
    }
    
    /**
     * 모든 보고서 조회
     */
    public List<Report> findAll() {
        logger.info("=== ReportMapper.findAll START ===");
        
        try {
            String sql = "SELECT REPORT_ID, REPORT_TYPE, REPORT_NAME, REPORT_DESCRIPTION, " +
                        "REPORT_STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, " +
                        "REPORT_CONFIG, REPORT_TEMPLATE " +
                        "FROM REPORT_INFO ORDER BY CREATED_DATE DESC";
            
            List<Report> result = jdbcTemplate.query(sql, new ReportRowMapper());
            
            logger.info("전체 보고서 조회 결과: " + (result != null ? "NOT NULL, 크기: " + result.size() : "NULL"));
            logger.info("=== ReportMapper.findAll END ===");
            
            return result;
            
        } catch (Exception e) {
            logger.error("ReportMapper.findAll 중 오류 발생: " + e.getMessage());
            logger.info("=== ReportMapper.findAll END (ERROR) ===");
            throw e;
        }
    }
    
    /**
     * 보고서 ID로 조회
     */
    public Report findByReportId(String reportId) {
        logger.info("=== ReportMapper.findByReportId START ===");
        logger.info("조회할 reportId: " + (reportId != null ? reportId : "NULL"));
        
        try {
            if (reportId == null || reportId.trim().isEmpty()) {
                logger.warn("reportId가 NULL이거나 비어있습니다.");
                logger.info("=== ReportMapper.findByReportId END (NULL_INPUT) ===");
                return null;
            }
            
            String sql = "SELECT REPORT_ID, REPORT_TYPE, REPORT_NAME, REPORT_DESCRIPTION, " +
                        "REPORT_STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, " +
                        "REPORT_CONFIG, REPORT_TEMPLATE " +
                        "FROM REPORT_INFO WHERE REPORT_ID = ?";
            
            List<Report> result = jdbcTemplate.query(sql, new Object[]{reportId}, new ReportRowMapper());
            
            Report report = result.isEmpty() ? null : result.get(0);
            
            logger.info("조회 결과: " + (report != null ? "NOT NULL" : "NULL"));
            logger.info("=== ReportMapper.findByReportId END ===");
            
            return report;
            
        } catch (Exception e) {
            logger.error("ReportMapper.findByReportId 중 오류 발생: " + e.getMessage());
            logger.info("=== ReportMapper.findByReportId END (ERROR) ===");
            throw e;
        }
    }
    
    /**
     * 계정 통계 조회
     */
    public Map<String, Object> getAccountStatistics(ReportDDTO searchCondition) {
        logger.info("=== ReportMapper.getAccountStatistics START ===");
        logger.info("입력 ReportDDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"));
        
        if (searchCondition != null) {
            logger.info("입력 ReportDDTO - reportId: " + (searchCondition.getReportId() != null ? searchCondition.getReportId() : "NULL"));
            logger.info("입력 ReportDDTO - reportType: " + (searchCondition.getReportType() != null ? searchCondition.getReportType() : "NULL"));
            logger.info("입력 ReportDDTO - reportName: " + (searchCondition.getReportName() != null ? searchCondition.getReportName() : "NULL"));
        }
        
        try {
            Map<String, Object> result = new HashMap<>();
            
            // 전체 계정 수 조회
            String totalSql = "SELECT COUNT(*) FROM ACCOUNT";
            Long totalCount = jdbcTemplate.queryForObject(totalSql, Long.class);
            result.put("totalCount", totalCount);
            logger.info("전체 계정 수 조회 결과: " + (totalCount != null ? totalCount : "NULL"));
            
            // 활성 계정 수 조회
            String activeSql = "SELECT COUNT(*) FROM ACCOUNT WHERE STATUS = 'ACTIVE'";
            Long activeCount = jdbcTemplate.queryForObject(activeSql, Long.class);
            result.put("activeCount", activeCount);
            logger.info("활성 계정 수 조회 결과: " + (activeCount != null ? activeCount : "NULL"));
            
            // 비활성 계정 수 조회
            String inactiveSql = "SELECT COUNT(*) FROM ACCOUNT WHERE STATUS = 'INACTIVE'";
            Long inactiveCount = jdbcTemplate.queryForObject(inactiveSql, Long.class);
            result.put("inactiveCount", inactiveCount);
            logger.info("비활성 계정 수 조회 결과: " + (inactiveCount != null ? inactiveCount : "NULL"));
            
            // 계정 타입별 통계
            String typeSql = "SELECT ACCOUNT_TYPE, COUNT(*) as count FROM ACCOUNT GROUP BY ACCOUNT_TYPE";
            List<Map<String, Object>> accountTypes = jdbcTemplate.queryForList(typeSql);
            result.put("accountTypes", accountTypes);
            logger.info("계정 타입별 통계 조회 결과: " + (accountTypes != null ? "NOT NULL, 크기: " + accountTypes.size() : "NULL"));
            
            // 계정 상태별 통계
            String statusSql = "SELECT STATUS, COUNT(*) as count FROM ACCOUNT GROUP BY STATUS";
            List<Map<String, Object>> accountStatuses = jdbcTemplate.queryForList(statusSql);
            result.put("accountStatuses", accountStatuses);
            logger.info("계정 상태별 통계 조회 결과: " + (accountStatuses != null ? "NOT NULL, 크기: " + accountStatuses.size() : "NULL"));
            
            logger.info("최종 결과 Map: " + (result != null ? "NOT NULL, 크기: " + result.size() : "NULL"));
            logger.info("=== ReportMapper.getAccountStatistics END ===");
            
            return result;
            
        } catch (Exception e) {
            logger.error("ReportMapper.getAccountStatistics 중 오류 발생: " + e.getMessage());
            logger.info("=== ReportMapper.getAccountStatistics END (ERROR) ===");
            throw e;
        }
    }
    
    /**
     * 사용자 통계 조회
     */
    public Map<String, Object> getUserStatistics(ReportDDTO searchCondition) {
        logger.info("=== ReportMapper.getUserStatistics START ===");
        logger.info("입력 ReportDDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"));
        
        if (searchCondition != null) {
            logger.info("입력 ReportDDTO - reportId: " + (searchCondition.getReportId() != null ? searchCondition.getReportId() : "NULL"));
            logger.info("입력 ReportDDTO - reportType: " + (searchCondition.getReportType() != null ? searchCondition.getReportType() : "NULL"));
            logger.info("입력 ReportDDTO - reportName: " + (searchCondition.getReportName() != null ? searchCondition.getReportName() : "NULL"));
        }
        
        try {
            Map<String, Object> result = new HashMap<>();
            
            // 전체 사용자 수 조회
            String totalSql = "SELECT COUNT(*) FROM USER_INFO";
            Long totalCount = jdbcTemplate.queryForObject(totalSql, Long.class);
            result.put("totalCount", totalCount);
            logger.info("전체 사용자 수 조회 결과: " + (totalCount != null ? totalCount : "NULL"));
            
            // 활성 사용자 수 조회
            String activeSql = "SELECT COUNT(*) FROM USER_INFO WHERE STATUS = 'ACTIVE'";
            Long activeCount = jdbcTemplate.queryForObject(activeSql, Long.class);
            result.put("activeCount", activeCount);
            logger.info("활성 사용자 수 조회 결과: " + (activeCount != null ? activeCount : "NULL"));
            
            // 비활성 사용자 수 조회
            String inactiveSql = "SELECT COUNT(*) FROM USER_INFO WHERE STATUS = 'INACTIVE'";
            Long inactiveCount = jdbcTemplate.queryForObject(inactiveSql, Long.class);
            result.put("inactiveCount", inactiveCount);
            logger.info("비활성 사용자 수 조회 결과: " + (inactiveCount != null ? inactiveCount : "NULL"));
            
            // 사용자 역할별 통계
            String roleSql = "SELECT ROLE, COUNT(*) as count FROM USER_INFO GROUP BY ROLE";
            List<Map<String, Object>> userRoles = jdbcTemplate.queryForList(roleSql);
            result.put("userRoles", userRoles);
            logger.info("사용자 역할별 통계 조회 결과: " + (userRoles != null ? "NOT NULL, 크기: " + userRoles.size() : "NULL"));
            
            // 사용자 상태별 통계
            String statusSql = "SELECT STATUS, COUNT(*) as count FROM USER_INFO GROUP BY STATUS";
            List<Map<String, Object>> userStatuses = jdbcTemplate.queryForList(statusSql);
            result.put("userStatuses", userStatuses);
            logger.info("사용자 상태별 통계 조회 결과: " + (userStatuses != null ? "NOT NULL, 크기: " + userStatuses.size() : "NULL"));
            
            logger.info("최종 결과 Map: " + (result != null ? "NOT NULL, 크기: " + result.size() : "NULL"));
            logger.info("=== ReportMapper.getUserStatistics END ===");
            
            return result;
            
        } catch (Exception e) {
            logger.error("ReportMapper.getUserStatistics 중 오류 발생: " + e.getMessage());
            logger.info("=== ReportMapper.getUserStatistics END (ERROR) ===");
            throw e;
        }
    }
    
    /**
     * Report RowMapper
     */
    private static class ReportRowMapper implements RowMapper<Report> {
        @Override
        public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
            logger.info("=== ReportRowMapper.mapRow START ===");
            logger.info("매핑할 행 번호: " + rowNum);
            
            try {
                Report report = new Report();
                report.setReportId(rs.getString("REPORT_ID"));
                report.setReportType(rs.getString("REPORT_TYPE"));
                report.setReportName(rs.getString("REPORT_NAME"));
                report.setReportDescription(rs.getString("REPORT_DESCRIPTION"));
                report.setReportStatus(rs.getString("REPORT_STATUS"));
                report.setCreatedBy(rs.getString("CREATED_BY"));
                report.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                report.setUpdatedBy(rs.getString("UPDATED_BY"));
                report.setUpdatedDate(rs.getTimestamp("UPDATED_DATE"));
                report.setReportConfig(rs.getString("REPORT_CONFIG"));
                report.setReportTemplate(rs.getString("REPORT_TEMPLATE"));
                
                logger.info("매핑된 Report - reportId: " + (report.getReportId() != null ? report.getReportId() : "NULL"));
                logger.info("=== ReportRowMapper.mapRow END ===");
                
                return report;
                
            } catch (SQLException e) {
                logger.error("ReportRowMapper.mapRow 중 SQL 오류 발생: " + e.getMessage());
                logger.info("=== ReportRowMapper.mapRow END (SQL_ERROR) ===");
                throw e;
            } catch (Exception e) {
                logger.error("ReportRowMapper.mapRow 중 오류 발생: " + e.getMessage());
                logger.info("=== ReportRowMapper.mapRow END (ERROR) ===");
                throw new SQLException("Report 매핑 중 오류가 발생했습니다.", e);
            }
        }
    }
} 
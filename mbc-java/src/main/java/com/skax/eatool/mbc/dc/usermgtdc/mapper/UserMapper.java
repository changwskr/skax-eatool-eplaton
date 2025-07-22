package com.skax.eatool.mbc.dc.usermgtdc.mapper;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbc.dc.usermgtdc.User;
import com.skax.eatool.mbc.dc.usermgtdc.dto.UserDDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 사용자 관리 Mapper
 * 
 * 프로그램명: UserMapper.java
 * 설명: 사용자 관리 데이터베이스 접근을 담당하는 Mapper
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 사용자 목록 조회
 * - 사용자 상세 조회
 * - 사용자 등록/수정/삭제
 * - 사용자 검색 및 페이징
 * 
 * @version 1.0
 */
@Repository
public class UserMapper {
    
    private static final NewIKesaLogger logger = NewKesaLogHelper.getBiz();
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 사용자 목록 조회
     */
    public List<User> findAll() {
        logger.info("=== UserMapper.findAll START ===", "UserMapper");
        String sql = "SELECT * FROM USER_INFO ORDER BY CREATED_DATE DESC";
        List<User> result = jdbcTemplate.query(sql, new UserRowMapper());
        logger.info("조회된 사용자 목록 크기: " + (result != null ? result.size() : 0), "UserMapper");
        logger.info("=== UserMapper.findAll END ===", "UserMapper");
        return result;
    }
    
    /**
     * 사용자 ID로 조회
     */
    public User findByUserId(String userId) {
        logger.info("=== UserMapper.findByUserId START ===", "UserMapper");
        logger.info("입력 userId: " + (userId != null ? userId : "NULL"), "UserMapper");
        
        try {
            // NULL 체크
            if (userId == null || userId.trim().isEmpty()) {
                logger.warn("userId가 NULL이거나 비어있습니다.", "UserMapper");
                logger.info("=== UserMapper.findByUserId END (NULL_INPUT) ===", "UserMapper");
                return null;
            }
            
            String sql = "SELECT * FROM USER_INFO WHERE USER_ID = ?";
            logger.info("실행할 SQL: " + sql, "UserMapper");
            logger.info("SQL 파라미터: " + userId, "UserMapper");
            
            List<User> result = jdbcTemplate.query(sql, new Object[]{userId}, new UserRowMapper());
            
            User user = result.isEmpty() ? null : result.get(0);
            logger.info("쿼리 실행 결과: " + (user != null ? "NOT NULL" : "NULL"), "UserMapper");
            if (user != null) {
                logger.info("조회된 사용자 - userId: " + (user.getUserId() != null ? user.getUserId() : "NULL"), "UserMapper");
                logger.info("조회된 사용자 - userName: " + (user.getUserName() != null ? user.getUserName() : "NULL"), "UserMapper");
            }
            
            logger.info("=== UserMapper.findByUserId END ===", "UserMapper");
            return user;
            
        } catch (Exception e) {
            logger.error("UserMapper.findByUserId 중 오류 발생: " + e.getMessage(), "UserMapper");
            logger.info("=== UserMapper.findByUserId END (ERROR) ===", "UserMapper");
            throw e;
        }
    }
    
    /**
     * 이메일로 조회
     */
    public User findByEmail(String email) {
        logger.info("=== UserMapper.findByEmail START ===", "UserMapper");
        logger.info("입력 email: " + (email != null ? email : "NULL"), "UserMapper");
        
        try {
            // NULL 체크
            if (email == null || email.trim().isEmpty()) {
                logger.warn("email이 NULL이거나 비어있습니다.", "UserMapper");
                logger.info("=== UserMapper.findByEmail END (NULL_INPUT) ===", "UserMapper");
                return null;
            }
            
            String sql = "SELECT * FROM USER_INFO WHERE EMAIL = ?";
            logger.info("실행할 SQL: " + sql, "UserMapper");
            logger.info("SQL 파라미터: " + email, "UserMapper");
            
            List<User> result = jdbcTemplate.query(sql, new Object[]{email}, new UserRowMapper());
            
            User user = result.isEmpty() ? null : result.get(0);
            logger.info("쿼리 실행 결과: " + (user != null ? "NOT NULL" : "NULL"), "UserMapper");
            if (user != null) {
                logger.info("조회된 사용자 - userId: " + (user.getUserId() != null ? user.getUserId() : "NULL"), "UserMapper");
                logger.info("조회된 사용자 - email: " + (user.getEmail() != null ? user.getEmail() : "NULL"), "UserMapper");
            }
            
            logger.info("=== UserMapper.findByEmail END ===", "UserMapper");
            return user;
            
        } catch (Exception e) {
            logger.error("UserMapper.findByEmail 중 오류 발생: " + e.getMessage(), "UserMapper");
            logger.info("=== UserMapper.findByEmail END (ERROR) ===", "UserMapper");
            throw e;
        }
    }
    
    /**
     * 사용자 등록
     */
    public int insert(User user) {
        logger.info("=== UserMapper.insert START ===", "UserMapper");
        logger.info("입력 User: " + (user != null ? "NOT NULL" : "NULL"), "UserMapper");
        
        if (user != null) {
            logger.info("입력 User - userId: " + (user.getUserId() != null ? user.getUserId() : "NULL"), "UserMapper");
            logger.info("입력 User - userName: " + (user.getUserName() != null ? user.getUserName() : "NULL"), "UserMapper");
            logger.info("입력 User - email: " + (user.getEmail() != null ? user.getEmail() : "NULL"), "UserMapper");
        }
        
        try {
            // NULL 체크
            if (user == null) {
                logger.error("User가 NULL입니다.", "UserMapper");
                throw new IllegalArgumentException("User는 필수입니다.");
            }
            
            if (user.getUserId() == null || user.getUserId().trim().isEmpty()) {
                logger.error("사용자 ID가 NULL이거나 비어있습니다.", "UserMapper");
                throw new IllegalArgumentException("사용자 ID는 필수입니다.");
            }
            
            String sql = "INSERT INTO USER_INFO (USER_ID, USER_NAME, EMAIL, PHONE, ROLE, STATUS, CREATED_DATE, UPDATED_DATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            logger.info("실행할 SQL: " + sql, "UserMapper");
            
            Object[] params = {
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole() != null ? user.getRole() : "USER",
                user.getStatus() != null ? user.getStatus() : "ACTIVE",
                user.getCreatedDate() != null ? user.getCreatedDate() : new java.util.Date(),
                user.getUpdatedDate() != null ? user.getUpdatedDate() : new java.util.Date()
            };
            
            logger.info("SQL 파라미터 개수: " + params.length, "UserMapper");
            for (int i = 0; i < params.length; i++) {
                logger.info("파라미터[" + i + "]: " + (params[i] != null ? params[i] : "NULL"), "UserMapper");
            }
            
            int result = jdbcTemplate.update(sql, params);
            
            logger.info("INSERT 실행 결과: " + result, "UserMapper");
            logger.info("=== UserMapper.insert END ===", "UserMapper");
            
            return result;
            
        } catch (Exception e) {
            logger.error("UserMapper.insert 중 오류 발생: " + e.getMessage(), "UserMapper");
            logger.info("=== UserMapper.insert END (ERROR) ===", "UserMapper");
            throw e;
        }
    }
    
    /**
     * 사용자 수정
     */
    public int update(User user) {
        logger.info("=== UserMapper.update START ===", "UserMapper");
        logger.info("입력 User: " + (user != null ? "NOT NULL" : "NULL"), "UserMapper");
        
        if (user != null) {
            logger.info("입력 User - userId: " + (user.getUserId() != null ? user.getUserId() : "NULL"), "UserMapper");
            logger.info("입력 User - userName: " + (user.getUserName() != null ? user.getUserName() : "NULL"), "UserMapper");
            logger.info("입력 User - email: " + (user.getEmail() != null ? user.getEmail() : "NULL"), "UserMapper");
        }
        
        try {
            // NULL 체크
            if (user == null) {
                logger.error("User가 NULL입니다.", "UserMapper");
                throw new IllegalArgumentException("User는 필수입니다.");
            }
            
            if (user.getUserId() == null || user.getUserId().trim().isEmpty()) {
                logger.error("사용자 ID가 NULL이거나 비어있습니다.", "UserMapper");
                throw new IllegalArgumentException("사용자 ID는 필수입니다.");
            }
            
            String sql = "UPDATE USER_INFO SET USER_NAME = ?, EMAIL = ?, PHONE = ?, ROLE = ?, STATUS = ?, UPDATED_DATE = ? WHERE USER_ID = ?";
            logger.info("실행할 SQL: " + sql, "UserMapper");
            
            Object[] params = {
                user.getUserName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getStatus(),
                user.getUpdatedDate() != null ? user.getUpdatedDate() : new java.util.Date(),
                user.getUserId()
            };
            
            logger.info("SQL 파라미터 개수: " + params.length, "UserMapper");
            for (int i = 0; i < params.length; i++) {
                logger.info("파라미터[" + i + "]: " + (params[i] != null ? params[i] : "NULL"), "UserMapper");
            }
            
            int result = jdbcTemplate.update(sql, params);
            
            logger.info("UPDATE 실행 결과: " + result, "UserMapper");
            logger.info("=== UserMapper.update END ===", "UserMapper");
            
            return result;
            
        } catch (Exception e) {
            logger.error("UserMapper.update 중 오류 발생: " + e.getMessage(), "UserMapper");
            logger.info("=== UserMapper.update END (ERROR) ===", "UserMapper");
            throw e;
        }
    }
    
    /**
     * 사용자 삭제
     */
    public int delete(String userId) {
        logger.info("=== UserMapper.delete START ===", "UserMapper");
        logger.info("입력 userId: " + (userId != null ? userId : "NULL"), "UserMapper");
        
        try {
            // NULL 체크
            if (userId == null || userId.trim().isEmpty()) {
                logger.error("userId가 NULL이거나 비어있습니다.", "UserMapper");
                throw new IllegalArgumentException("userId는 필수입니다.");
            }
            
            String sql = "DELETE FROM USER_INFO WHERE USER_ID = ?";
            logger.info("실행할 SQL: " + sql, "UserMapper");
            logger.info("SQL 파라미터: " + userId, "UserMapper");
            
            int result = jdbcTemplate.update(sql, userId);
            
            logger.info("DELETE 실행 결과: " + result, "UserMapper");
            logger.info("=== UserMapper.delete END ===", "UserMapper");
            
            return result;
            
        } catch (Exception e) {
            logger.error("UserMapper.delete 중 오류 발생: " + e.getMessage(), "UserMapper");
            logger.info("=== UserMapper.delete END (ERROR) ===", "UserMapper");
            throw e;
        }
    }
    
    /**
     * 검색 조건에 따른 사용자 목록 조회
     */
    public List<User> findBySearchCondition(UserDDTO searchCondition) {
        logger.info("=== UserMapper.findBySearchCondition START ===", "UserMapper");
        logger.info("입력 UserDDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"), "UserMapper");
        
        if (searchCondition != null) {
            logger.info("입력 UserDDTO - searchKeyword: " + (searchCondition.getSearchKeyword() != null ? searchCondition.getSearchKeyword() : "NULL"), "UserMapper");
            logger.info("입력 UserDDTO - searchType: " + (searchCondition.getSearchType() != null ? searchCondition.getSearchType() : "NULL"), "UserMapper");
            logger.info("입력 UserDDTO - roleFilter: " + (searchCondition.getRoleFilter() != null ? searchCondition.getRoleFilter() : "NULL"), "UserMapper");
            logger.info("입력 UserDDTO - statusFilter: " + (searchCondition.getStatusFilter() != null ? searchCondition.getStatusFilter() : "NULL"), "UserMapper");
            logger.info("입력 UserDDTO - page: " + (searchCondition.getPage() != null ? searchCondition.getPage() : "NULL"), "UserMapper");
            logger.info("입력 UserDDTO - size: " + (searchCondition.getSize() != null ? searchCondition.getSize() : "NULL"), "UserMapper");
        }
        
        try {
            // NULL 체크
            if (searchCondition == null) {
                logger.warn("UserDDTO가 NULL입니다. 기본 조회를 수행합니다.", "UserMapper");
                String sql = "SELECT * FROM USER_INFO ORDER BY CREATED_DATE DESC";
                List<User> result = jdbcTemplate.query(sql, new UserRowMapper());
                logger.info("기본 조회 결과: " + (result != null ? "NOT NULL, 크기: " + result.size() : "NULL"), "UserMapper");
                logger.info("=== UserMapper.findBySearchCondition END ===", "UserMapper");
                return result;
            }
            
            // 검색 조건에 따른 SQL 동적 생성
            StringBuilder sql = new StringBuilder("SELECT * FROM USER_INFO WHERE 1=1");
            List<Object> params = new java.util.ArrayList<>();
            
            // 검색 키워드 조건
            if (searchCondition.getSearchKeyword() != null && !searchCondition.getSearchKeyword().trim().isEmpty()) {
                String searchType = searchCondition.getSearchType();
                if ("userId".equals(searchType)) {
                    sql.append(" AND USER_ID LIKE ?");
                    params.add("%" + searchCondition.getSearchKeyword() + "%");
                } else if ("userName".equals(searchType)) {
                    sql.append(" AND USER_NAME LIKE ?");
                    params.add("%" + searchCondition.getSearchKeyword() + "%");
                } else if ("email".equals(searchType)) {
                    sql.append(" AND EMAIL LIKE ?");
                    params.add("%" + searchCondition.getSearchKeyword() + "%");
                } else if ("phone".equals(searchType)) {
                    sql.append(" AND PHONE LIKE ?");
                    params.add("%" + searchCondition.getSearchKeyword() + "%");
                } else {
                    // 기본 검색 (모든 필드)
                    sql.append(" AND (USER_ID LIKE ? OR USER_NAME LIKE ? OR EMAIL LIKE ? OR PHONE LIKE ?)");
                    String keyword = "%" + searchCondition.getSearchKeyword() + "%";
                    params.add(keyword);
                    params.add(keyword);
                    params.add(keyword);
                    params.add(keyword);
                }
            }
            
            // 역할 필터
            if (searchCondition.getRoleFilter() != null && !searchCondition.getRoleFilter().trim().isEmpty()) {
                sql.append(" AND ROLE = ?");
                params.add(searchCondition.getRoleFilter());
            }
            
            // 상태 필터
            if (searchCondition.getStatusFilter() != null && !searchCondition.getStatusFilter().trim().isEmpty()) {
                sql.append(" AND STATUS = ?");
                params.add(searchCondition.getStatusFilter());
            }
            
            // 정렬
            sql.append(" ORDER BY CREATED_DATE DESC");
            
            // 페이징
            if (searchCondition.getPage() != null && searchCondition.getSize() != null) {
                int offset = (searchCondition.getPage() - 1) * searchCondition.getSize();
                sql.append(" LIMIT ? OFFSET ?");
                params.add(searchCondition.getSize());
                params.add(offset);
            }
            
            logger.info("생성된 SQL: " + sql.toString(), "UserMapper");
            logger.info("SQL 파라미터 개수: " + params.size(), "UserMapper");
            
            // 쿼리 실행
            List<User> result = jdbcTemplate.query(sql.toString(), params.toArray(), new UserRowMapper());
            
            logger.info("쿼리 실행 결과: " + (result != null ? "NOT NULL, 크기: " + result.size() : "NULL"), "UserMapper");
            logger.info("=== UserMapper.findBySearchCondition END ===", "UserMapper");
            
            return result;
            
        } catch (Exception e) {
            logger.error("UserMapper.findBySearchCondition 중 오류 발생: " + e.getMessage(), "UserMapper");
            logger.info("=== UserMapper.findBySearchCondition END (ERROR) ===", "UserMapper");
            throw e;
        }
    }
    
    /**
     * 검색 조건에 따른 사용자 수 조회
     */
    public int countBySearchCondition(UserDDTO searchCondition) {
        logger.info("=== UserMapper.countBySearchCondition START ===", "UserMapper");
        logger.info("입력 UserDDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"), "UserMapper");
        
        try {
            // NULL 체크
            if (searchCondition == null) {
                logger.warn("UserDDTO가 NULL입니다. 전체 개수를 조회합니다.", "UserMapper");
                String sql = "SELECT COUNT(*) FROM USER_INFO";
                int result = jdbcTemplate.queryForObject(sql, Integer.class);
                logger.info("전체 개수 조회 결과: " + result, "UserMapper");
                logger.info("=== UserMapper.countBySearchCondition END ===", "UserMapper");
                return result;
            }
            
            // 검색 조건에 따른 SQL 동적 생성
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM USER_INFO WHERE 1=1");
            List<Object> params = new java.util.ArrayList<>();
            
            // 검색 키워드 조건
            if (searchCondition.getSearchKeyword() != null && !searchCondition.getSearchKeyword().trim().isEmpty()) {
                String searchType = searchCondition.getSearchType();
                if ("userId".equals(searchType)) {
                    sql.append(" AND USER_ID LIKE ?");
                    params.add("%" + searchCondition.getSearchKeyword() + "%");
                } else if ("userName".equals(searchType)) {
                    sql.append(" AND USER_NAME LIKE ?");
                    params.add("%" + searchCondition.getSearchKeyword() + "%");
                } else if ("email".equals(searchType)) {
                    sql.append(" AND EMAIL LIKE ?");
                    params.add("%" + searchCondition.getSearchKeyword() + "%");
                } else if ("phone".equals(searchType)) {
                    sql.append(" AND PHONE LIKE ?");
                    params.add("%" + searchCondition.getSearchKeyword() + "%");
                } else {
                    // 기본 검색 (모든 필드)
                    sql.append(" AND (USER_ID LIKE ? OR USER_NAME LIKE ? OR EMAIL LIKE ? OR PHONE LIKE ?)");
                    String keyword = "%" + searchCondition.getSearchKeyword() + "%";
                    params.add(keyword);
                    params.add(keyword);
                    params.add(keyword);
                    params.add(keyword);
                }
            }
            
            // 역할 필터
            if (searchCondition.getRoleFilter() != null && !searchCondition.getRoleFilter().trim().isEmpty()) {
                sql.append(" AND ROLE = ?");
                params.add(searchCondition.getRoleFilter());
            }
            
            // 상태 필터
            if (searchCondition.getStatusFilter() != null && !searchCondition.getStatusFilter().trim().isEmpty()) {
                sql.append(" AND STATUS = ?");
                params.add(searchCondition.getStatusFilter());
            }
            
            logger.info("생성된 SQL: " + sql.toString(), "UserMapper");
            logger.info("SQL 파라미터 개수: " + params.size(), "UserMapper");
            
            int result = jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
            
            logger.info("COUNT 쿼리 실행 결과: " + result, "UserMapper");
            logger.info("=== UserMapper.countBySearchCondition END ===", "UserMapper");
            
            return result;
            
        } catch (Exception e) {
            logger.error("UserMapper.countBySearchCondition 중 오류 발생: " + e.getMessage(), "UserMapper");
            logger.info("=== UserMapper.countBySearchCondition END (ERROR) ===", "UserMapper");
            throw e;
        }
    }
    
    /**
     * ResultSet을 User 엔티티로 매핑하는 RowMapper
     */
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            logger.info("=== UserRowMapper.mapRow START ===", "UserMapper");
            logger.info("매핑할 행 번호: " + rowNum, "UserMapper");
            
            try {
                User user = new User();
                
                // 기본 정보 매핑
                user.setUserId(rs.getString("USER_ID"));
                user.setUserName(rs.getString("USER_NAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setPhone(rs.getString("PHONE"));
                user.setRole(rs.getString("ROLE"));
                user.setStatus(rs.getString("STATUS"));
                user.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                user.setUpdatedDate(rs.getTimestamp("UPDATED_DATE"));
                
                logger.info("매핑된 User - userId: " + (user.getUserId() != null ? user.getUserId() : "NULL"), "UserMapper");
                logger.info("매핑된 User - userName: " + (user.getUserName() != null ? user.getUserName() : "NULL"), "UserMapper");
                logger.info("매핑된 User - email: " + (user.getEmail() != null ? user.getEmail() : "NULL"), "UserMapper");
                
                logger.info("=== UserRowMapper.mapRow END ===", "UserMapper");
                return user;
                
            } catch (SQLException e) {
                logger.error("UserRowMapper.mapRow 중 SQL 오류 발생: " + e.getMessage(), "UserMapper");
                logger.info("=== UserRowMapper.mapRow END (SQL_ERROR) ===", "UserMapper");
                throw e;
            } catch (Exception e) {
                logger.error("UserRowMapper.mapRow 중 오류 발생: " + e.getMessage(), "UserMapper");
                logger.info("=== UserRowMapper.mapRow END (ERROR) ===", "UserMapper");
                throw new SQLException("User 매핑 중 오류가 발생했습니다.", e);
            }
        }
    }
}
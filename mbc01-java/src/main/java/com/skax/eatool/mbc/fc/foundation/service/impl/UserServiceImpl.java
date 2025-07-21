package com.skax.eatool.mbc.fc.foundation.service.impl;

import com.skax.eatool.mbc.fc.foundation.entity.User;
import com.skax.eatool.mbc.fc.foundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 사용자 서비스 구현 클래스
 * 
 * 프로그램명: UserServiceImpl.java
 * 설명: 사용자 관련 비즈니스 로직을 구현하는 서비스 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 사용자 CRUD 작업 구현
 * - 사용자 검색 기능 구현
 * - 비즈니스 로직 처리
 * 
 * @version 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getLong("USER_ID"));
            user.setUserName(rs.getString("USER_NAME"));
            user.setEmail(rs.getString("EMAIL"));
            user.setPhone(rs.getString("PHONE"));
            user.setStatus(User.UserStatus.valueOf(rs.getString("STATUS")));
            if (rs.getTimestamp("LAST_LOGIN_DATE") != null) {
                user.setLastLoginDate(rs.getTimestamp("LAST_LOGIN_DATE").toLocalDateTime());
            }
            if (rs.getTimestamp("CREATED_DATE") != null) {
                user.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
            }
            if (rs.getTimestamp("MODIFIED_DATE") != null) {
                user.setModifiedDate(rs.getTimestamp("MODIFIED_DATE").toLocalDateTime());
            }
            return user;
        }
    };

    @Override
    public User saveUser(User user) {
        if (user.getCreatedDate() == null) {
            user.setCreatedDate(LocalDateTime.now());
        }
        user.setModifiedDate(LocalDateTime.now());

        if (user.getUserId() == null) {
            // Insert new user
            String sql = "INSERT INTO USERS (USER_NAME, EMAIL, PHONE, STATUS, CREATED_DATE, MODIFIED_DATE) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    user.getUserName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getStatus().name(),
                    user.getCreatedDate(),
                    user.getModifiedDate());
        } else {
            // Update existing user
            String sql = "UPDATE USERS SET USER_NAME = ?, EMAIL = ?, PHONE = ?, STATUS = ?, MODIFIED_DATE = ? WHERE USER_ID = ?";
            jdbcTemplate.update(sql,
                    user.getUserName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getStatus().name(),
                    user.getModifiedDate(),
                    user.getUserId());
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long userId) {
        String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, userId);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        String sql = "SELECT * FROM USERS WHERE EMAIL = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, email);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        String sql = "SELECT * FROM USERS";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUsersByName(String userName) {
        String sql = "SELECT * FROM USERS WHERE USER_NAME LIKE ?";
        return jdbcTemplate.query(sql, userRowMapper, "%" + userName + "%");
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUsersByStatus(User.UserStatus status) {
        String sql = "SELECT * FROM USERS WHERE STATUS = ?";
        return jdbcTemplate.query(sql, userRowMapper, status.name());
    }

    @Override
    public void deleteUser(Long userId) {
        String sql = "DELETE FROM USERS WHERE USER_ID = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public User updateUserStatus(Long userId, User.UserStatus status) {
        Optional<User> userOpt = findUserById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setStatus(status);
            user.setModifiedDate(LocalDateTime.now());
            return saveUser(user);
        }
        throw new RuntimeException("User not found with ID: " + userId);
    }

    @Override
    public User updateLastLoginDate(Long userId, LocalDateTime loginDate) {
        Optional<User> userOpt = findUserById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setLastLoginDate(loginDate);
            user.setModifiedDate(LocalDateTime.now());
            return saveUser(user);
        }
        throw new RuntimeException("User not found with ID: " + userId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countActiveUsers() {
        String sql = "SELECT COUNT(*) FROM USERS WHERE STATUS = 'ACTIVE'";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUsersByRegistrationPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT * FROM USERS WHERE CREATED_DATE BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, userRowMapper, startDate, endDate);
    }
}
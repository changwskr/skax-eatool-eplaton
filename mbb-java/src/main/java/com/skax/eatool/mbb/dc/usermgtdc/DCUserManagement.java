package com.skax.eatool.mbb.dc.usermgtdc;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.mbb.dc.dto.UserDDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * MBBUSER 테이블 사용자 관리 Domain Component
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class DCUserManagement {
    
    private static final NewIKesaLogger logger = NewKesaLogHelper.getBiz();
    
    // 메모리 기반 사용자 저장소 (실제로는 데이터베이스 사용)
    private static final Map<String, UserDDTO> userStore = new HashMap<>();
    
    static {
        // 기본 admin 사용자 등록
        UserDDTO adminUser = new UserDDTO(
            "ADMIN_001",
            "admin",
            "admin123",
            "admin@skax.com",
            "ADMIN"
        );
        userStore.put(adminUser.getUserId(), adminUser);
        logger.info("DCUserManagement", "기본 admin 사용자가 등록되었습니다: " + adminUser.getUsername());
    }
    
    /**
     * 사용자를 등록한다.
     * 
     * @param user 사용자 정보
     * @return 등록된 사용자 정보
     * @throws NewBusinessException 비즈니스 예외
     */
    public UserDDTO createUser(UserDDTO user) throws NewBusinessException {
        logger.info("DCUserManagement", "=== DCUserManagement.createUser START ===");
        
        try {
            // 1. 사용자 ID 생성
            if (user.getUserId() == null || user.getUserId().trim().isEmpty()) {
                user.setUserId("USER_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
            }
            
            // 2. 중복 사용자명 검증
            if (isUsernameExists(user.getUsername())) {
                throw new NewBusinessException("이미 존재하는 사용자명입니다: " + user.getUsername());
            }
            
            // 3. 사용자 등록
            userStore.put(user.getUserId(), user);
            
            logger.info("DCUserManagement", "사용자 등록 완료: " + user.getUsername());
            logger.info("DCUserManagement", "=== DCUserManagement.createUser END ===");
            return user;
            
        } catch (NewBusinessException e) {
            logger.error("DCUserManagement", "=== DCUserManagement.createUser ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error("DCUserManagement", "=== DCUserManagement.createUser - Error: " + e.getMessage() + " ===");
            logger.error("DCUserManagement", "=== DCUserManagement.createUser ERROR ===");
            throw new NewBusinessException("사용자 등록 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 ID로 사용자를 조회한다.
     * 
     * @param userId 사용자 ID
     * @return 사용자 정보
     * @throws NewBusinessException 비즈니스 예외
     */
    public UserDDTO getUserById(String userId) throws NewBusinessException {
        logger.info("DCUserManagement", "=== DCUserManagement.getUserById START ===");
        
        try {
            UserDDTO user = userStore.get(userId);
            if (user == null) {
                throw new NewBusinessException("사용자를 찾을 수 없습니다: " + userId);
            }
            
            logger.info("DCUserManagement", "사용자 조회 완료: " + user.getUsername());
            logger.info("DCUserManagement", "=== DCUserManagement.getUserById END ===");
            return user;
            
        } catch (NewBusinessException e) {
            logger.error("DCUserManagement", "=== DCUserManagement.getUserById ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error("DCUserManagement", "=== DCUserManagement.getUserById - Error: " + e.getMessage() + " ===");
            logger.error("DCUserManagement", "=== DCUserManagement.getUserById ERROR ===");
            throw new NewBusinessException("사용자 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자명으로 사용자를 조회한다.
     * 
     * @param username 사용자명
     * @return 사용자 정보
     * @throws NewBusinessException 비즈니스 예외
     */
    public UserDDTO getUserByUsername(String username) throws NewBusinessException {
        logger.info("DCUserManagement", "=== DCUserManagement.getUserByUsername START ===");
        
        try {
            for (UserDDTO user : userStore.values()) {
                if (username.equals(user.getUsername())) {
                    logger.info("DCUserManagement", "사용자명 조회 완료: " + user.getUsername());
                    logger.info("DCUserManagement", "=== DCUserManagement.getUserByUsername END ===");
                    return user;
                }
            }
            
            throw new NewBusinessException("사용자를 찾을 수 없습니다: " + username);
            
        } catch (NewBusinessException e) {
            logger.error("DCUserManagement", "=== DCUserManagement.getUserByUsername ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error("DCUserManagement", "=== DCUserManagement.getUserByUsername - Error: " + e.getMessage() + " ===");
            logger.error("DCUserManagement", "=== DCUserManagement.getUserByUsername ERROR ===");
            throw new NewBusinessException("사용자명 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 모든 사용자를 조회한다.
     * 
     * @return 사용자 목록
     * @throws NewBusinessException 비즈니스 예외
     */
    public List<UserDDTO> getAllUsers() throws NewBusinessException {
        logger.info("DCUserManagement", "=== DCUserManagement.getAllUsers START ===");
        
        try {
            List<UserDDTO> users = new ArrayList<>(userStore.values());
            logger.info("DCUserManagement", "전체 사용자 조회 완료: " + users.size() + "명");
            logger.info("DCUserManagement", "=== DCUserManagement.getAllUsers END ===");
            return users;
            
        } catch (Exception e) {
            logger.error("DCUserManagement", "=== DCUserManagement.getAllUsers - Error: " + e.getMessage() + " ===");
            logger.error("DCUserManagement", "=== DCUserManagement.getAllUsers ERROR ===");
            throw new NewBusinessException("전체 사용자 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 정보를 수정한다.
     * 
     * @param userId 사용자 ID
     * @param user 수정할 사용자 정보
     * @return 수정된 사용자 정보
     * @throws NewBusinessException 비즈니스 예외
     */
    public UserDDTO updateUser(String userId, UserDDTO user) throws NewBusinessException {
        logger.info("DCUserManagement", "=== DCUserManagement.updateUser START ===");
        
        try {
            // 1. 기존 사용자 확인
            UserDDTO existingUser = getUserById(userId);
            
            // 2. 사용자 정보 업데이트
            if (user.getUsername() != null) {
                existingUser.setUsername(user.getUsername());
            }
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getRole() != null) {
                existingUser.setRole(user.getRole());
            }
            if (user.getPassword() != null) {
                existingUser.setPassword(user.getPassword());
            }
            existingUser.setUpdatedAt(java.time.LocalDateTime.now());
            userStore.put(existingUser.getUserId(), existingUser);
            
            logger.info("DCUserManagement", "사용자 업데이트 완료: " + existingUser.getUsername());
            logger.info("DCUserManagement", "=== DCUserManagement.updateUser END ===");
            return existingUser;
            
        } catch (NewBusinessException e) {
            logger.error("DCUserManagement", "=== DCUserManagement.updateUser ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error("DCUserManagement", "=== DCUserManagement.updateUser - Error: " + e.getMessage() + " ===");
            logger.error("DCUserManagement", "=== DCUserManagement.updateUser ERROR ===");
            throw new NewBusinessException("사용자 업데이트 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자를 삭제한다.
     * 
     * @param userId 사용자 ID
     * @return 삭제 성공 여부
     * @throws NewBusinessException 비즈니스 예외
     */
    public boolean deleteUser(String userId) throws NewBusinessException {
        logger.info("DCUserManagement", "=== DCUserManagement.deleteUser START ===");
        
        try {
            // 1. 기존 사용자 확인
            UserDDTO existingUser = getUserById(userId);
            
            // 2. 사용자 삭제
            userStore.remove(userId);
            
            logger.info("DCUserManagement", "사용자 삭제 완료: " + existingUser.getUsername());
            logger.info("DCUserManagement", "=== DCUserManagement.deleteUser END ===");
            return true;
            
        } catch (NewBusinessException e) {
            logger.error("DCUserManagement", "=== DCUserManagement.deleteUser ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error("DCUserManagement", "=== DCUserManagement.deleteUser - Error: " + e.getMessage() + " ===");
            logger.error("DCUserManagement", "=== DCUserManagement.deleteUser ERROR ===");
            throw new NewBusinessException("사용자 삭제 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 인증을 수행한다.
     * 
     * @param username 사용자명
     * @param password 비밀번호
     * @return 인증된 사용자 정보
     * @throws NewBusinessException 비즈니스 예외
     */
    public UserDDTO authenticateUser(String username, String password) throws NewBusinessException {
        logger.info("DCUserManagement", "=== DCUserManagement.authenticateUser START ===");
        
        try {
            // 1. 사용자 조회
            UserDDTO user = getUserByUsername(username);
            
            // 2. 비밀번호 검증
            if (!password.equals(user.getPassword())) {
                throw new NewBusinessException("비밀번호가 일치하지 않습니다.");
            }
            
            // 3. 사용자 상태 확인
            if (!"ACTIVE".equals(user.getStatus())) {
                throw new NewBusinessException("비활성화된 사용자입니다: " + username);
            }
            
            logger.info("DCUserManagement", "사용자 인증 성공: " + user.getUsername());
            logger.info("DCUserManagement", "=== DCUserManagement.authenticateUser END ===");
            return user;
            
        } catch (NewBusinessException e) {
            logger.error("DCUserManagement", "=== DCUserManagement.authenticateUser ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error("DCUserManagement", "=== DCUserManagement.authenticateUser - Error: " + e.getMessage() + " ===");
            logger.error("DCUserManagement", "=== DCUserManagement.authenticateUser ERROR ===");
            throw new NewBusinessException("사용자 인증 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자명이 이미 존재하는지 확인한다.
     * 
     * @param username 사용자명
     * @return 존재 여부
     */
    private boolean isUsernameExists(String username) {
        for (UserDDTO user : userStore.values()) {
            if (username.equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }
} 
package com.skax.eatool.mba.pc.usermgmtpc;

import com.skax.eatool.mba.as.usermgmtas.dto.CreateUserRequestDto;
import com.skax.eatool.mba.as.usermgmtas.dto.PageRequestDto;
import com.skax.eatool.mba.as.usermgmtas.dto.UserDto;
import com.skax.eatool.mba.dc.usermgmtdc.UserDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 사용자 프로세스 컴포넌트
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@Component
public class UserPC {
    
    private static final Logger logger = LoggerFactory.getLogger(UserPC.class);
    
    @Autowired
    private UserDC userDC;
    
    /**
     * 사용자 목록 조회
     * 
     * @param pageRequest 페이지 요청 정보
     * @return 사용자 목록
     */
    public List<UserDto> getUserList(PageRequestDto pageRequest) {
        logger.info("=== UserPC.getUserList START ===");
        logger.info("페이지 요청: {}", pageRequest);
        
        try {
            List<UserDto> userList = userDC.getUserList(pageRequest);
            logger.info("조회된 사용자 수: {}", userList != null ? userList.size() : 0);
            logger.info("=== UserPC.getUserList END ===");
            return userList;
            
        } catch (Exception e) {
            logger.error("UserPC.getUserList 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 사용자 총 개수 조회
     * 
     * @param pageRequest 페이지 요청 정보
     * @return 사용자 총 개수
     */
    public Long getUserCount(PageRequestDto pageRequest) {
        logger.info("=== UserPC.getUserCount START ===");
        
        try {
            Long count = userDC.getUserCount(pageRequest);
            logger.info("사용자 총 개수: {}", count);
            logger.info("=== UserPC.getUserCount END ===");
            return count;
            
        } catch (Exception e) {
            logger.error("UserPC.getUserCount 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 사용자 ID로 조회
     * 
     * @param userId 사용자 ID
     * @return 사용자 정보
     */
    public UserDto getUserById(Long userId) {
        logger.info("=== UserPC.getUserById START ===");
        logger.info("조회할 사용자 ID: {}", userId);
        
        try {
            UserDto user = userDC.getUserById(userId);
            logger.info("조회 결과: {}", user != null ? "성공" : "실패");
            logger.info("=== UserPC.getUserById END ===");
            return user;
            
        } catch (Exception e) {
            logger.error("UserPC.getUserById 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 사용자명으로 조회
     * 
     * @param username 사용자명
     * @return 사용자 정보
     */
    public UserDto getUserByUsername(String username) {
        logger.info("=== UserPC.getUserByUsername START ===");
        logger.info("조회할 사용자명: {}", username);
        
        try {
            UserDto user = userDC.getUserByUsername(username);
            logger.info("조회 결과: {}", user != null ? "성공" : "실패");
            logger.info("=== UserPC.getUserByUsername END ===");
            return user;
            
        } catch (Exception e) {
            logger.error("UserPC.getUserByUsername 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 이메일로 조회
     * 
     * @param email 이메일
     * @return 사용자 정보
     */
    public UserDto getUserByEmail(String email) {
        logger.info("=== UserPC.getUserByEmail START ===");
        logger.info("조회할 이메일: {}", email);
        
        try {
            UserDto user = userDC.getUserByEmail(email);
            logger.info("조회 결과: {}", user != null ? "성공" : "실패");
            logger.info("=== UserPC.getUserByEmail END ===");
            return user;
            
        } catch (Exception e) {
            logger.error("UserPC.getUserByEmail 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 사용자 검색
     * 
     * @param pageRequest 페이지 요청 정보 (검색 조건 포함)
     * @return 검색된 사용자 목록
     */
    public List<UserDto> searchUsers(PageRequestDto pageRequest) {
        logger.info("=== UserPC.searchUsers START ===");
        logger.info("검색 조건: {}", pageRequest);
        
        try {
            List<UserDto> userList = userDC.searchUsers(pageRequest);
            logger.info("검색된 사용자 수: {}", userList != null ? userList.size() : 0);
            logger.info("=== UserPC.searchUsers END ===");
            return userList;
            
        } catch (Exception e) {
            logger.error("UserPC.searchUsers 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 검색된 사용자 총 개수 조회
     * 
     * @param pageRequest 페이지 요청 정보 (검색 조건 포함)
     * @return 검색된 사용자 총 개수
     */
    public Long getSearchUserCount(PageRequestDto pageRequest) {
        logger.info("=== UserPC.getSearchUserCount START ===");
        
        try {
            Long count = userDC.getSearchUserCount(pageRequest);
            logger.info("검색된 사용자 총 개수: {}", count);
            logger.info("=== UserPC.getSearchUserCount END ===");
            return count;
            
        } catch (Exception e) {
            logger.error("UserPC.getSearchUserCount 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 사용자 생성
     * 
     * @param createRequest 사용자 생성 요청
     * @return 생성된 사용자 정보
     */
    public UserDto createUser(CreateUserRequestDto createRequest) {
        logger.info("=== UserPC.createUser START ===");
        logger.info("생성 요청: {}", createRequest);
        
        try {
            UserDto createdUser = userDC.createUser(createRequest);
            logger.info("생성 결과: {}", createdUser != null ? "성공" : "실패");
            logger.info("=== UserPC.createUser END ===");
            return createdUser;
            
        } catch (Exception e) {
            logger.error("UserPC.createUser 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 사용자 수정
     * 
     * @param userId 사용자 ID
     * @param updateRequest 사용자 수정 요청
     * @return 수정된 사용자 정보
     */
    public UserDto updateUser(Long userId, com.skax.eatool.mba.as.usermgmtas.dto.UpdateUserRequestDto updateRequest) {
        logger.info("=== UserPC.updateUser START ===");
        logger.info("수정할 사용자 ID: {}", userId);
        logger.info("수정 요청: {}", updateRequest);
        
        try {
            UserDto updatedUser = userDC.updateUser(userId, updateRequest);
            logger.info("수정 결과: {}", updatedUser != null ? "성공" : "실패");
            logger.info("=== UserPC.updateUser END ===");
            return updatedUser;
            
        } catch (Exception e) {
            logger.error("UserPC.updateUser 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 사용자 삭제
     * 
     * @param userId 사용자 ID
     * @return 삭제 성공 여부
     */
    public boolean deleteUser(Long userId) {
        logger.info("=== UserPC.deleteUser START ===");
        logger.info("삭제할 사용자 ID: {}", userId);
        
        try {
            boolean isDeleted = userDC.deleteUser(userId);
            logger.info("삭제 결과: {}", isDeleted ? "성공" : "실패");
            logger.info("=== UserPC.deleteUser END ===");
            return isDeleted;
            
        } catch (Exception e) {
            logger.error("UserPC.deleteUser 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
} 
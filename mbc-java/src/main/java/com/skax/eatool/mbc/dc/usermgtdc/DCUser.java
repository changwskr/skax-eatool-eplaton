package com.skax.eatool.mbc.dc.usermgtdc;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbc.dc.usermgtdc.dto.UserDDTO;
import com.skax.eatool.mbc.dc.usermgtdc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 사용자 관리 Domain Component
 * 
 * 프로그램명: DCUser.java
 * 설명: 사용자 관리 비즈니스 로직을 담당하는 Domain Component
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
@Component
public class DCUser {
    
    private static final NewIKesaLogger logger = NewKesaLogHelper.getBiz();
    
    @Autowired
    private UserMapper userMapper;
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 사용자 목록 조회
     */
    public List<UserDDTO> getListUser(UserDDTO searchCondition) throws NewBusinessException {
        logger.info("=== DCUser.getListUser START ===", "DCUser");
        logger.info("입력 UserDDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"), "DCUser");
        
        if (searchCondition != null) {
            logger.info("입력 UserDDTO - searchKeyword: " + (searchCondition.getSearchKeyword() != null ? searchCondition.getSearchKeyword() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - searchType: " + (searchCondition.getSearchType() != null ? searchCondition.getSearchType() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - roleFilter: " + (searchCondition.getRoleFilter() != null ? searchCondition.getRoleFilter() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - statusFilter: " + (searchCondition.getStatusFilter() != null ? searchCondition.getStatusFilter() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - page: " + (searchCondition.getPage() != null ? searchCondition.getPage() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - size: " + (searchCondition.getSize() != null ? searchCondition.getSize() : "NULL"), "DCUser");
        }
        
        try {
            // NULL 체크
            if (searchCondition == null) {
                logger.error("UserDDTO가 NULL입니다.", "DCUser");
                throw new NewBusinessException("UserDDTO는 필수입니다.");
            }
            
            // 검색 조건에 따른 사용자 목록 조회
            List<User> userList = userMapper.findBySearchCondition(searchCondition);
            
            logger.info("Mapper 호출 결과 - userList: " + (userList != null ? "NOT NULL, 크기: " + userList.size() : "NULL"), "DCUser");
            
            // User 엔티티를 UserDDTO로 변환
            List<UserDDTO> resultList = new ArrayList<>();
            if (userList != null) {
                for (User user : userList) {
                    UserDDTO dto = convertToUserDDTO(user);
                    resultList.add(dto);
                }
            }
            
            logger.info("변환된 UserDDTO 리스트: " + (resultList != null ? "NOT NULL, 크기: " + resultList.size() : "NULL"), "DCUser");
            logger.info("=== DCUser.getListUser END ===", "DCUser");
            
            return resultList;
            
        } catch (NewBusinessException e) {
            logger.error("DCUser.getListUser 중 비즈니스 오류: " + e.getMessage(), "DCUser");
            logger.info("=== DCUser.getListUser END (BUSINESS_ERROR) ===", "DCUser");
            throw e;
        } catch (Exception e) {
            logger.error("DCUser.getListUser 중 오류 발생: " + e.getMessage(), "DCUser");
            logger.info("=== DCUser.getListUser END (ERROR) ===", "DCUser");
            throw new NewBusinessException("사용자 목록 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 상세 조회
     */
    public UserDDTO getUser(String userId) throws NewBusinessException {
        logger.info("=== DCUser.getUser START ===", "DCUser");
        logger.info("입력 userId: " + (userId != null ? userId : "NULL"), "DCUser");
        
        try {
            // NULL 체크
            if (userId == null || userId.trim().isEmpty()) {
                logger.error("userId가 NULL이거나 비어있습니다.", "DCUser");
                throw new NewBusinessException("userId는 필수입니다.");
            }
            
            User user = userMapper.findByUserId(userId);
            
            logger.info("Mapper 호출 결과 - user: " + (user != null ? "NOT NULL" : "NULL"), "DCUser");
            if (user != null) {
                logger.info("Mapper 호출 결과 - user.userId: " + (user.getUserId() != null ? user.getUserId() : "NULL"), "DCUser");
                logger.info("Mapper 호출 결과 - user.userName: " + (user.getUserName() != null ? user.getUserName() : "NULL"), "DCUser");
            }
            
            if (user == null) {
                logger.error("사용자를 찾을 수 없습니다. ID: " + userId, "DCUser");
                throw new NewBusinessException("사용자를 찾을 수 없습니다. ID: " + userId);
            }
            
            UserDDTO result = convertToUserDDTO(user);
            
            logger.info("변환된 UserDDTO: " + (result != null ? "NOT NULL" : "NULL"), "DCUser");
            if (result != null) {
                logger.info("변환된 UserDDTO - userId: " + (result.getUserId() != null ? result.getUserId() : "NULL"), "DCUser");
                logger.info("변환된 UserDDTO - userName: " + (result.getUserName() != null ? result.getUserName() : "NULL"), "DCUser");
            }
            
            logger.info("=== DCUser.getUser END ===", "DCUser");
            
            return result;
            
        } catch (NewBusinessException e) {
            logger.error("DCUser.getUser 중 비즈니스 오류: " + e.getMessage(), "DCUser");
            logger.info("=== DCUser.getUser END (BUSINESS_ERROR) ===", "DCUser");
            throw e;
        } catch (Exception e) {
            logger.error("DCUser.getUser 중 오류 발생: " + e.getMessage(), "DCUser");
            logger.info("=== DCUser.getUser END (ERROR) ===", "DCUser");
            throw new NewBusinessException("사용자 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 등록
     */
    public void createUser(UserDDTO userDDTO) throws NewBusinessException {
        logger.info("=== DCUser.createUser START ===", "DCUser");
        logger.info("입력 UserDDTO: " + (userDDTO != null ? "NOT NULL" : "NULL"), "DCUser");
        
        if (userDDTO != null) {
            logger.info("입력 UserDDTO - userId: " + (userDDTO.getUserId() != null ? userDDTO.getUserId() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - userName: " + (userDDTO.getUserName() != null ? userDDTO.getUserName() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - email: " + (userDDTO.getEmail() != null ? userDDTO.getEmail() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - phone: " + (userDDTO.getPhone() != null ? userDDTO.getPhone() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - role: " + (userDDTO.getRole() != null ? userDDTO.getRole() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - status: " + (userDDTO.getStatus() != null ? userDDTO.getStatus() : "NULL"), "DCUser");
        }
        
        try {
            // NULL 체크
            if (userDDTO == null) {
                logger.error("UserDDTO가 NULL입니다.", "DCUser");
                throw new NewBusinessException("UserDDTO는 필수입니다.");
            }
            
            if (userDDTO.getUserId() == null || userDDTO.getUserId().trim().isEmpty()) {
                logger.error("사용자 ID가 NULL이거나 비어있습니다.", "DCUser");
                throw new NewBusinessException("사용자 ID는 필수입니다.");
            }
            
            // 이메일 중복 체크
            User existingUser = userMapper.findByEmail(userDDTO.getEmail());
            if (existingUser != null) {
                logger.error("이미 존재하는 이메일입니다: " + userDDTO.getEmail(), "DCUser");
                throw new NewBusinessException("이미 존재하는 이메일입니다: " + userDDTO.getEmail());
            }
            
            // UserDDTO를 User 엔티티로 변환
            User user = convertToUser(userDDTO);
            
            logger.info("변환된 User 엔티티: " + (user != null ? "NOT NULL" : "NULL"), "DCUser");
            if (user != null) {
                logger.info("변환된 User 엔티티 - userId: " + (user.getUserId() != null ? user.getUserId() : "NULL"), "DCUser");
                logger.info("변환된 User 엔티티 - userName: " + (user.getUserName() != null ? user.getUserName() : "NULL"), "DCUser");
            }
            
            // Mapper 호출
            int result = userMapper.insert(user);
            
            logger.info("Mapper 호출 결과 - insert 결과: " + result, "DCUser");
            logger.info("사용자 등록 완료: " + userDDTO.getUserName(), "DCUser");
            logger.info("=== DCUser.createUser END ===", "DCUser");
            
        } catch (NewBusinessException e) {
            logger.error("DCUser.createUser 중 비즈니스 오류: " + e.getMessage(), "DCUser");
            logger.info("=== DCUser.createUser END (BUSINESS_ERROR) ===", "DCUser");
            throw e;
        } catch (Exception e) {
            logger.error("DCUser.createUser 중 오류 발생: " + e.getMessage(), "DCUser");
            logger.info("=== DCUser.createUser END (ERROR) ===", "DCUser");
            throw new NewBusinessException("사용자 등록 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 수정
     */
    public void updateUser(UserDDTO userDDTO) throws NewBusinessException {
        logger.info("=== DCUser.updateUser START ===", "DCUser");
        logger.info("입력 UserDDTO: " + (userDDTO != null ? "NOT NULL" : "NULL"), "DCUser");
        
        if (userDDTO != null) {
            logger.info("입력 UserDDTO - userId: " + (userDDTO.getUserId() != null ? userDDTO.getUserId() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - userName: " + (userDDTO.getUserName() != null ? userDDTO.getUserName() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - email: " + (userDDTO.getEmail() != null ? userDDTO.getEmail() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - phone: " + (userDDTO.getPhone() != null ? userDDTO.getPhone() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - role: " + (userDDTO.getRole() != null ? userDDTO.getRole() : "NULL"), "DCUser");
            logger.info("입력 UserDDTO - status: " + (userDDTO.getStatus() != null ? userDDTO.getStatus() : "NULL"), "DCUser");
        }
        
        try {
            // NULL 체크
            if (userDDTO == null) {
                logger.error("UserDDTO가 NULL입니다.", "DCUser");
                throw new NewBusinessException("UserDDTO는 필수입니다.");
            }
            
            if (userDDTO.getUserId() == null || userDDTO.getUserId().trim().isEmpty()) {
                logger.error("사용자 ID가 NULL이거나 비어있습니다.", "DCUser");
                throw new NewBusinessException("사용자 ID는 필수입니다.");
            }
            
            // 기존 사용자 존재 여부 확인
            User existingUser = userMapper.findByUserId(userDDTO.getUserId());
            if (existingUser == null) {
                logger.error("수정할 사용자를 찾을 수 없습니다. ID: " + userDDTO.getUserId(), "DCUser");
                throw new NewBusinessException("수정할 사용자를 찾을 수 없습니다. ID: " + userDDTO.getUserId());
            }
            
            // 이메일 중복 체크 (자신 제외)
            if (userDDTO.getEmail() != null && !userDDTO.getEmail().equals(existingUser.getEmail())) {
                User emailUser = userMapper.findByEmail(userDDTO.getEmail());
                if (emailUser != null) {
                    logger.error("이미 존재하는 이메일입니다: " + userDDTO.getEmail(), "DCUser");
                    throw new NewBusinessException("이미 존재하는 이메일입니다: " + userDDTO.getEmail());
                }
            }
            
            // UserDDTO를 User 엔티티로 변환
            User user = convertToUser(userDDTO);
            user.setUpdatedDate(new Date());
            
            logger.info("변환된 User 엔티티: " + (user != null ? "NOT NULL" : "NULL"), "DCUser");
            if (user != null) {
                logger.info("변환된 User 엔티티 - userId: " + (user.getUserId() != null ? user.getUserId() : "NULL"), "DCUser");
                logger.info("변환된 User 엔티티 - userName: " + (user.getUserName() != null ? user.getUserName() : "NULL"), "DCUser");
            }
            
            // Mapper 호출
            int result = userMapper.update(user);
            
            logger.info("Mapper 호출 결과 - update 결과: " + result, "DCUser");
            logger.info("사용자 수정 완료: " + userDDTO.getUserName(), "DCUser");
            logger.info("=== DCUser.updateUser END ===", "DCUser");
            
        } catch (NewBusinessException e) {
            logger.error("DCUser.updateUser 중 비즈니스 오류: " + e.getMessage(), "DCUser");
            logger.info("=== DCUser.updateUser END (BUSINESS_ERROR) ===", "DCUser");
            throw e;
        } catch (Exception e) {
            logger.error("DCUser.updateUser 중 오류 발생: " + e.getMessage(), "DCUser");
            logger.info("=== DCUser.updateUser END (ERROR) ===", "DCUser");
            throw new NewBusinessException("사용자 수정 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 삭제
     */
    public void deleteUser(String userId) throws NewBusinessException {
        logger.info("=== DCUser.deleteUser START ===", "DCUser");
        logger.info("입력 userId: " + (userId != null ? userId : "NULL"), "DCUser");
        
        try {
            // NULL 체크
            if (userId == null || userId.trim().isEmpty()) {
                logger.error("userId가 NULL이거나 비어있습니다.", "DCUser");
                throw new NewBusinessException("userId는 필수입니다.");
            }
            
            // 기존 사용자 존재 여부 확인
            User existingUser = userMapper.findByUserId(userId);
            if (existingUser == null) {
                logger.error("삭제할 사용자를 찾을 수 없습니다. ID: " + userId, "DCUser");
                throw new NewBusinessException("삭제할 사용자를 찾을 수 없습니다. ID: " + userId);
            }
            
            logger.info("삭제할 사용자 정보 - userName: " + (existingUser.getUserName() != null ? existingUser.getUserName() : "NULL"), "DCUser");
            
            // Mapper 호출
            int result = userMapper.delete(userId);
            
            logger.info("Mapper 호출 결과 - delete 결과: " + result, "DCUser");
            logger.info("사용자 삭제 완료: " + userId, "DCUser");
            logger.info("=== DCUser.deleteUser END ===", "DCUser");
            
        } catch (NewBusinessException e) {
            logger.error("DCUser.deleteUser 중 비즈니스 오류: " + e.getMessage(), "DCUser");
            logger.info("=== DCUser.deleteUser END (BUSINESS_ERROR) ===", "DCUser");
            throw e;
        } catch (Exception e) {
            logger.error("DCUser.deleteUser 중 오류 발생: " + e.getMessage(), "DCUser");
            logger.info("=== DCUser.deleteUser END (ERROR) ===", "DCUser");
            throw new NewBusinessException("사용자 삭제 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * User 엔티티를 UserDDTO로 변환
     */
    private UserDDTO convertToUserDDTO(User user) {
        logger.info("=== DCUser.convertToUserDDTO START ===", "DCUser");
        logger.info("입력 User 엔티티: " + (user != null ? "NOT NULL" : "NULL"), "DCUser");
        
        if (user == null) {
            logger.warn("입력 User 엔티티가 NULL입니다.", "DCUser");
            logger.info("=== DCUser.convertToUserDDTO END (NULL_INPUT) ===", "DCUser");
            return null;
        }
        
        try {
            UserDDTO dto = new UserDDTO();
            
            // 기본 정보 변환
            dto.setUserId(user.getUserId());
            dto.setUserName(user.getUserName());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getPhone());
            dto.setRole(user.getRole());
            dto.setStatus(user.getStatus());
            dto.setCreatedDate(user.getCreatedDate());
            dto.setUpdatedDate(user.getUpdatedDate());
            
            // 상세 정보 변환
            dto.setDepartment(user.getDepartment());
            dto.setPosition(user.getPosition());
            dto.setEmployeeId(user.getEmployeeId());
            dto.setBirthDate(user.getBirthDate());
            dto.setAddress(user.getAddress());
            dto.setEmergencyContact(user.getEmergencyContact());
            dto.setEmergencyContactName(user.getEmergencyContactName());
            dto.setProfileImageUrl(user.getProfileImageUrl());
            dto.setLastLoginDate(user.getLastLoginDate());
            dto.setLoginCount(user.getLoginCount());
            
            logger.info("변환된 UserDDTO: " + (dto != null ? "NOT NULL" : "NULL"), "DCUser");
            if (dto != null) {
                logger.info("변환된 UserDDTO - userId: " + (dto.getUserId() != null ? dto.getUserId() : "NULL"), "DCUser");
                logger.info("변환된 UserDDTO - userName: " + (dto.getUserName() != null ? dto.getUserName() : "NULL"), "DCUser");
            }
            
            logger.info("=== DCUser.convertToUserDDTO END ===", "DCUser");
            return dto;
            
        } catch (Exception e) {
            logger.error("User 엔티티를 UserDDTO로 변환 중 오류 발생: " + e.getMessage(), "DCUser");
            logger.info("=== DCUser.convertToUserDDTO END (ERROR) ===", "DCUser");
            return null;
        }
    }
    
    /**
     * UserDDTO를 User 엔티티로 변환
     */
    private User convertToUser(UserDDTO dto) {
        logger.info("=== DCUser.convertToUser START ===", "DCUser");
        logger.info("입력 UserDDTO: " + (dto != null ? "NOT NULL" : "NULL"), "DCUser");
        
        if (dto == null) {
            logger.warn("입력 UserDDTO가 NULL입니다.", "DCUser");
            logger.info("=== DCUser.convertToUser END (NULL_INPUT) ===", "DCUser");
            return null;
        }
        
        try {
            User user = new User();
            
            // 기본 정보 변환
            user.setUserId(dto.getUserId());
            user.setUserName(dto.getUserName());
            user.setEmail(dto.getEmail());
            user.setPhone(dto.getPhone());
            user.setRole(dto.getRole());
            user.setStatus(dto.getStatus());
            user.setCreatedDate(dto.getCreatedDate());
            user.setUpdatedDate(dto.getUpdatedDate());
            
            // 상세 정보 변환
            user.setDepartment(dto.getDepartment());
            user.setPosition(dto.getPosition());
            user.setEmployeeId(dto.getEmployeeId());
            user.setBirthDate(dto.getBirthDate());
            user.setAddress(dto.getAddress());
            user.setEmergencyContact(dto.getEmergencyContact());
            user.setEmergencyContactName(dto.getEmergencyContactName());
            user.setProfileImageUrl(dto.getProfileImageUrl());
            user.setLastLoginDate(dto.getLastLoginDate());
            user.setLoginCount(dto.getLoginCount());
            
            logger.info("변환된 User 엔티티: " + (user != null ? "NOT NULL" : "NULL"), "DCUser");
            if (user != null) {
                logger.info("변환된 User 엔티티 - userId: " + (user.getUserId() != null ? user.getUserId() : "NULL"), "DCUser");
                logger.info("변환된 User 엔티티 - userName: " + (user.getUserName() != null ? user.getUserName() : "NULL"), "DCUser");
            }
            
            logger.info("=== DCUser.convertToUser END ===", "DCUser");
            return user;
            
        } catch (Exception e) {
            logger.error("UserDDTO를 User 엔티티로 변환 중 오류 발생: " + e.getMessage(), "DCUser");
            logger.info("=== DCUser.convertToUser END (ERROR) ===", "DCUser");
            return null;
        }
    }
}

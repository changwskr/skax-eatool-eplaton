package com.skax.eatool.mbc.dc.usermgtdc.repository.impl;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbc.dc.usermgtdc.User;
import com.skax.eatool.mbc.dc.usermgtdc.dto.PageDDTO;
import com.skax.eatool.mbc.dc.usermgtdc.dto.TreeDDTO;
import com.skax.eatool.mbc.dc.usermgtdc.dto.UserDDTO;
import com.skax.eatool.mbc.dc.usermgtdc.mapper.UserMapper;
import com.skax.eatool.mbc.dc.usermgtdc.repository.UserRepository;
import com.skax.eatool.mbc.dc.usermgtdc.Page;
import com.skax.eatool.mbc.dc.usermgtdc.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 사용자 관리 MyBatis Repository 구현체
 * 
 * 프로그램명: UserMyBatisRepository.java
 * 설명: MyBatis를 사용한 사용자 관리 Repository 구현
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 사용자 목록 조회
 * - 사용자 상세 조회
 * - 사용자 등록/수정/삭제
 * - 페이징 및 트리 구조 지원
 * 
 * @version 1.0
 */
@Repository
@Profile("mybatis")
public class UserMyBatisRepository implements UserRepository {
    
    private NewIKesaLogger logger = NewKesaLogHelper.getBiz();
    private static final Logger slf4jLogger = LoggerFactory.getLogger(UserMyBatisRepository.class);
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public List<User> getListUser(UserDDTO userDDTO) throws NewBusinessException {
        logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getListUser START ===");
        slf4jLogger.info("=== UserMyBatisRepository.getListUser START (SLF4J) ===");
        try {
            logger.debug("UserMyBatisRepository.getListUser called");
            List<User> userList = userMapper.findBySearchCondition(userDDTO);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getListUser END ===");
            slf4jLogger.info("=== UserMyBatisRepository.getListUser END (SLF4J) ===");
            return userList; // NewObjectUtil 사용하지 않음
        } catch (Exception e) {
            logger.error("Error in getListUser: " + e.getMessage(), String.valueOf(e));
            slf4jLogger.error("Error in getListUser: " + e.getMessage(), e);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getListUser END ===");
            slf4jLogger.info("=== UserMyBatisRepository.getListUser END (SLF4J) ===");
            throw new NewBusinessException("B0000001", "getListUser", e);
        }
    }

    @Override
    public int insertUser(UserDDTO userDDTO) throws NewBusinessException {
        logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.insertUser START ===");
        slf4jLogger.info("=== UserMyBatisRepository.insertUser START (SLF4J) ===");
        try {
            logger.debug("UserMyBatisRepository.insertUser called");
            // UserDDTO를 User 엔티티로 변환
            User user = convertToUser(userDDTO);
            int result = userMapper.insert(user);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.insertUser END ===");
            slf4jLogger.info("=== UserMyBatisRepository.insertUser END (SLF4J) ===");
            return result;
        } catch (Exception e) {
            logger.error("Error in insertUser: " + e.getMessage(), String.valueOf(e));
            slf4jLogger.error("Error in insertUser: " + e.getMessage(), e);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.insertUser END ===");
            slf4jLogger.info("=== UserMyBatisRepository.insertUser END (SLF4J) ===");
            throw new NewBusinessException("B0000002", "insertUser", e);
        }
    }

    @Override
    public int updateUser(UserDDTO userDDTO) throws NewBusinessException {
        logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.updateUser START ===");
        slf4jLogger.info("=== UserMyBatisRepository.updateUser START (SLF4J) ===");
        try {
            logger.debug("UserMyBatisRepository.updateUser called");
            // UserDDTO를 User 엔티티로 변환
            User user = convertToUser(userDDTO);
            int result = userMapper.update(user);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.updateUser END ===");
            slf4jLogger.info("=== UserMyBatisRepository.updateUser END (SLF4J) ===");
            return result;
        } catch (Exception e) {
            logger.error("Error in updateUser: " + e.getMessage(), String.valueOf(e));
            slf4jLogger.error("Error in updateUser: " + e.getMessage(), e);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.updateUser END ===");
            slf4jLogger.info("=== UserMyBatisRepository.updateUser END (SLF4J) ===");
            throw new NewBusinessException("B0000003", "updateUser", e);
        }
    }

    @Override
    public int deleteUser(UserDDTO userDDTO) throws NewBusinessException {
        logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.deleteUser START ===");
        slf4jLogger.info("=== UserMyBatisRepository.deleteUser START (SLF4J) ===");
        try {
            logger.debug("UserMyBatisRepository.deleteUser called");
            String userId = userDDTO.getUserId();
            int result = userMapper.delete(userId);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.deleteUser END ===");
            slf4jLogger.info("=== UserMyBatisRepository.deleteUser END (SLF4J) ===");
            return result;
        } catch (Exception e) {
            logger.error("Error in deleteUser: " + e.getMessage(), String.valueOf(e));
            slf4jLogger.error("Error in deleteUser: " + e.getMessage(), e);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.deleteUser END ===");
            slf4jLogger.info("=== UserMyBatisRepository.deleteUser END (SLF4J) ===");
            throw new NewBusinessException("B0000004", "deleteUser", e);
        }
    }

    @Override
    public List<Page> getListPage(PageDDTO pageDDTO) throws NewBusinessException {
        logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getListPage START ===");
        slf4jLogger.info("=== UserMyBatisRepository.getListPage START (SLF4J) ===");
        try {
            logger.debug("UserMyBatisRepository.getListPage called");
            // PageDDTO를 UserDDTO로 변환하여 검색 조건으로 사용
            UserDDTO searchCondition = convertPageDDTOToUserDDTO(pageDDTO);
            List<User> userList = userMapper.findBySearchCondition(searchCondition);
            // User 리스트를 Page 리스트로 변환 (실제 구현에서는 적절한 변환 로직 필요)
            List<Page> result = convertUserListToPageList(userList);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getListPage END ===");
            slf4jLogger.info("=== UserMyBatisRepository.getListPage END (SLF4J) ===");
            return result;
        } catch (Exception e) {
            logger.error("Error in getListPage: " + e.getMessage(), String.valueOf(e));
            slf4jLogger.error("Error in getListPage: " + e.getMessage(), e);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getListPage END ===");
            slf4jLogger.info("=== UserMyBatisRepository.getListPage END (SLF4J) ===");
            throw new NewBusinessException("B0000005", "getListPage", e);
        }
    }

    @Override
    public String getPageCount(PageDDTO pageDDTO) throws NewBusinessException {
        logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getPageCount START ===");
        slf4jLogger.info("=== UserMyBatisRepository.getPageCount START (SLF4J) ===");
        try {
            logger.debug("UserMyBatisRepository.getPageCount called");
            // PageDDTO를 UserDDTO로 변환하여 검색 조건으로 사용
            UserDDTO searchCondition = convertPageDDTOToUserDDTO(pageDDTO);
            int count = userMapper.countBySearchCondition(searchCondition);
            String result = String.valueOf(count);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getPageCount END ===");
            slf4jLogger.info("=== UserMyBatisRepository.getPageCount END (SLF4J) ===");
            return result;
        } catch (Exception e) {
            logger.error("Error in getPageCount: " + e.getMessage(), String.valueOf(e));
            slf4jLogger.error("Error in getPageCount: " + e.getMessage(), e);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getPageCount END ===");
            slf4jLogger.info("=== UserMyBatisRepository.getPageCount END (SLF4J) ===");
            throw new NewBusinessException("B0000006", "getPageCount", e);
        }
    }

    @Override
    public List<Tree> getListTree(TreeDDTO treeDDTO) throws NewBusinessException {
        logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getListTree START ===");
        slf4jLogger.info("=== UserMyBatisRepository.getListTree START (SLF4J) ===");
        try {
            logger.debug("UserMyBatisRepository.getListTree called");
            // TreeDDTO를 UserDDTO로 변환하여 검색 조건으로 사용
            UserDDTO searchCondition = convertTreeDDTOToUserDDTO(treeDDTO);
            List<User> userList = userMapper.findBySearchCondition(searchCondition);
            // User 리스트를 Tree 리스트로 변환 (실제 구현에서는 적절한 변환 로직 필요)
            List<Tree> result = convertUserListToTreeList(userList);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getListTree END ===");
            slf4jLogger.info("=== UserMyBatisRepository.getListTree END (SLF4J) ===");
            return result;
        } catch (Exception e) {
            logger.error("Error in getListTree: " + e.getMessage(), String.valueOf(e));
            slf4jLogger.error("Error in getListTree: " + e.getMessage(), e);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getListTree END ===");
            slf4jLogger.info("=== UserMyBatisRepository.getListTree END (SLF4J) ===");
            throw new NewBusinessException("B0000007", "getListTree", e);
        }
    }

    @Override
    public User getUserById(String userId) throws NewBusinessException {
        logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getUserById START ===");
        slf4jLogger.info("=== UserMyBatisRepository.getUserById START (SLF4J) ===");
        try {
            logger.debug("UserMyBatisRepository.getUserById called");
            User result = userMapper.findByUserId(userId);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getUserById END ===");
            slf4jLogger.info("=== UserMyBatisRepository.getUserById END (SLF4J) ===");
            return result;
        } catch (Exception e) {
            logger.error("Error in getUserById: " + e.getMessage(), String.valueOf(e));
            slf4jLogger.error("Error in getUserById: " + e.getMessage(), e);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getUserById END ===");
            slf4jLogger.info("=== UserMyBatisRepository.getUserById END (SLF4J) ===");
            throw new NewBusinessException("B0000008", "getUserById", e);
        }
    }

    @Override
    public User getUserByEmail(String userEmail) throws NewBusinessException {
        logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getUserByEmail START ===");
        slf4jLogger.info("=== UserMyBatisRepository.getUserByEmail START (SLF4J) ===");
        try {
            logger.debug("UserMyBatisRepository.getUserByEmail called");
            User result = userMapper.findByEmail(userEmail);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getUserByEmail END ===");
            slf4jLogger.info("=== UserMyBatisRepository.getUserByEmail END (SLF4J) ===");
            return result;
        } catch (Exception e) {
            logger.error("Error in getUserByEmail: " + e.getMessage(), String.valueOf(e));
            slf4jLogger.error("Error in getUserByEmail: " + e.getMessage(), e);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getUserByEmail END ===");
            slf4jLogger.info("=== UserMyBatisRepository.getUserByEmail END (SLF4J) ===");
            throw new NewBusinessException("B0000009", "getUserByEmail", e);
        }
    }

    @Override
    public int existsUser(String userId) throws NewBusinessException {
        logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.existsUser START ===");
        slf4jLogger.info("=== UserMyBatisRepository.existsUser START (SLF4J) ===");
        try {
            logger.debug("UserMyBatisRepository.existsUser called");
            User user = userMapper.findByUserId(userId);
            int result = (user != null) ? 1 : 0;
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.existsUser END ===");
            slf4jLogger.info("=== UserMyBatisRepository.existsUser END (SLF4J) ===");
            return result;
        } catch (Exception e) {
            logger.error("Error in existsUser: " + e.getMessage(), String.valueOf(e));
            slf4jLogger.error("Error in existsUser: " + e.getMessage(), e);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.existsUser END ===");
            slf4jLogger.info("=== UserMyBatisRepository.existsUser END (SLF4J) ===");
            throw new NewBusinessException("B0000010", "existsUser", e);
        }
    }
    
    /**
     * UserDDTO를 User 엔티티로 변환
     */
    private User convertToUser(UserDDTO dto) {
        if (dto == null) {
            return null;
        }
        
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());
        user.setCreatedDate(dto.getCreatedDate());
        user.setUpdatedDate(dto.getUpdatedDate());
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
        
        return user;
    }
    
    /**
     * PageDDTO를 UserDDTO로 변환
     */
    private UserDDTO convertPageDDTOToUserDDTO(PageDDTO pageDDTO) {
        if (pageDDTO == null) {
            return null;
        }
        
        UserDDTO userDDTO = new UserDDTO();
        // PageDDTO의 필드들을 UserDDTO의 검색 조건으로 매핑
        // 실제 구현에서는 적절한 매핑 로직 필요
        return userDDTO;
    }
    
    /**
     * TreeDDTO를 UserDDTO로 변환
     */
    private UserDDTO convertTreeDDTOToUserDDTO(TreeDDTO treeDDTO) {
        if (treeDDTO == null) {
            return null;
        }
        
        UserDDTO userDDTO = new UserDDTO();
        // TreeDDTO의 필드들을 UserDDTO의 검색 조건으로 매핑
        // 실제 구현에서는 적절한 매핑 로직 필요
        return userDDTO;
    }
    
    /**
     * User 리스트를 Page 리스트로 변환
     */
    private List<Page> convertUserListToPageList(List<User> userList) {
        // 실제 구현에서는 User를 Page로 변환하는 로직 필요
        return new java.util.ArrayList<>();
    }
    
    /**
     * User 리스트를 Tree 리스트로 변환
     */
    private List<Tree> convertUserListToTreeList(List<User> userList) {
        // 실제 구현에서는 User를 Tree로 변환하는 로직 필요
        return new java.util.ArrayList<>();
    }
}
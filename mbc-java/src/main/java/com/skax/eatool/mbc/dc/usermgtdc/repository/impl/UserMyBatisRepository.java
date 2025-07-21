package com.skax.eatool.mbc.dc.usermgtdc.repository.impl;

import java.util.List;

import com.skax.eatool.mbc.dc.usermgtdc.Page;
import com.skax.eatool.mbc.dc.usermgtdc.Tree;
import com.skax.eatool.mbc.dc.usermgtdc.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.util.NewObjectUtil;
import com.skax.eatool.mbc.dc.usermgtdc.dto.UserDDTO;
import com.skax.eatool.mbc.dc.usermgtdc.dto.PageDDTO;
import com.skax.eatool.mbc.dc.usermgtdc.dto.TreeDDTO;
import com.skax.eatool.mbc.dc.usermgtdc.mapper.UserMapper;
import com.skax.eatool.mbc.dc.usermgtdc.repository.UserRepository;

/**
 * MyBatis 기반 사용자 Repository 구현체
 * 
 * 프로그램명: UserMyBatisRepository.java
 * 설명: MyBatis를 사용한 사용자 관리 Repository 구현체
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - MyBatis를 통한 사용자 CRUD 작업
 * - 페이징 처리
 * - 트리 구조 데이터 조회
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
            List<User> userList = userMapper.getListUser(userDDTO);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getListUser END ===");
            slf4jLogger.info("=== UserMyBatisRepository.getListUser END (SLF4J) ===");
            return NewObjectUtil.copyForList(User.class, userList);
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
            int result = userMapper.insertUser(userDDTO);
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
            int result = userMapper.updateUser(userDDTO);
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
            int result = userMapper.deleteUser(userDDTO);
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
            List<Page> result = userMapper.getListPage(pageDDTO);
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
            String result = userMapper.getPageCount(pageDDTO);
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
            List<Tree> treeList = userMapper.getListTree(treeDDTO);
            logger.info("UserMyBatisRepository", "=== UserMyBatisRepository.getListTree END ===");
            slf4jLogger.info("=== UserMyBatisRepository.getListTree END (SLF4J) ===");
            return NewObjectUtil.copyForList(Tree.class, treeList);
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
            User result = userMapper.getUserById(userId);
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
            User result = userMapper.getUserByEmail(userEmail);
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
            int result = userMapper.existsUser(userId);
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
}
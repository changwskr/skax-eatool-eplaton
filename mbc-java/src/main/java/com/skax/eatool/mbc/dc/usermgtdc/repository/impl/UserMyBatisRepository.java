package com.skax.eatool.mbc.dc.usermgtdc.repository.impl;

import java.util.List;

import com.skax.eatool.mbc.dc.usermgtdc.Page;
import com.skax.eatool.mbc.dc.usermgtdc.Tree;
import com.skax.eatool.mbc.dc.usermgtdc.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

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

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getListUser(UserDDTO userDDTO) throws NewBusinessException {
        try {
            logger.debug("UserMyBatisRepository.getListUser called");
            List<User> userList = userMapper.getListUser(userDDTO);
            return NewObjectUtil.copyForList(User.class, userList);
        } catch (Exception e) {
            logger.error("Error in getListUser: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("B0000001", "getListUser", e);
        }
    }

    @Override
    public int insertUser(UserDDTO userDDTO) throws NewBusinessException {
        try {
            logger.debug("UserMyBatisRepository.insertUser called");
            return userMapper.insertUser(userDDTO);
        } catch (Exception e) {
            logger.error("Error in insertUser: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("B0000002", "insertUser", e);
        }
    }

    @Override
    public int updateUser(UserDDTO userDDTO) throws NewBusinessException {
        try {
            logger.debug("UserMyBatisRepository.updateUser called");
            return userMapper.updateUser(userDDTO);
        } catch (Exception e) {
            logger.error("Error in updateUser: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("B0000003", "updateUser", e);
        }
    }

    @Override
    public int deleteUser(UserDDTO userDDTO) throws NewBusinessException {
        try {
            logger.debug("UserMyBatisRepository.deleteUser called");
            return userMapper.deleteUser(userDDTO);
        } catch (Exception e) {
            logger.error("Error in deleteUser: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("B0000004", "deleteUser", e);
        }
    }

    @Override
    public List<Page> getListPage(PageDDTO pageDDTO) throws NewBusinessException {
        try {
            logger.debug("UserMyBatisRepository.getListPage called");
            return userMapper.getListPage(pageDDTO);
        } catch (Exception e) {
            logger.error("Error in getListPage: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("B0000005", "getListPage", e);
        }
    }

    @Override
    public String getPageCount(PageDDTO pageDDTO) throws NewBusinessException {
        try {
            logger.debug("UserMyBatisRepository.getPageCount called");
            return userMapper.getPageCount(pageDDTO);
        } catch (Exception e) {
            logger.error("Error in getPageCount: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("B0000006", "getPageCount", e);
        }
    }

    @Override
    public List<Tree> getListTree(TreeDDTO treeDDTO) throws NewBusinessException {
        try {
            logger.debug("UserMyBatisRepository.getListTree called");
            List<Tree> treeList = userMapper.getListTree(treeDDTO);
            return NewObjectUtil.copyForList(Tree.class, treeList);
        } catch (Exception e) {
            logger.error("Error in getListTree: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("B0000007", "getListTree", e);
        }
    }

    @Override
    public User getUserById(String userId) throws NewBusinessException {
        try {
            logger.debug("UserMyBatisRepository.getUserById called");
            return userMapper.getUserById(userId);
        } catch (Exception e) {
            logger.error("Error in getUserById: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("B0000008", "getUserById", e);
        }
    }

    @Override
    public User getUserByEmail(String userEmail) throws NewBusinessException {
        try {
            logger.debug("UserMyBatisRepository.getUserByEmail called");
            return userMapper.getUserByEmail(userEmail);
        } catch (Exception e) {
            logger.error("Error in getUserByEmail: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("B0000009", "getUserByEmail", e);
        }
    }

    @Override
    public int existsUser(String userId) throws NewBusinessException {
        try {
            logger.debug("UserMyBatisRepository.existsUser called");
            return userMapper.existsUser(userId);
        } catch (Exception e) {
            logger.error("Error in existsUser: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("B0000010", "existsUser", e);
        }
    }
}
package com.skax.eatool.mbc.dc.usermgtdc.mapper;

import java.util.List;

import com.skax.eatool.mbc.dc.usermgtdc.Page;
import com.skax.eatool.mbc.dc.usermgtdc.Tree;
import com.skax.eatool.mbc.dc.usermgtdc.User;
import com.skax.eatool.mbc.dc.usermgtdc.dto.PageDDTO;
import com.skax.eatool.mbc.dc.usermgtdc.dto.TreeDDTO;
import com.skax.eatool.mbc.dc.usermgtdc.dto.UserDDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * User MyBatis 매퍼 인터페이스
 * 
 * 프로그램명: UserMapper.java
 * 설명: 사용자 관리 관련 데이터베이스 작업을 위한 MyBatis 매퍼 인터페이스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 사용자 CRUD 작업
 * - 페이징 처리
 * - 트리 구조 데이터 조회
 * 
 * @version 1.0
 */
@Mapper
public interface UserMapper {

    /**
     * 사용자 목록 조회
     * 
     * @param userDDTO 사용자 조회 조건
     * @return 사용자 목록
     */
    List<User> getListUser(UserDDTO userDDTO);

    /**
     * 사용자 등록
     * 
     * @param userDDTO 사용자 정보
     * @return 등록된 행 수
     */
    int insertUser(UserDDTO userDDTO);

    /**
     * 사용자 수정
     * 
     * @param userDDTO 사용자 정보
     * @return 수정된 행 수
     */
    int updateUser(UserDDTO userDDTO);

    /**
     * 사용자 삭제 (논리 삭제)
     * 
     * @param userDDTO 사용자 정보
     * @return 삭제된 행 수
     */
    int deleteUser(UserDDTO userDDTO);

    /**
     * 페이징 데이터 조회
     * 
     * @param pageDDTO 페이징 조건
     * @return 페이징 데이터 목록
     */
    List<Page> getListPage(PageDDTO pageDDTO);

    /**
     * 전체 데이터 개수 조회
     * 
     * @param pageDDTO 페이징 조건
     * @return 전체 데이터 개수
     */
    String getPageCount(PageDDTO pageDDTO);

    /**
     * 트리 구조 데이터 조회
     * 
     * @param treeDDTO 트리 조회 조건
     * @return 트리 데이터 목록
     */
    List<Tree> getListTree(TreeDDTO treeDDTO);

    /**
     * 사용자 ID로 사용자 조회
     * 
     * @param userId 사용자 ID
     * @return 사용자 정보
     */
    User getUserById(@Param("userId") String userId);

    /**
     * 사용자 이메일로 사용자 조회
     * 
     * @param userEmail 사용자 이메일
     * @return 사용자 정보
     */
    User getUserByEmail(@Param("userEmail") String userEmail);

    /**
     * 사용자 존재 여부 확인
     * 
     * @param userId 사용자 ID
     * @return 존재 여부 (1: 존재, 0: 존재하지 않음)
     */
    int existsUser(@Param("userId") String userId);
}
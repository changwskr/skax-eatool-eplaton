package com.skax.eatool.mbc.fc.foundation.bzcrudbus.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 공통 MyBatis 매퍼 인터페이스
 * 
 * 프로그램명: CommonMapper.java
 * 설명: 공통 데이터베이스 작업을 위한 MyBatis 매퍼 인터페이스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 기본 CRUD 작업
 * - 동적 쿼리 지원
 * - 프로시저 호출
 * 
 * @version 1.0
 */
@Mapper
public interface CommonMapper {

    /**
     * 단일 데이터 조회
     * 
     * @param statementId SQL 문 ID
     * @param params      파라미터 맵
     * @return 조회 결과 맵
     */
    Map<String, Object> selectOne(@Param("statementId") String statementId,
            @Param("params") Map<String, Object> params);

    /**
     * 목록 데이터 조회
     * 
     * @param statementId SQL 문 ID
     * @param params      파라미터 맵
     * @return 조회 결과 리스트
     */
    List<Map<String, Object>> selectList(@Param("statementId") String statementId,
            @Param("params") Map<String, Object> params);

    /**
     * 데이터 삽입
     * 
     * @param statementId SQL 문 ID
     * @param params      파라미터 맵
     * @return 삽입된 행 수
     */
    int insert(@Param("statementId") String statementId, @Param("params") Map<String, Object> params);

    /**
     * 데이터 수정
     * 
     * @param statementId SQL 문 ID
     * @param params      파라미터 맵
     * @return 수정된 행 수
     */
    int update(@Param("statementId") String statementId, @Param("params") Map<String, Object> params);

    /**
     * 데이터 삭제
     * 
     * @param statementId SQL 문 ID
     * @param params      파라미터 맵
     * @return 삭제된 행 수
     */
    int delete(@Param("statementId") String statementId, @Param("params") Map<String, Object> params);

    /**
     * 프로시저 호출
     * 
     * @param statementId SQL 문 ID
     * @param params      파라미터 맵
     * @return 프로시저 실행 결과
     */
    Map<String, Object> callProcedure(@Param("statementId") String statementId,
            @Param("params") Map<String, Object> params);

    /**
     * 동적 쿼리 실행
     * 
     * @param sql    SQL 문
     * @param params 파라미터 맵
     * @return 조회 결과 맵
     */
    Map<String, Object> executeDynamicQuery(@Param("sql") String sql, @Param("params") Map<String, Object> params);

    /**
     * 동적 쿼리 실행 (목록)
     * 
     * @param sql    SQL 문
     * @param params 파라미터 맵
     * @return 조회 결과 리스트
     */
    List<Map<String, Object>> executeDynamicQueryList(@Param("sql") String sql,
            @Param("params") Map<String, Object> params);
}
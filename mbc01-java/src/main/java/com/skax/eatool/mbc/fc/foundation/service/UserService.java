package com.skax.eatool.mbc.fc.foundation.service;

import com.skax.eatool.mbc.fc.foundation.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 사용자 서비스 인터페이스
 * 
 * 프로그램명: UserService.java
 * 설명: 사용자 관련 비즈니스 로직을 담당하는 서비스 인터페이스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 사용자 CRUD 작업
 * - 사용자 검색 기능
 * - 비즈니스 로직 처리
 * 
 * @version 1.0
 */
public interface UserService {

    /**
     * 사용자 저장
     * 
     * @param user 사용자 정보
     * @return 저장된 사용자
     */
    User saveUser(User user);

    /**
     * 사용자 ID로 조회
     * 
     * @param userId 사용자 ID
     * @return 사용자 Optional
     */
    Optional<User> findUserById(Long userId);

    /**
     * 이메일로 사용자 조회
     * 
     * @param email 이메일
     * @return 사용자 Optional
     */
    Optional<User> findUserByEmail(String email);

    /**
     * 모든 사용자 조회
     * 
     * @return 사용자 목록
     */
    List<User> findAllUsers();

    /**
     * 사용자명으로 사용자 검색
     * 
     * @param userName 사용자명
     * @return 사용자 목록
     */
    List<User> findUsersByName(String userName);

    /**
     * 상태별 사용자 조회
     * 
     * @param status 사용자 상태
     * @return 사용자 목록
     */
    List<User> findUsersByStatus(User.UserStatus status);

    /**
     * 사용자 삭제
     * 
     * @param userId 사용자 ID
     */
    void deleteUser(Long userId);

    /**
     * 사용자 상태 업데이트
     * 
     * @param userId 사용자 ID
     * @param status 새로운 상태
     * @return 업데이트된 사용자
     */
    User updateUserStatus(Long userId, User.UserStatus status);

    /**
     * 마지막 로그인 시간 업데이트
     * 
     * @param userId    사용자 ID
     * @param loginDate 로그인 시간
     * @return 업데이트된 사용자
     */
    User updateLastLoginDate(Long userId, LocalDateTime loginDate);

    /**
     * 활성 사용자 수 조회
     * 
     * @return 활성 사용자 수
     */
    long countActiveUsers();

    /**
     * 특정 기간 동안 가입한 사용자 조회
     * 
     * @param startDate 시작 날짜
     * @param endDate   종료 날짜
     * @return 사용자 목록
     */
    List<User> findUsersByRegistrationPeriod(LocalDateTime startDate, LocalDateTime endDate);
}
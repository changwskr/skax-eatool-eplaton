package com.skax.eatool.mba.dc.usermgmtdc.repository;

import com.skax.eatool.mba.dc.usermgmtdc.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 리포지토리
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    /**
     * 사용자명으로 사용자 조회
     * 
     * @param username 사용자명
     * @return 사용자 엔티티 (Optional)
     */
    Optional<UserEntity> findByUsername(String username);
    
    /**
     * 이메일로 사용자 조회
     * 
     * @param email 이메일
     * @return 사용자 엔티티 (Optional)
     */
    Optional<UserEntity> findByEmail(String email);
    
    /**
     * 사용자명 존재 여부 확인
     * 
     * @param username 사용자명
     * @return 존재 여부
     */
    boolean existsByUsername(String username);
    
    /**
     * 이메일 존재 여부 확인
     * 
     * @param email 이메일
     * @return 존재 여부
     */
    boolean existsByEmail(String email);
    
    /**
     * 부서별 사용자 목록 조회
     * 
     * @param department 부서명
     * @param pageable 페이지 정보
     * @return 사용자 페이지
     */
    Page<UserEntity> findByDepartment(String department, Pageable pageable);
    
    /**
     * 역할별 사용자 목록 조회
     * 
     * @param role 역할
     * @param pageable 페이지 정보
     * @return 사용자 페이지
     */
    Page<UserEntity> findByRole(String role, Pageable pageable);
    
    /**
     * 상태별 사용자 목록 조회
     * 
     * @param status 상태
     * @param pageable 페이지 정보
     * @return 사용자 페이지
     */
    Page<UserEntity> findByStatus(String status, Pageable pageable);
    
    /**
     * 사용자명으로 검색 (LIKE 검색)
     * 
     * @param username 사용자명 (부분 일치)
     * @param pageable 페이지 정보
     * @return 사용자 페이지
     */
    Page<UserEntity> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
    
    /**
     * 이메일로 검색 (LIKE 검색)
     * 
     * @param email 이메일 (부분 일치)
     * @param pageable 페이지 정보
     * @return 사용자 페이지
     */
    Page<UserEntity> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    
    /**
     * 전체 이름으로 검색 (LIKE 검색)
     * 
     * @param fullName 전체 이름 (부분 일치)
     * @param pageable 페이지 정보
     * @return 사용자 페이지
     */
    Page<UserEntity> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);
    
    /**
     * 부서로 검색 (LIKE 검색)
     * 
     * @param department 부서 (부분 일치)
     * @param pageable 페이지 정보
     * @return 사용자 페이지
     */
    Page<UserEntity> findByDepartmentContainingIgnoreCase(String department, Pageable pageable);
    
    /**
     * 복합 검색 (사용자명, 이메일, 전체 이름, 부서)
     * 
     * @param keyword 검색 키워드
     * @param pageable 페이지 정보
     * @return 사용자 페이지
     */
    @Query("SELECT u FROM UserEntity u WHERE " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.department) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<UserEntity> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    /**
     * 특정 필드로 검색
     * 
     * @param field 검색 필드
     * @param keyword 검색 키워드
     * @param pageable 페이지 정보
     * @return 사용자 페이지
     */
    @Query("SELECT u FROM UserEntity u WHERE " +
           "(:field = 'username' AND LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
           "(:field = 'email' AND LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
           "(:field = 'fullName' AND LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
           "(:field = 'department' AND LOWER(u.department) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<UserEntity> findByFieldAndKeyword(@Param("field") String field, 
                                          @Param("keyword") String keyword, 
                                          Pageable pageable);
    
    /**
     * 활성 사용자 목록 조회
     * 
     * @param pageable 페이지 정보
     * @return 활성 사용자 페이지
     */
    Page<UserEntity> findByStatusIgnoreCase(String status, Pageable pageable);
    
    /**
     * 최근 생성된 사용자 목록 조회
     * 
     * @param pageable 페이지 정보
     * @return 최근 생성된 사용자 페이지
     */
    Page<UserEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
    
    /**
     * 사용자명으로 정렬된 사용자 목록 조회
     * 
     * @param pageable 페이지 정보
     * @return 사용자명 정렬된 사용자 페이지
     */
    Page<UserEntity> findAllByOrderByUsernameAsc(Pageable pageable);
    
    /**
     * 부서별 사용자 수 조회
     * 
     * @return 부서별 사용자 수 목록
     */
    @Query("SELECT u.department, COUNT(u) FROM UserEntity u GROUP BY u.department")
    List<Object[]> countByDepartment();
    
    /**
     * 역할별 사용자 수 조회
     * 
     * @return 역할별 사용자 수 목록
     */
    @Query("SELECT u.role, COUNT(u) FROM UserEntity u GROUP BY u.role")
    List<Object[]> countByRole();
    
    /**
     * 상태별 사용자 수 조회
     * 
     * @return 상태별 사용자 수 목록
     */
    @Query("SELECT u.status, COUNT(u) FROM UserEntity u GROUP BY u.status")
    List<Object[]> countByStatus();
} 
package com.skax.eatool.mba.dc.usermgmtdc.mapper;

import com.skax.eatool.mba.as.usermgmtas.dto.CreateUserRequestDto;
import com.skax.eatool.mba.as.usermgmtas.dto.UpdateUserRequestDto;
import com.skax.eatool.mba.as.usermgmtas.dto.UserDto;
import com.skax.eatool.mba.dc.usermgmtdc.entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 사용자 매퍼
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@Mapper
public interface UserMapper {
    
    /**
     * 사용자 목록 조회
     * 
     * @param offset 시작 위치
     * @param limit 조회 개수
     * @return 사용자 목록
     */
    @Select("SELECT * FROM users ORDER BY created_at DESC LIMIT #{limit} OFFSET #{offset}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "email", column = "email"),
        @Result(property = "fullName", column = "full_name"),
        @Result(property = "department", column = "department"),
        @Result(property = "role", column = "role"),
        @Result(property = "status", column = "status"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "address", column = "address"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    List<UserEntity> selectUserList(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 사용자 총 개수 조회
     * 
     * @return 사용자 총 개수
     */
    @Select("SELECT COUNT(*) FROM users")
    Long selectUserCount();
    
    /**
     * 사용자 ID로 조회
     * 
     * @param id 사용자 ID
     * @return 사용자 정보
     */
    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "email", column = "email"),
        @Result(property = "fullName", column = "full_name"),
        @Result(property = "department", column = "department"),
        @Result(property = "role", column = "role"),
        @Result(property = "status", column = "status"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "address", column = "address"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    UserEntity selectUserById(@Param("id") Long id);
    
    /**
     * 사용자명으로 조회
     * 
     * @param username 사용자명
     * @return 사용자 정보
     */
    @Select("SELECT * FROM users WHERE username = #{username}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "email", column = "email"),
        @Result(property = "fullName", column = "full_name"),
        @Result(property = "department", column = "department"),
        @Result(property = "role", column = "role"),
        @Result(property = "status", column = "status"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "address", column = "address"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    UserEntity selectUserByUsername(@Param("username") String username);
    
    /**
     * 이메일로 조회
     * 
     * @param email 이메일
     * @return 사용자 정보
     */
    @Select("SELECT * FROM users WHERE email = #{email}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "email", column = "email"),
        @Result(property = "fullName", column = "full_name"),
        @Result(property = "department", column = "department"),
        @Result(property = "role", column = "role"),
        @Result(property = "status", column = "status"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "address", column = "address"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    UserEntity selectUserByEmail(@Param("email") String email);
    
    /**
     * 사용자 검색
     * 
     * @param keyword 검색 키워드
     * @param offset 시작 위치
     * @param limit 조회 개수
     * @return 검색된 사용자 목록
     */
    @Select("SELECT * FROM users WHERE " +
            "username LIKE CONCAT('%', #{keyword}, '%') OR " +
            "email LIKE CONCAT('%', #{keyword}, '%') OR " +
            "full_name LIKE CONCAT('%', #{keyword}, '%') OR " +
            "department LIKE CONCAT('%', #{keyword}, '%') " +
            "ORDER BY created_at DESC LIMIT #{limit} OFFSET #{offset}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "email", column = "email"),
        @Result(property = "fullName", column = "full_name"),
        @Result(property = "department", column = "department"),
        @Result(property = "role", column = "role"),
        @Result(property = "status", column = "status"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "address", column = "address"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    List<UserEntity> searchUsers(@Param("keyword") String keyword, 
                                @Param("offset") int offset, 
                                @Param("limit") int limit);
    
    /**
     * 검색된 사용자 총 개수 조회
     * 
     * @param keyword 검색 키워드
     * @return 검색된 사용자 총 개수
     */
    @Select("SELECT COUNT(*) FROM users WHERE " +
            "username LIKE CONCAT('%', #{keyword}, '%') OR " +
            "email LIKE CONCAT('%', #{keyword}, '%') OR " +
            "full_name LIKE CONCAT('%', #{keyword}, '%') OR " +
            "department LIKE CONCAT('%', #{keyword}, '%')")
    Long searchUserCount(@Param("keyword") String keyword);
    
    /**
     * 사용자 생성
     * 
     * @param user 사용자 정보
     * @return 생성된 행 수
     */
    @Insert("INSERT INTO users (username, password, email, full_name, department, role, status, phone, address, created_at, updated_at) " +
            "VALUES (#{username}, #{password}, #{email}, #{fullName}, #{department}, #{role}, #{status}, #{phone}, #{address}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(UserEntity user);
    
    /**
     * 사용자 수정
     * 
     * @param user 사용자 정보
     * @return 수정된 행 수
     */
    @Update("UPDATE users SET " +
            "email = #{email}, " +
            "full_name = #{fullName}, " +
            "department = #{department}, " +
            "role = #{role}, " +
            "status = #{status}, " +
            "phone = #{phone}, " +
            "address = #{address}, " +
            "updated_at = NOW() " +
            "WHERE id = #{id}")
    int updateUser(UserEntity user);
    
    /**
     * 사용자 삭제
     * 
     * @param id 사용자 ID
     * @return 삭제된 행 수
     */
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteUser(@Param("id") Long id);
    
    /**
     * 사용자명 존재 여부 확인
     * 
     * @param username 사용자명
     * @return 존재 여부
     */
    @Select("SELECT COUNT(*) FROM users WHERE username = #{username}")
    int existsByUsername(@Param("username") String username);
    
    /**
     * 이메일 존재 여부 확인
     * 
     * @param email 이메일
     * @return 존재 여부
     */
    @Select("SELECT COUNT(*) FROM users WHERE email = #{email}")
    int existsByEmail(@Param("email") String email);
} 
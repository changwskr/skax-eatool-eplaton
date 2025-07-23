package com.skax.eatool.mba.dc.usermgmtdc;

import com.skax.eatool.mba.as.usermgmtas.dto.CreateUserRequestDto;
import com.skax.eatool.mba.as.usermgmtas.dto.PageRequestDto;
import com.skax.eatool.mba.as.usermgmtas.dto.UserDto;
import com.skax.eatool.mba.dc.usermgmtdc.entity.UserEntity;
import com.skax.eatool.mba.dc.usermgmtdc.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 사용자 데이터 컨트롤러
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@Repository
public class UserDC {
    
    private static final Logger logger = LoggerFactory.getLogger(UserDC.class);
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * 사용자 목록 조회
     * 
     * @param pageRequest 페이지 요청 정보
     * @return 사용자 목록
     */
    public List<UserDto> getUserList(PageRequestDto pageRequest) {
        logger.info("=== UserDC.getUserList START ===");
        logger.info("페이지 요청: {}", pageRequest);
        
        try {
            Pageable pageable = PageRequest.of(pageRequest.getPage(), pageRequest.getSize());
            Page<UserEntity> userPage = userRepository.findAllByOrderByCreatedAtDesc(pageable);
            List<UserDto> userList = userPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
            logger.info("조회된 사용자 수: {}", userList != null ? userList.size() : 0);
            logger.info("=== UserDC.getUserList END ===");
            return userList;
            
        } catch (Exception e) {
            logger.error("UserDC.getUserList 중 오류 발생: {}", e.getMessage(), e);
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
        logger.info("=== UserDC.getUserCount START ===");
        
        try {
            Long count = userRepository.count();
            logger.info("사용자 총 개수: {}", count);
            logger.info("=== UserDC.getUserCount END ===");
            return count;
            
        } catch (Exception e) {
            logger.error("UserDC.getUserCount 중 오류 발생: {}", e.getMessage(), e);
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
        logger.info("=== UserDC.getUserById START ===");
        logger.info("조회할 사용자 ID: {}", userId);
        
        try {
            Optional<UserEntity> userEntity = userRepository.findById(userId);
            UserDto user = userEntity.map(this::convertToDto).orElse(null);
            logger.info("조회 결과: {}", user != null ? "성공" : "실패");
            logger.info("=== UserDC.getUserById END ===");
            return user;
            
        } catch (Exception e) {
            logger.error("UserDC.getUserById 중 오류 발생: {}", e.getMessage(), e);
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
        logger.info("=== UserDC.getUserByUsername START ===");
        logger.info("조회할 사용자명: {}", username);
        
        try {
            Optional<UserEntity> userEntity = userRepository.findByUsername(username);
            UserDto user = userEntity.map(this::convertToDto).orElse(null);
            logger.info("조회 결과: {}", user != null ? "성공" : "실패");
            logger.info("=== UserDC.getUserByUsername END ===");
            return user;
            
        } catch (Exception e) {
            logger.error("UserDC.getUserByUsername 중 오류 발생: {}", e.getMessage(), e);
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
        logger.info("=== UserDC.getUserByEmail START ===");
        logger.info("조회할 이메일: {}", email);
        
        try {
            Optional<UserEntity> userEntity = userRepository.findByEmail(email);
            UserDto user = userEntity.map(this::convertToDto).orElse(null);
            logger.info("조회 결과: {}", user != null ? "성공" : "실패");
            logger.info("=== UserDC.getUserByEmail END ===");
            return user;
            
        } catch (Exception e) {
            logger.error("UserDC.getUserByEmail 중 오류 발생: {}", e.getMessage(), e);
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
        logger.info("=== UserDC.searchUsers START ===");
        logger.info("검색 조건: {}", pageRequest);
        
        try {
            Pageable pageable = PageRequest.of(pageRequest.getPage(), pageRequest.getSize());
            Page<UserEntity> userPage;
            
            String keyword = pageRequest.getKeyword();
            String searchField = pageRequest.getSearchField();
            
            if (keyword != null && !keyword.trim().isEmpty()) {
                if ("all".equals(searchField) || searchField == null) {
                    // 전체 검색
                    userPage = userRepository.findByKeyword(keyword, pageable);
                } else {
                    // 특정 필드 검색
                    userPage = userRepository.findByFieldAndKeyword(searchField, keyword, pageable);
                }
            } else {
                // 검색 조건이 없으면 전체 목록
                userPage = userRepository.findAllByOrderByCreatedAtDesc(pageable);
            }
            
            List<UserDto> userList = userPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
            
            logger.info("검색된 사용자 수: {}", userList != null ? userList.size() : 0);
            logger.info("=== UserDC.searchUsers END ===");
            return userList;
            
        } catch (Exception e) {
            logger.error("UserDC.searchUsers 중 오류 발생: {}", e.getMessage(), e);
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
        logger.info("=== UserDC.getSearchUserCount START ===");
        
        try {
            String keyword = pageRequest.getKeyword();
            String searchField = pageRequest.getSearchField();
            
            Long count;
            if (keyword != null && !keyword.trim().isEmpty()) {
                if ("all".equals(searchField) || searchField == null) {
                    // 전체 검색 개수
                    Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
                    Page<UserEntity> userPage = userRepository.findByKeyword(keyword, pageable);
                    count = userPage.getTotalElements();
                } else {
                    // 특정 필드 검색 개수
                    Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
                    Page<UserEntity> userPage = userRepository.findByFieldAndKeyword(searchField, keyword, pageable);
                    count = userPage.getTotalElements();
                }
            } else {
                // 검색 조건이 없으면 전체 개수
                count = userRepository.count();
            }
            
            logger.info("검색된 사용자 총 개수: {}", count);
            logger.info("=== UserDC.getSearchUserCount END ===");
            return count;
            
        } catch (Exception e) {
            logger.error("UserDC.getSearchUserCount 중 오류 발생: {}", e.getMessage(), e);
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
        logger.info("=== UserDC.createUser START ===");
        logger.info("생성 요청: {}", createRequest);
        
        try {
            // 중복 검사
            if (userRepository.existsByUsername(createRequest.getUsername())) {
                logger.error("이미 존재하는 사용자명: {}", createRequest.getUsername());
                throw new RuntimeException("이미 존재하는 사용자명입니다.");
            }
            
            if (userRepository.existsByEmail(createRequest.getEmail())) {
                logger.error("이미 존재하는 이메일: {}", createRequest.getEmail());
                throw new RuntimeException("이미 존재하는 이메일입니다.");
            }
            
            // 엔티티 생성
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(createRequest.getUsername());
            userEntity.setPassword(createRequest.getPassword());
            userEntity.setEmail(createRequest.getEmail());
            userEntity.setFullName(createRequest.getFullName());
            userEntity.setDepartment(createRequest.getDepartment());
            userEntity.setRole(createRequest.getRole());
            userEntity.setPhone(createRequest.getPhone());
            userEntity.setAddress(createRequest.getAddress());
            userEntity.setStatus("ACTIVE");
            
            // 저장
            UserEntity savedEntity = userRepository.save(userEntity);
            UserDto createdUser = convertToDto(savedEntity);
            
            logger.info("생성 결과: {}", createdUser != null ? "성공" : "실패");
            logger.info("=== UserDC.createUser END ===");
            return createdUser;
            
        } catch (Exception e) {
            logger.error("UserDC.createUser 중 오류 발생: {}", e.getMessage(), e);
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
        logger.info("=== UserDC.updateUser START ===");
        logger.info("수정할 사용자 ID: {}", userId);
        logger.info("수정 요청: {}", updateRequest);
        
        try {
            // 기존 사용자 조회
            Optional<UserEntity> userEntityOpt = userRepository.findById(userId);
            if (userEntityOpt.isEmpty()) {
                logger.warn("수정할 사용자를 찾을 수 없습니다. ID: {}", userId);
                return null;
            }
            
            UserEntity userEntity = userEntityOpt.get();
            
            // 이메일 중복 검사 (다른 사용자와 중복되지 않는지)
            if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(userEntity.getEmail())) {
                if (userRepository.existsByEmail(updateRequest.getEmail())) {
                    logger.error("이미 존재하는 이메일: {}", updateRequest.getEmail());
                    throw new RuntimeException("이미 존재하는 이메일입니다.");
                }
            }
            
            // 필드 업데이트
            if (updateRequest.getEmail() != null) {
                userEntity.setEmail(updateRequest.getEmail());
            }
            if (updateRequest.getFullName() != null) {
                userEntity.setFullName(updateRequest.getFullName());
            }
            if (updateRequest.getDepartment() != null) {
                userEntity.setDepartment(updateRequest.getDepartment());
            }
            if (updateRequest.getRole() != null) {
                userEntity.setRole(updateRequest.getRole());
            }
            if (updateRequest.getPhone() != null) {
                userEntity.setPhone(updateRequest.getPhone());
            }
            if (updateRequest.getAddress() != null) {
                userEntity.setAddress(updateRequest.getAddress());
            }
            if (updateRequest.getStatus() != null) {
                userEntity.setStatus(updateRequest.getStatus());
            }
            
            // 저장
            UserEntity savedEntity = userRepository.save(userEntity);
            UserDto updatedUser = convertToDto(savedEntity);
            
            logger.info("수정 결과: {}", updatedUser != null ? "성공" : "실패");
            logger.info("=== UserDC.updateUser END ===");
            return updatedUser;
            
        } catch (Exception e) {
            logger.error("UserDC.updateUser 중 오류 발생: {}", e.getMessage(), e);
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
        logger.info("=== UserDC.deleteUser START ===");
        logger.info("삭제할 사용자 ID: {}", userId);
        
        try {
            if (!userRepository.existsById(userId)) {
                logger.warn("삭제할 사용자를 찾을 수 없습니다. ID: {}", userId);
                return false;
            }
            
            userRepository.deleteById(userId);
            logger.info("삭제 결과: 성공");
            logger.info("=== UserDC.deleteUser END ===");
            return true;
            
        } catch (Exception e) {
            logger.error("UserDC.deleteUser 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Entity를 DTO로 변환
     * 
     * @param entity 사용자 엔티티
     * @return 사용자 DTO
     */
    private UserDto convertToDto(UserEntity entity) {
        if (entity == null) return null;
        
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        dto.setFullName(entity.getFullName());
        dto.setDepartment(entity.getDepartment());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        
        return dto;
    }
    
    /**
     * DTO를 Entity로 변환
     * 
     * @param dto 사용자 DTO
     * @return 사용자 엔티티
     */
    private UserEntity convertToEntity(UserDto dto) {
        if (dto == null) return null;
        
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setFullName(dto.getFullName());
        entity.setDepartment(dto.getDepartment());
        entity.setRole(dto.getRole());
        entity.setStatus(dto.getStatus());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        
        return entity;
    }
} 
package com.skax.eatool.mbc.pc.userpc;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbc.dc.usermgtdc.DCUser;
import com.skax.eatool.mbc.dc.usermgtdc.dto.UserDDTO;
import com.skax.eatool.mbc.pc.dto.UserPDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 사용자 관리 Process Component
 * 
 * 프로그램명: PCUser.java
 * 설명: 사용자 관리 프로세스 로직을 담당하는 Process Component
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - AS와 DC 간의 데이터 변환
 * - 사용자 관리 프로세스 처리
 * - 데이터 검증 및 변환
 * 
 * @version 1.0
 */
@Component
public class PCUser {
    
    private static final NewIKesaLogger logger = NewKesaLogHelper.getBiz();
    
    @Autowired
    private DCUser dcUser;
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 사용자 목록 조회
     */
    public List<UserPDTO> getListUser(UserPDTO userPDTO) throws NewBusinessException {
        logger.info("=== PCUser.getListUser START ===", "PCUser");
        logger.info("입력 UserPDTO: " + (userPDTO != null ? "NOT NULL" : "NULL"), "PCUser");
        
        if (userPDTO != null) {
            logger.info("입력 UserPDTO - searchKeyword: " + (userPDTO.getSearchKeyword() != null ? userPDTO.getSearchKeyword() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - searchType: " + (userPDTO.getSearchType() != null ? userPDTO.getSearchType() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - roleFilter: " + (userPDTO.getRoleFilter() != null ? userPDTO.getRoleFilter() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - statusFilter: " + (userPDTO.getStatusFilter() != null ? userPDTO.getStatusFilter() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - page: " + (userPDTO.getPage() != null ? userPDTO.getPage() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - size: " + (userPDTO.getSize() != null ? userPDTO.getSize() : "NULL"), "PCUser");
        }
        
        try {
            // NULL 체크
            if (userPDTO == null) {
                logger.error("UserPDTO가 NULL입니다.", "PCUser");
                throw new NewBusinessException("UserPDTO는 필수입니다.");
            }
            
            // UserPDTO를 UserDDTO로 변환
            UserDDTO searchCondition = convertToUserDDTO(userPDTO);
            
            logger.info("변환된 UserDDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"), "PCUser");
            if (searchCondition != null) {
                logger.info("변환된 UserDDTO - searchKeyword: " + (searchCondition.getSearchKeyword() != null ? searchCondition.getSearchKeyword() : "NULL"), "PCUser");
                logger.info("변환된 UserDDTO - searchType: " + (searchCondition.getSearchType() != null ? searchCondition.getSearchType() : "NULL"), "PCUser");
                logger.info("변환된 UserDDTO - page: " + (searchCondition.getPage() != null ? searchCondition.getPage() : "NULL"), "PCUser");
                logger.info("변환된 UserDDTO - size: " + (searchCondition.getSize() != null ? searchCondition.getSize() : "NULL"), "PCUser");
            }
            
            // DC 호출
            List<UserDDTO> userDDTOList = dcUser.getListUser(searchCondition);
            
            logger.info("DC 호출 결과 - userDDTOList: " + (userDDTOList != null ? "NOT NULL, 크기: " + userDDTOList.size() : "NULL"), "PCUser");
            
            // UserDDTO를 UserPDTO로 변환
            List<UserPDTO> resultList = new ArrayList<>();
            if (userDDTOList != null) {
                for (UserDDTO dto : userDDTOList) {
                    UserPDTO pDto = convertToUserPDTO(dto);
                    resultList.add(pDto);
                }
            }
            
            logger.info("최종 결과 - resultList: " + (resultList != null ? "NOT NULL, 크기: " + resultList.size() : "NULL"), "PCUser");
            logger.info("=== PCUser.getListUser END ===", "PCUser");
            
            return resultList;
            
        } catch (NewBusinessException e) {
            logger.error("PCUser.getListUser 중 비즈니스 오류: " + e.getMessage(), "PCUser");
            logger.info("=== PCUser.getListUser END (BUSINESS_ERROR) ===", "PCUser");
            throw e;
        } catch (Exception e) {
            logger.error("PCUser.getListUser 중 오류 발생: " + e.getMessage(), "PCUser");
            logger.info("=== PCUser.getListUser END (ERROR) ===", "PCUser");
            throw new NewBusinessException("사용자 목록 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 상세 조회
     */
    public UserPDTO getUser(String userId) throws NewBusinessException {
        logger.info("=== PCUser.getUser START ===", "PCUser");
        logger.info("입력 userId: " + (userId != null ? userId : "NULL"), "PCUser");
        
        try {
            // NULL 체크
            if (userId == null || userId.trim().isEmpty()) {
                logger.error("userId가 NULL이거나 비어있습니다.", "PCUser");
                throw new NewBusinessException("userId는 필수입니다.");
            }
            
            // DC 호출
            UserDDTO userDDTO = dcUser.getUser(userId);
            
            logger.info("DC 호출 결과 - userDDTO: " + (userDDTO != null ? "NOT NULL" : "NULL"), "PCUser");
            if (userDDTO != null) {
                logger.info("DC 호출 결과 - userDDTO.userId: " + (userDDTO.getUserId() != null ? userDDTO.getUserId() : "NULL"), "PCUser");
                logger.info("DC 호출 결과 - userDDTO.userName: " + (userDDTO.getUserName() != null ? userDDTO.getUserName() : "NULL"), "PCUser");
            }
            
            // UserDDTO를 UserPDTO로 변환
            UserPDTO result = convertToUserPDTO(userDDTO);
            
            logger.info("변환된 UserPDTO: " + (result != null ? "NOT NULL" : "NULL"), "PCUser");
            if (result != null) {
                logger.info("변환된 UserPDTO - userId: " + (result.getUserId() != null ? result.getUserId() : "NULL"), "PCUser");
                logger.info("변환된 UserPDTO - userName: " + (result.getUserName() != null ? result.getUserName() : "NULL"), "PCUser");
            }
            
            logger.info("=== PCUser.getUser END ===", "PCUser");
            
            return result;
            
        } catch (NewBusinessException e) {
            logger.error("PCUser.getUser 중 비즈니스 오류: " + e.getMessage(), "PCUser");
            logger.info("=== PCUser.getUser END (BUSINESS_ERROR) ===", "PCUser");
            throw e;
        } catch (Exception e) {
            logger.error("PCUser.getUser 중 오류 발생: " + e.getMessage(), "PCUser");
            logger.info("=== PCUser.getUser END (ERROR) ===", "PCUser");
            throw new NewBusinessException("사용자 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 등록
     */
    public void createUser(UserPDTO userPDTO) throws NewBusinessException {
        logger.info("=== PCUser.createUser START ===", "PCUser");
        logger.info("입력 UserPDTO: " + (userPDTO != null ? "NOT NULL" : "NULL"), "PCUser");
        
        if (userPDTO != null) {
            logger.info("입력 UserPDTO - userId: " + (userPDTO.getUserId() != null ? userPDTO.getUserId() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - userName: " + (userPDTO.getUserName() != null ? userPDTO.getUserName() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - email: " + (userPDTO.getEmail() != null ? userPDTO.getEmail() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - phone: " + (userPDTO.getPhone() != null ? userPDTO.getPhone() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - role: " + (userPDTO.getRole() != null ? userPDTO.getRole() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - status: " + (userPDTO.getStatus() != null ? userPDTO.getStatus() : "NULL"), "PCUser");
        }
        
        try {
            // NULL 체크
            if (userPDTO == null) {
                logger.error("UserPDTO가 NULL입니다.", "PCUser");
                throw new NewBusinessException("UserPDTO는 필수입니다.");
            }
            
            if (userPDTO.getUserId() == null || userPDTO.getUserId().trim().isEmpty()) {
                logger.error("사용자 ID가 NULL이거나 비어있습니다.", "PCUser");
                throw new NewBusinessException("사용자 ID는 필수입니다.");
            }
            
            // UserPDTO를 UserDDTO로 변환
            UserDDTO userDDTO = convertToUserDDTO(userPDTO);
            
            logger.info("변환된 UserDDTO: " + (userDDTO != null ? "NOT NULL" : "NULL"), "PCUser");
            if (userDDTO != null) {
                logger.info("변환된 UserDDTO - userId: " + (userDDTO.getUserId() != null ? userDDTO.getUserId() : "NULL"), "PCUser");
                logger.info("변환된 UserDDTO - userName: " + (userDDTO.getUserName() != null ? userDDTO.getUserName() : "NULL"), "PCUser");
            }
            
            // DC 호출
            dcUser.createUser(userDDTO);
            
            logger.info("DC 호출 완료 - 사용자 등록 성공", "PCUser");
            logger.info("=== PCUser.createUser END ===", "PCUser");
            
        } catch (NewBusinessException e) {
            logger.error("PCUser.createUser 중 비즈니스 오류: " + e.getMessage(), "PCUser");
            logger.info("=== PCUser.createUser END (BUSINESS_ERROR) ===", "PCUser");
            throw e;
        } catch (Exception e) {
            logger.error("PCUser.createUser 중 오류 발생: " + e.getMessage(), "PCUser");
            logger.info("=== PCUser.createUser END (ERROR) ===", "PCUser");
            throw new NewBusinessException("사용자 등록 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 수정
     */
    public void updateUser(UserPDTO userPDTO) throws NewBusinessException {
        logger.info("=== PCUser.updateUser START ===", "PCUser");
        logger.info("입력 UserPDTO: " + (userPDTO != null ? "NOT NULL" : "NULL"), "PCUser");
        
        if (userPDTO != null) {
            logger.info("입력 UserPDTO - userId: " + (userPDTO.getUserId() != null ? userPDTO.getUserId() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - userName: " + (userPDTO.getUserName() != null ? userPDTO.getUserName() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - email: " + (userPDTO.getEmail() != null ? userPDTO.getEmail() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - phone: " + (userPDTO.getPhone() != null ? userPDTO.getPhone() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - role: " + (userPDTO.getRole() != null ? userPDTO.getRole() : "NULL"), "PCUser");
            logger.info("입력 UserPDTO - status: " + (userPDTO.getStatus() != null ? userPDTO.getStatus() : "NULL"), "PCUser");
        }
        
        try {
            // NULL 체크
            if (userPDTO == null) {
                logger.error("UserPDTO가 NULL입니다.", "PCUser");
                throw new NewBusinessException("UserPDTO는 필수입니다.");
            }
            
            if (userPDTO.getUserId() == null || userPDTO.getUserId().trim().isEmpty()) {
                logger.error("사용자 ID가 NULL이거나 비어있습니다.", "PCUser");
                throw new NewBusinessException("사용자 ID는 필수입니다.");
            }
            
            // UserPDTO를 UserDDTO로 변환
            UserDDTO userDDTO = convertToUserDDTO(userPDTO);
            
            logger.info("변환된 UserDDTO: " + (userDDTO != null ? "NOT NULL" : "NULL"), "PCUser");
            if (userDDTO != null) {
                logger.info("변환된 UserDDTO - userId: " + (userDDTO.getUserId() != null ? userDDTO.getUserId() : "NULL"), "PCUser");
                logger.info("변환된 UserDDTO - userName: " + (userDDTO.getUserName() != null ? userDDTO.getUserName() : "NULL"), "PCUser");
            }
            
            // DC 호출
            dcUser.updateUser(userDDTO);
            
            logger.info("DC 호출 완료 - 사용자 수정 성공", "PCUser");
            logger.info("=== PCUser.updateUser END ===", "PCUser");
            
        } catch (NewBusinessException e) {
            logger.error("PCUser.updateUser 중 비즈니스 오류: " + e.getMessage(), "PCUser");
            logger.info("=== PCUser.updateUser END (BUSINESS_ERROR) ===", "PCUser");
            throw e;
        } catch (Exception e) {
            logger.error("PCUser.updateUser 중 오류 발생: " + e.getMessage(), "PCUser");
            logger.info("=== PCUser.updateUser END (ERROR) ===", "PCUser");
            throw new NewBusinessException("사용자 수정 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 삭제
     */
    public void deleteUser(String userId) throws NewBusinessException {
        logger.info("=== PCUser.deleteUser START ===", "PCUser");
        logger.info("입력 userId: " + (userId != null ? userId : "NULL"), "PCUser");
        
        try {
            // NULL 체크
            if (userId == null || userId.trim().isEmpty()) {
                logger.error("userId가 NULL이거나 비어있습니다.", "PCUser");
                throw new NewBusinessException("userId는 필수입니다.");
            }
            
            // DC 호출
            dcUser.deleteUser(userId);
            
            logger.info("DC 호출 완료 - 사용자 삭제 성공", "PCUser");
            logger.info("=== PCUser.deleteUser END ===", "PCUser");
            
        } catch (NewBusinessException e) {
            logger.error("PCUser.deleteUser 중 비즈니스 오류: " + e.getMessage(), "PCUser");
            logger.info("=== PCUser.deleteUser END (BUSINESS_ERROR) ===", "PCUser");
            throw e;
        } catch (Exception e) {
            logger.error("PCUser.deleteUser 중 오류 발생: " + e.getMessage(), "PCUser");
            logger.info("=== PCUser.deleteUser END (ERROR) ===", "PCUser");
            throw new NewBusinessException("사용자 삭제 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * UserPDTO를 UserDDTO로 변환
     */
    private UserDDTO convertToUserDDTO(UserPDTO pDto) {
        logger.info("=== PCUser.convertToUserDDTO START ===", "PCUser");
        logger.info("입력 UserPDTO: " + (pDto != null ? "NOT NULL" : "NULL"), "PCUser");
        
        if (pDto == null) {
            logger.warn("입력 UserPDTO가 NULL입니다.", "PCUser");
            logger.info("=== PCUser.convertToUserDDTO END (NULL_INPUT) ===", "PCUser");
            return null;
        }
        
        try {
            UserDDTO dto = new UserDDTO();
            
            // 기본 정보 변환
            dto.setUserId(pDto.getUserId());
            dto.setUserName(pDto.getUserName());
            dto.setEmail(pDto.getEmail());
            dto.setPhone(pDto.getPhone());
            dto.setRole(pDto.getRole());
            dto.setStatus(pDto.getStatus());
            
            // 상세 정보 변환
            dto.setDepartment(pDto.getDepartment());
            dto.setPosition(pDto.getPosition());
            dto.setEmployeeId(pDto.getEmployeeId());
            dto.setAddress(pDto.getAddress());
            dto.setEmergencyContact(pDto.getEmergencyContact());
            dto.setEmergencyContactName(pDto.getEmergencyContactName());
            dto.setProfileImageUrl(pDto.getProfileImageUrl());
            dto.setLoginCount(pDto.getLoginCount());
            
            // 검색 조건 변환
            dto.setSearchKeyword(pDto.getSearchKeyword());
            dto.setSearchType(pDto.getSearchType());
            dto.setRoleFilter(pDto.getRoleFilter());
            dto.setStatusFilter(pDto.getStatusFilter());
            dto.setDepartmentFilter(pDto.getDepartmentFilter());
            
            // 페이징 정보 변환
            dto.setPage(pDto.getPage());
            dto.setSize(pDto.getSize());
            dto.setTotalCount(pDto.getTotalCount());
            
            // 날짜 변환
            if (pDto.getCreatedDate() != null) {
                try {
                    dto.setCreatedDate(dateFormat.parse(pDto.getCreatedDate()));
                } catch (Exception e) {
                    logger.warn("생성일 변환 실패: " + pDto.getCreatedDate(), "PCUser");
                }
            }
            
            if (pDto.getUpdatedDate() != null) {
                try {
                    dto.setUpdatedDate(dateFormat.parse(pDto.getUpdatedDate()));
                } catch (Exception e) {
                    logger.warn("수정일 변환 실패: " + pDto.getUpdatedDate(), "PCUser");
                }
            }
            
            if (pDto.getBirthDate() != null) {
                try {
                    dto.setBirthDate(dateFormat.parse(pDto.getBirthDate()));
                } catch (Exception e) {
                    logger.warn("생년월일 변환 실패: " + pDto.getBirthDate(), "PCUser");
                }
            }
            
            if (pDto.getLastLoginDate() != null) {
                try {
                    dto.setLastLoginDate(dateFormat.parse(pDto.getLastLoginDate()));
                } catch (Exception e) {
                    logger.warn("마지막 로그인일 변환 실패: " + pDto.getLastLoginDate(), "PCUser");
                }
            }
            
            logger.info("변환된 UserDDTO: " + (dto != null ? "NOT NULL" : "NULL"), "PCUser");
            if (dto != null) {
                logger.info("변환된 UserDDTO - userId: " + (dto.getUserId() != null ? dto.getUserId() : "NULL"), "PCUser");
                logger.info("변환된 UserDDTO - userName: " + (dto.getUserName() != null ? dto.getUserName() : "NULL"), "PCUser");
            }
            
            logger.info("=== PCUser.convertToUserDDTO END ===", "PCUser");
            return dto;
            
        } catch (Exception e) {
            logger.error("UserPDTO를 UserDDTO로 변환 중 오류 발생: " + e.getMessage(), "PCUser");
            logger.info("=== PCUser.convertToUserDDTO END (ERROR) ===", "PCUser");
            return null;
        }
    }
    
    /**
     * UserDDTO를 UserPDTO로 변환
     */
    private UserPDTO convertToUserPDTO(UserDDTO dto) {
        logger.info("=== PCUser.convertToUserPDTO START ===", "PCUser");
        logger.info("입력 UserDDTO: " + (dto != null ? "NOT NULL" : "NULL"), "PCUser");
        
        if (dto == null) {
            logger.warn("입력 UserDDTO가 NULL입니다.", "PCUser");
            logger.info("=== PCUser.convertToUserPDTO END (NULL_INPUT) ===", "PCUser");
            return null;
        }
        
        try {
            UserPDTO pDto = new UserPDTO();
            
            // 기본 정보 변환
            pDto.setUserId(dto.getUserId());
            pDto.setUserName(dto.getUserName());
            pDto.setEmail(dto.getEmail());
            pDto.setPhone(dto.getPhone());
            pDto.setRole(dto.getRole());
            pDto.setStatus(dto.getStatus());
            
            // 상세 정보 변환
            pDto.setDepartment(dto.getDepartment());
            pDto.setPosition(dto.getPosition());
            pDto.setEmployeeId(dto.getEmployeeId());
            pDto.setAddress(dto.getAddress());
            pDto.setEmergencyContact(dto.getEmergencyContact());
            pDto.setEmergencyContactName(dto.getEmergencyContactName());
            pDto.setProfileImageUrl(dto.getProfileImageUrl());
            pDto.setLoginCount(dto.getLoginCount());
            
            // 검색 조건 변환
            pDto.setSearchKeyword(dto.getSearchKeyword());
            pDto.setSearchType(dto.getSearchType());
            pDto.setRoleFilter(dto.getRoleFilter());
            pDto.setStatusFilter(dto.getStatusFilter());
            pDto.setDepartmentFilter(dto.getDepartmentFilter());
            
            // 페이징 정보 변환
            pDto.setPage(dto.getPage());
            pDto.setSize(dto.getSize());
            pDto.setTotalCount(dto.getTotalCount());
            
            // 날짜 변환
            if (dto.getCreatedDate() != null) {
                pDto.setCreatedDate(dateFormat.format(dto.getCreatedDate()));
            }
            
            if (dto.getUpdatedDate() != null) {
                pDto.setUpdatedDate(dateFormat.format(dto.getUpdatedDate()));
            }
            
            if (dto.getBirthDate() != null) {
                pDto.setBirthDate(dateFormat.format(dto.getBirthDate()));
            }
            
            if (dto.getLastLoginDate() != null) {
                pDto.setLastLoginDate(dateFormat.format(dto.getLastLoginDate()));
            }
            
            logger.info("변환된 UserPDTO: " + (pDto != null ? "NOT NULL" : "NULL"), "PCUser");
            if (pDto != null) {
                logger.info("변환된 UserPDTO - userId: " + (pDto.getUserId() != null ? pDto.getUserId() : "NULL"), "PCUser");
                logger.info("변환된 UserPDTO - userName: " + (pDto.getUserName() != null ? pDto.getUserName() : "NULL"), "PCUser");
            }
            
            logger.info("=== PCUser.convertToUserPDTO END ===", "PCUser");
            return pDto;
            
        } catch (Exception e) {
            logger.error("UserDDTO를 UserPDTO로 변환 중 오류 발생: " + e.getMessage(), "PCUser");
            logger.info("=== PCUser.convertToUserPDTO END (ERROR) ===", "PCUser");
            return null;
        }
    }
}
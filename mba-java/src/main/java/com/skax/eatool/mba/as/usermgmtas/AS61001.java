package com.skax.eatool.mba.as.usermgmtas;

import com.skax.eatool.mba.as.usermgmtas.dto.ApiResponseDto;
import com.skax.eatool.mba.as.usermgmtas.dto.PageRequestDto;
import com.skax.eatool.mba.as.usermgmtas.dto.PageResponseDto;
import com.skax.eatool.mba.as.usermgmtas.dto.UserDto;
import com.skax.eatool.mba.pc.usermgmtpc.UserPC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 사용자 목록 조회 이벤트 서비스
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class AS61001 {
    
    private static final Logger logger = LoggerFactory.getLogger(AS61001.class);
    
    @Autowired
    private UserPC userPC;
    
    /**
     * 사용자 목록 조회 이벤트 처리
     * 
     * @param pageRequest 페이지 요청 정보
     * @return 사용자 목록 응답
     */
    public ApiResponseDto<PageResponseDto<UserDto>> getUserList(PageRequestDto pageRequest) {
        logger.info("=== AS61001.getUserList START ===");
        logger.info("입력 PageRequestDto: {}", pageRequest);
        
        try {
            // 입력 검증
            if (pageRequest == null) {
                logger.error("PageRequestDto가 NULL입니다.");
                return ApiResponseDto.error("400", "페이지 요청 정보가 필요합니다.");
            }
            
            // PC 호출하여 사용자 목록 조회
            List<UserDto> userList = userPC.getUserList(pageRequest);
            Long totalCount = userPC.getUserCount(pageRequest);
            
            // PageResponseDto 생성
            PageResponseDto<UserDto> pageResponse = new PageResponseDto<>(
                userList, 
                pageRequest.getPage(), 
                pageRequest.getSize(), 
                totalCount
            );
            
            logger.info("조회 결과 - 사용자 수: {}, 전체 개수: {}", 
                userList != null ? userList.size() : 0, totalCount);
            logger.info("=== AS61001.getUserList END ===");
            
            return ApiResponseDto.success(pageResponse, "사용자 목록 조회가 완료되었습니다.");
            
        } catch (Exception e) {
            logger.error("AS61001.getUserList 중 오류 발생: {}", e.getMessage(), e);
            return ApiResponseDto.error("500", "사용자 목록 조회 중 오류가 발생했습니다.", e.getMessage());
        }
    }
    
    /**
     * 사용자 검색 이벤트 처리
     * 
     * @param keyword 검색 키워드
     * @param searchField 검색 필드
     * @param pageRequest 페이지 요청 정보
     * @return 검색 결과 응답
     */
    public ApiResponseDto<PageResponseDto<UserDto>> searchUsers(String keyword, String searchField, PageRequestDto pageRequest) {
        logger.info("=== AS61001.searchUsers START ===");
        logger.info("검색 키워드: {}, 검색 필드: {}", keyword, searchField);
        logger.info("페이지 요청: {}", pageRequest);
        
        try {
            // 입력 검증
            if (keyword == null || keyword.trim().isEmpty()) {
                logger.error("검색 키워드가 비어있습니다.");
                return ApiResponseDto.error("400", "검색 키워드가 필요합니다.");
            }
            
            if (pageRequest == null) {
                pageRequest = new PageRequestDto(0, 10);
            }
            
            // 검색 조건 설정
            pageRequest.setKeyword(keyword);
            pageRequest.setSearchField(searchField);
            
            // PC 호출하여 사용자 검색
            List<UserDto> userList = userPC.searchUsers(pageRequest);
            Long totalCount = userPC.getSearchUserCount(pageRequest);
            
            // PageResponseDto 생성
            PageResponseDto<UserDto> pageResponse = new PageResponseDto<>(
                userList, 
                pageRequest.getPage(), 
                pageRequest.getSize(), 
                totalCount
            );
            
            logger.info("검색 결과 - 사용자 수: {}, 전체 개수: {}", 
                userList != null ? userList.size() : 0, totalCount);
            logger.info("=== AS61001.searchUsers END ===");
            
            return ApiResponseDto.success(pageResponse, "사용자 검색이 완료되었습니다.");
            
        } catch (Exception e) {
            logger.error("AS61001.searchUsers 중 오류 발생: {}", e.getMessage(), e);
            return ApiResponseDto.error("500", "사용자 검색 중 오류가 발생했습니다.", e.getMessage());
        }
    }
} 
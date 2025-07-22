package com.skax.eatool.mbc.as.usermgtas;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;
import com.skax.eatool.mbc.pc.dto.UserPDTO;
import com.skax.eatool.mbc.pc.userpc.PCUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 사용자 관리 Application Service
 * 
 * 프로그램명: ASMBC75001.java
 * 설명: 사용자 관리 애플리케이션 서비스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 사용자 목록 조회
 * - 사용자 상세 조회
 * - 사용자 등록/수정/삭제
 * - AC와 PC 간의 데이터 전달
 * 
 * @version 1.0
 */
@Service
public class ASMBC75001 implements NewIApplicationService {
    
    private static final NewIKesaLogger logger = NewKesaLogHelper.getBiz();
    
    @Autowired
    private PCUser pcUser;
    
    /**
     * 애플리케이션 서비스 실행
     */
    public NewKBData execute(NewKBData reqData) throws NewBusinessException {
        logger.info("=== ASMBC75001.execute START ===", "ASMBC75001");
        logger.info("입력 NewKBData: " + (reqData != null ? "NOT NULL" : "NULL"), "ASMBC75001");
        
        try {
            // NULL 체크
            if (reqData == null) {
                logger.error("NewKBData가 NULL입니다.", "ASMBC75001");
                throw new NewBusinessException("NewKBData는 필수입니다.");
            }
            
            if (reqData.getInputGenericDto() == null) {
                logger.error("InputGenericDto가 NULL입니다.", "ASMBC75001");
                throw new NewBusinessException("InputGenericDto는 필수입니다.");
            }
            
            // 입력 데이터에서 command와 UserPDTO 추출
            String command = reqData.getInputGenericDto().getString("command");
            UserPDTO userPDTO = (UserPDTO) reqData.getInputGenericDto().get("UserPDTO");
            
            logger.info("추출된 command: " + (command != null ? command : "NULL"), "ASMBC75001");
            logger.info("추출된 UserPDTO: " + (userPDTO != null ? "NOT NULL" : "NULL"), "ASMBC75001");
            
            if (userPDTO != null) {
                logger.info("추출된 UserPDTO - userId: " + (userPDTO.getUserId() != null ? userPDTO.getUserId() : "NULL"), "ASMBC75001");
                logger.info("추출된 UserPDTO - userName: " + (userPDTO.getUserName() != null ? userPDTO.getUserName() : "NULL"), "ASMBC75001");
                logger.info("추출된 UserPDTO - email: " + (userPDTO.getEmail() != null ? userPDTO.getEmail() : "NULL"), "ASMBC75001");
            }
            
            // command에 따른 처리 분기
            NewKBData resultData = new NewKBData();
            
            switch (command) {
                case "LIST":
                    resultData = handleList(userPDTO);
                    break;
                case "READ":
                    resultData = handleRead(userPDTO);
                    break;
                case "CREATE":
                    resultData = handleCreate(userPDTO);
                    break;
                case "UPDATE":
                    resultData = handleUpdate(userPDTO);
                    break;
                case "DELETE":
                    resultData = handleDelete(userPDTO);
                    break;
                default:
                    throw new NewBusinessException("지원하지 않는 명령입니다: " + command);
            }
            
            logger.info("처리 완료: " + command, "ASMBC75001");
            logger.info("결과 NewKBData: " + (resultData != null ? "NOT NULL" : "NULL"), "ASMBC75001");
            logger.info("=== ASMBC75001.execute END ===", "ASMBC75001");
            
            return resultData;
            
        } catch (NewBusinessException e) {
            logger.error("ASMBC75001.execute 중 비즈니스 오류: " + e.getMessage(), "ASMBC75001");
            logger.info("=== ASMBC75001.execute END (BUSINESS_ERROR) ===", "ASMBC75001");
            throw e;
        } catch (Exception e) {
            logger.error("ASMBC75001.execute 중 오류 발생: " + e.getMessage(), "ASMBC75001");
            logger.info("=== ASMBC75001.execute END (ERROR) ===", "ASMBC75001");
            throw new NewBusinessException("애플리케이션 서비스 실행 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 목록 조회 처리
     */
    private NewKBData handleList(UserPDTO userPDTO) throws NewBusinessException {
        logger.info("=== ASMBC75001.handleList START ===", "ASMBC75001");
        logger.info("입력 UserPDTO: " + (userPDTO != null ? "NOT NULL" : "NULL"), "ASMBC75001");
        
        if (userPDTO != null) {
            logger.info("입력 UserPDTO - searchKeyword: " + (userPDTO.getSearchKeyword() != null ? userPDTO.getSearchKeyword() : "NULL"), "ASMBC75001");
            logger.info("입력 UserPDTO - searchType: " + (userPDTO.getSearchType() != null ? userPDTO.getSearchType() : "NULL"), "ASMBC75001");
            logger.info("입력 UserPDTO - page: " + (userPDTO.getPage() != null ? userPDTO.getPage() : "NULL"), "ASMBC75001");
            logger.info("입력 UserPDTO - size: " + (userPDTO.getSize() != null ? userPDTO.getSize() : "NULL"), "ASMBC75001");
        }
        
        try {
            // NULL 체크
            if (userPDTO == null) {
                logger.error("UserPDTO가 NULL입니다.", "ASMBC75001");
                throw new NewBusinessException("UserPDTO는 필수입니다.");
            }
            
            // PC 호출
            List<UserPDTO> userList = pcUser.getListUser(userPDTO);
            
            logger.info("PC 호출 결과 - userList: " + (userList != null ? "NOT NULL, 크기: " + userList.size() : "NULL"), "ASMBC75001");
            
            // 결과를 NewKBData에 저장
            NewKBData resultData = new NewKBData();
            resultData.getOutputGenericDto().put("UserPDTOList", userList);
            
            logger.info("결과 NewKBData 생성 완료", "ASMBC75001");
            logger.info("=== ASMBC75001.handleList END ===", "ASMBC75001");
            
            return resultData;
            
        } catch (NewBusinessException e) {
            logger.error("ASMBC75001.handleList 중 비즈니스 오류: " + e.getMessage(), "ASMBC75001");
            logger.info("=== ASMBC75001.handleList END (BUSINESS_ERROR) ===", "ASMBC75001");
            throw e;
        } catch (Exception e) {
            logger.error("ASMBC75001.handleList 중 오류 발생: " + e.getMessage(), "ASMBC75001");
            logger.info("=== ASMBC75001.handleList END (ERROR) ===", "ASMBC75001");
            throw new NewBusinessException("사용자 목록 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 상세 조회 처리
     */
    private NewKBData handleRead(UserPDTO userPDTO) throws NewBusinessException {
        logger.info("=== ASMBC75001.handleRead START ===", "ASMBC75001");
        logger.info("입력 UserPDTO: " + (userPDTO != null ? "NOT NULL" : "NULL"), "ASMBC75001");
        
        if (userPDTO != null) {
            logger.info("입력 UserPDTO - userId: " + (userPDTO.getUserId() != null ? userPDTO.getUserId() : "NULL"), "ASMBC75001");
        }
        
        try {
            // NULL 체크
            if (userPDTO == null) {
                logger.error("UserPDTO가 NULL입니다.", "ASMBC75001");
                throw new NewBusinessException("UserPDTO는 필수입니다.");
            }
            
            if (userPDTO.getUserId() == null || userPDTO.getUserId().trim().isEmpty()) {
                logger.error("사용자 ID가 NULL이거나 비어있습니다.", "ASMBC75001");
                throw new NewBusinessException("사용자 ID는 필수입니다.");
            }
            
            // PC 호출
            UserPDTO user = pcUser.getUser(userPDTO.getUserId());
            
            logger.info("PC 호출 결과 - user: " + (user != null ? "NOT NULL" : "NULL"), "ASMBC75001");
            if (user != null) {
                logger.info("PC 호출 결과 - user.userId: " + (user.getUserId() != null ? user.getUserId() : "NULL"), "ASMBC75001");
                logger.info("PC 호출 결과 - user.userName: " + (user.getUserName() != null ? user.getUserName() : "NULL"), "ASMBC75001");
            }
            
            // 결과를 NewKBData에 저장
            NewKBData resultData = new NewKBData();
            resultData.getOutputGenericDto().put("UserPDTO", user);
            
            logger.info("결과 NewKBData 생성 완료", "ASMBC75001");
            logger.info("=== ASMBC75001.handleRead END ===", "ASMBC75001");
            
            return resultData;
            
        } catch (NewBusinessException e) {
            logger.error("ASMBC75001.handleRead 중 비즈니스 오류: " + e.getMessage(), "ASMBC75001");
            logger.info("=== ASMBC75001.handleRead END (BUSINESS_ERROR) ===", "ASMBC75001");
            throw e;
        } catch (Exception e) {
            logger.error("ASMBC75001.handleRead 중 오류 발생: " + e.getMessage(), "ASMBC75001");
            logger.info("=== ASMBC75001.handleRead END (ERROR) ===", "ASMBC75001");
            throw new NewBusinessException("사용자 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 등록 처리
     */
    private NewKBData handleCreate(UserPDTO userPDTO) throws NewBusinessException {
        logger.info("=== ASMBC75001.handleCreate START ===", "ASMBC75001");
        logger.info("입력 UserPDTO: " + (userPDTO != null ? "NOT NULL" : "NULL"), "ASMBC75001");
        
        if (userPDTO != null) {
            logger.info("입력 UserPDTO - userId: " + (userPDTO.getUserId() != null ? userPDTO.getUserId() : "NULL"), "ASMBC75001");
            logger.info("입력 UserPDTO - userName: " + (userPDTO.getUserName() != null ? userPDTO.getUserName() : "NULL"), "ASMBC75001");
            logger.info("입력 UserPDTO - email: " + (userPDTO.getEmail() != null ? userPDTO.getEmail() : "NULL"), "ASMBC75001");
        }
        
        try {
            // NULL 체크
            if (userPDTO == null) {
                logger.error("UserPDTO가 NULL입니다.", "ASMBC75001");
                throw new NewBusinessException("UserPDTO는 필수입니다.");
            }
            
            if (userPDTO.getUserId() == null || userPDTO.getUserId().trim().isEmpty()) {
                logger.error("사용자 ID가 NULL이거나 비어있습니다.", "ASMBC75001");
                throw new NewBusinessException("사용자 ID는 필수입니다.");
            }
            
            // PC 호출
            pcUser.createUser(userPDTO);
            
            logger.info("PC 호출 완료 - 사용자 등록 성공", "ASMBC75001");
            
            // 결과를 NewKBData에 저장
            NewKBData resultData = new NewKBData();
            resultData.getOutputGenericDto().put("UserPDTO", userPDTO);
            
            logger.info("결과 NewKBData 생성 완료", "ASMBC75001");
            logger.info("=== ASMBC75001.handleCreate END ===", "ASMBC75001");
            
            return resultData;
            
        } catch (NewBusinessException e) {
            logger.error("ASMBC75001.handleCreate 중 비즈니스 오류: " + e.getMessage(), "ASMBC75001");
            logger.info("=== ASMBC75001.handleCreate END (BUSINESS_ERROR) ===", "ASMBC75001");
            throw e;
        } catch (Exception e) {
            logger.error("ASMBC75001.handleCreate 중 오류 발생: " + e.getMessage(), "ASMBC75001");
            logger.info("=== ASMBC75001.handleCreate END (ERROR) ===", "ASMBC75001");
            throw new NewBusinessException("사용자 등록 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 수정 처리
     */
    private NewKBData handleUpdate(UserPDTO userPDTO) throws NewBusinessException {
        logger.info("=== ASMBC75001.handleUpdate START ===", "ASMBC75001");
        logger.info("입력 UserPDTO: " + (userPDTO != null ? "NOT NULL" : "NULL"), "ASMBC75001");
        
        if (userPDTO != null) {
            logger.info("입력 UserPDTO - userId: " + (userPDTO.getUserId() != null ? userPDTO.getUserId() : "NULL"), "ASMBC75001");
            logger.info("입력 UserPDTO - userName: " + (userPDTO.getUserName() != null ? userPDTO.getUserName() : "NULL"), "ASMBC75001");
            logger.info("입력 UserPDTO - email: " + (userPDTO.getEmail() != null ? userPDTO.getEmail() : "NULL"), "ASMBC75001");
        }
        
        try {
            // NULL 체크
            if (userPDTO == null) {
                logger.error("UserPDTO가 NULL입니다.", "ASMBC75001");
                throw new NewBusinessException("UserPDTO는 필수입니다.");
            }
            
            if (userPDTO.getUserId() == null || userPDTO.getUserId().trim().isEmpty()) {
                logger.error("사용자 ID가 NULL이거나 비어있습니다.", "ASMBC75001");
                throw new NewBusinessException("사용자 ID는 필수입니다.");
            }
            
            // PC 호출
            pcUser.updateUser(userPDTO);
            
            logger.info("PC 호출 완료 - 사용자 수정 성공", "ASMBC75001");
            
            // 결과를 NewKBData에 저장
            NewKBData resultData = new NewKBData();
            resultData.getOutputGenericDto().put("UserPDTO", userPDTO);
            
            logger.info("결과 NewKBData 생성 완료", "ASMBC75001");
            logger.info("=== ASMBC75001.handleUpdate END ===", "ASMBC75001");
            
            return resultData;
            
        } catch (NewBusinessException e) {
            logger.error("ASMBC75001.handleUpdate 중 비즈니스 오류: " + e.getMessage(), "ASMBC75001");
            logger.info("=== ASMBC75001.handleUpdate END (BUSINESS_ERROR) ===", "ASMBC75001");
            throw e;
        } catch (Exception e) {
            logger.error("ASMBC75001.handleUpdate 중 오류 발생: " + e.getMessage(), "ASMBC75001");
            logger.info("=== ASMBC75001.handleUpdate END (ERROR) ===", "ASMBC75001");
            throw new NewBusinessException("사용자 수정 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 삭제 처리
     */
    private NewKBData handleDelete(UserPDTO userPDTO) throws NewBusinessException {
        logger.info("=== ASMBC75001.handleDelete START ===", "ASMBC75001");
        logger.info("입력 UserPDTO: " + (userPDTO != null ? "NOT NULL" : "NULL"), "ASMBC75001");
        
        if (userPDTO != null) {
            logger.info("입력 UserPDTO - userId: " + (userPDTO.getUserId() != null ? userPDTO.getUserId() : "NULL"), "ASMBC75001");
        }
        
        try {
            // NULL 체크
            if (userPDTO == null) {
                logger.error("UserPDTO가 NULL입니다.", "ASMBC75001");
                throw new NewBusinessException("UserPDTO는 필수입니다.");
            }
            
            if (userPDTO.getUserId() == null || userPDTO.getUserId().trim().isEmpty()) {
                logger.error("사용자 ID가 NULL이거나 비어있습니다.", "ASMBC75001");
                throw new NewBusinessException("사용자 ID는 필수입니다.");
            }
            
            // PC 호출
            pcUser.deleteUser(userPDTO.getUserId());
            
            logger.info("PC 호출 완료 - 사용자 삭제 성공", "ASMBC75001");
            
            // 결과를 NewKBData에 저장
            NewKBData resultData = new NewKBData();
            resultData.getOutputGenericDto().put("deletedUserId", userPDTO.getUserId());
            
            logger.info("결과 NewKBData 생성 완료", "ASMBC75001");
            logger.info("=== ASMBC75001.handleDelete END ===", "ASMBC75001");
            
            return resultData;
            
        } catch (NewBusinessException e) {
            logger.error("ASMBC75001.handleDelete 중 비즈니스 오류: " + e.getMessage(), "ASMBC75001");
            logger.info("=== ASMBC75001.handleDelete END (BUSINESS_ERROR) ===", "ASMBC75001");
            throw e;
        } catch (Exception e) {
            logger.error("ASMBC75001.handleDelete 중 오류 발생: " + e.getMessage(), "ASMBC75001");
            logger.info("=== ASMBC75001.handleDelete END (ERROR) ===", "ASMBC75001");
            throw new NewBusinessException("사용자 삭제 중 오류가 발생했습니다.", e);
        }
    }
} 
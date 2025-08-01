package com.skax.eatool.mbb.pc.usermgtpc;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.dc.dto.UserDDTO;
import com.skax.eatool.mbb.dc.usermgtdc.DCUserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 사용자 관리 Process Component
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Component
public class PCUserManagement {

    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());

    @Autowired
    private DCUserManagement dcUserManagement;

    /**
     * 사용자를 생성합니다.
     *
     * @param user 사용자 정보
     * @return 생성된 사용자 정보
     */
    public UserDDTO createUser(UserDDTO user) throws NewBusinessException {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== PCUserManagement.createUser START ===");
            logger.info(className, "사용자 생성 프로세스 시작: " + user.getUsername());

            // 비즈니스 규칙 검증
            if (!validateUserCreation(user)) {
                logger.error(className, "사용자 생성 비즈니스 규칙 검증 실패");
                throw new RuntimeException("사용자 생성 검증 실패");
            }

            // DC 호출하여 사용자 생성
            UserDDTO result = dcUserManagement.createUser(user);

            logger.info(className, "사용자 생성 프로세스 완료: " + result.getUsername());
            logger.info(className, "=== PCUserManagement.createUser END ===");
            return result;

        } catch (NewBusinessException e) {
            logger.error(className, "사용자 생성 비즈니스 오류: " + e.getMessage());
            logger.error(className, "=== PCUserManagement.createUser ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error(className, "사용자 생성 프로세스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== PCUserManagement.createUser ERROR ===");
            throw e;
        }
    }

    /**
     * 사용자 인증을 수행합니다.
     *
     * @param username 사용자명
     * @param password 비밀번호
     * @return 인증된 사용자 정보
     */
    public UserDDTO authenticateUser(String username, String password) throws NewBusinessException {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== PCUserManagement.authenticateUser START ===");
            logger.info(className, "사용자 인증 프로세스 시작: " + username);

            // 입력값 검증
            if (!validateAuthenticationInput(username, password)) {
                logger.error(className, "인증 입력값 검증 실패");
                throw new RuntimeException("인증 입력값 검증 실패");
            }

            // DC 호출하여 사용자 인증
            UserDDTO result = dcUserManagement.authenticateUser(username, password);

            logger.info(className, "사용자 인증 프로세스 완료: " + result.getUsername());
            logger.info(className, "=== PCUserManagement.authenticateUser END ===");
            return result;

        } catch (NewBusinessException e) {
            logger.error(className, "사용자 인증 비즈니스 오류: " + e.getMessage());
            logger.error(className, "=== PCUserManagement.authenticateUser ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error(className, "사용자 인증 프로세스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== PCUserManagement.authenticateUser ERROR ===");
            throw e;
        }
    }

    /**
     * 모든 사용자를 조회합니다.
     *
     * @return 사용자 목록
     */
    public List<UserDDTO> getAllUsers() throws NewBusinessException {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== PCUserManagement.getAllUsers START ===");
            logger.info(className, "사용자 목록 조회 프로세스 시작");

            // DC 호출하여 사용자 목록 조회
            List<UserDDTO> result = dcUserManagement.getAllUsers();

            logger.info(className, "사용자 목록 조회 프로세스 완료: " + result.size() + "명");
            logger.info(className, "=== PCUserManagement.getAllUsers END ===");
            return result;

        } catch (NewBusinessException e) {
            logger.error(className, "사용자 목록 조회 비즈니스 오류: " + e.getMessage());
            logger.error(className, "=== PCUserManagement.getAllUsers ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error(className, "사용자 목록 조회 프로세스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== PCUserManagement.getAllUsers ERROR ===");
            throw e;
        }
    }

    /**
     * 사용자 ID로 사용자를 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 사용자 정보
     */
    public UserDDTO getUserById(String userId) throws NewBusinessException {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== PCUserManagement.getUserById START ===");
            logger.info(className, "사용자 ID 조회 프로세스 시작: " + userId);

            // 입력값 검증
            if (!validateUserId(userId)) {
                logger.error(className, "사용자 ID 검증 실패");
                throw new RuntimeException("사용자 ID 검증 실패");
            }

            // DC 호출하여 사용자 조회
            UserDDTO result = dcUserManagement.getUserById(userId);

            logger.info(className, "사용자 ID 조회 프로세스 완료: " + result.getUsername());
            logger.info(className, "=== PCUserManagement.getUserById END ===");
            return result;

        } catch (NewBusinessException e) {
            logger.error(className, "사용자 ID 조회 비즈니스 오류: " + e.getMessage());
            logger.error(className, "=== PCUserManagement.getUserById ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error(className, "사용자 ID 조회 프로세스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== PCUserManagement.getUserById ERROR ===");
            throw e;
        }
    }

    /**
     * 사용자명으로 사용자를 조회합니다.
     *
     * @param username 사용자명
     * @return 사용자 정보
     */
    public UserDDTO getUserByUsername(String username) throws NewBusinessException {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== PCUserManagement.getUserByUsername START ===");
            logger.info(className, "사용자명 조회 프로세스 시작: " + username);

            // 입력값 검증
            if (!validateUsername(username)) {
                logger.error(className, "사용자명 검증 실패");
                throw new RuntimeException("사용자명 검증 실패");
            }

            // DC 호출하여 사용자 조회
            UserDDTO result = dcUserManagement.getUserByUsername(username);

            logger.info(className, "사용자명 조회 프로세스 완료: " + result.getUsername());
            logger.info(className, "=== PCUserManagement.getUserByUsername END ===");
            return result;

        } catch (NewBusinessException e) {
            logger.error(className, "사용자명 조회 비즈니스 오류: " + e.getMessage());
            logger.error(className, "=== PCUserManagement.getUserByUsername ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error(className, "사용자명 조회 프로세스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== PCUserManagement.getUserByUsername ERROR ===");
            throw e;
        }
    }

    /**
     * 사용자 정보를 수정합니다.
     *
     * @param userId 사용자 ID
     * @param user 수정할 사용자 정보
     * @return 수정된 사용자 정보
     */
    public UserDDTO updateUser(String userId, UserDDTO user) throws NewBusinessException {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== PCUserManagement.updateUser START ===");
            logger.info(className, "사용자 수정 프로세스 시작: " + userId);

            // 비즈니스 규칙 검증
            if (!validateUserUpdate(userId, user)) {
                logger.error(className, "사용자 수정 비즈니스 규칙 검증 실패");
                throw new RuntimeException("사용자 수정 검증 실패");
            }

            // DC 호출하여 사용자 수정
            UserDDTO result = dcUserManagement.updateUser(userId, user);

            logger.info(className, "사용자 수정 프로세스 완료: " + result.getUsername());
            logger.info(className, "=== PCUserManagement.updateUser END ===");
            return result;

        } catch (NewBusinessException e) {
            logger.error(className, "사용자 수정 비즈니스 오류: " + e.getMessage());
            logger.error(className, "=== PCUserManagement.updateUser ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error(className, "사용자 수정 프로세스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== PCUserManagement.updateUser ERROR ===");
            throw e;
        }
    }

    /**
     * 사용자를 삭제합니다.
     *
     * @param userId 사용자 ID
     * @return 삭제 결과
     */
    public boolean deleteUser(String userId) throws NewBusinessException {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== PCUserManagement.deleteUser START ===");
            logger.info(className, "사용자 삭제 프로세스 시작: " + userId);

            // 입력값 검증
            if (!validateUserId(userId)) {
                logger.error(className, "사용자 ID 검증 실패");
                throw new RuntimeException("사용자 ID 검증 실패");
            }

            // DC 호출하여 사용자 삭제
            boolean result = dcUserManagement.deleteUser(userId);

            logger.info(className, "사용자 삭제 프로세스 완료: " + result);
            logger.info(className, "=== PCUserManagement.deleteUser END ===");
            return result;

        } catch (NewBusinessException e) {
            logger.error(className, "사용자 삭제 비즈니스 오류: " + e.getMessage());
            logger.error(className, "=== PCUserManagement.deleteUser ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error(className, "사용자 삭제 프로세스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== PCUserManagement.deleteUser ERROR ===");
            throw e;
        }
    }

    /**
     * 사용자 생성 비즈니스 규칙을 검증합니다.
     *
     * @param user 사용자 정보
     * @return 검증 결과
     */
    private boolean validateUserCreation(UserDDTO user) {
        if (user == null) {
            return false;
        }

        // 사용자명 검증
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return false;
        }

        // 비밀번호 검증
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return false;
        }

        // 이메일 검증
        if (user.getEmail() != null && !user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return false;
        }

        return true;
    }

    /**
     * 인증 입력값을 검증합니다.
     *
     * @param username 사용자명
     * @param password 비밀번호
     * @return 검증 결과
     */
    private boolean validateAuthenticationInput(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * 사용자 ID를 검증합니다.
     *
     * @param userId 사용자 ID
     * @return 검증 결과
     */
    private boolean validateUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * 사용자명을 검증합니다.
     *
     * @param username 사용자명
     * @return 검증 결과
     */
    private boolean validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * 사용자 수정 비즈니스 규칙을 검증합니다.
     *
     * @param userId 사용자 ID
     * @param user 사용자 정보
     * @return 검증 결과
     */
    private boolean validateUserUpdate(String userId, UserDDTO user) {
        if (!validateUserId(userId)) {
            return false;
        }

        if (user == null) {
            return false;
        }

        // 이메일 검증
        if (user.getEmail() != null && !user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return false;
        }

        return true;
    }
} 
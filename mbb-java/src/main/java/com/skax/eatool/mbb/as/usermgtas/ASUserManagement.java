package com.skax.eatool.mbb.as.usermgtas;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.dc.dto.UserDDTO;
import com.skax.eatool.mbb.pc.usermgtpc.PCUserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 사용자 관리 Application Service
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class ASUserManagement {

    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());

    @Autowired
    private PCUserManagement pcUserManagement;

    /**
     * 사용자를 생성합니다.
     *
     * @param user 사용자 정보
     * @return 생성된 사용자 정보
     * @throws NewBusinessException 비즈니스 예외
     */
    @Transactional
    public UserDDTO createUser(UserDDTO user) throws NewBusinessException {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== ASUserManagement.createUser START ===");
            logger.info(className, "사용자 생성 서비스 시작: " + user.getUsername());

            // 입력값 검증
            if (!validateUserCreation(user)) {
                logger.error(className, "사용자 생성 입력값 검증 실패");
                throw new NewBusinessException("사용자 생성 입력값 검증 실패");
            }

            // PC 호출하여 사용자 생성
            UserDDTO result = pcUserManagement.createUser(user);

            logger.info(className, "사용자 생성 서비스 완료: " + result.getUsername());
            logger.info(className, "=== ASUserManagement.createUser END ===");
            return result;

        } catch (NewBusinessException e) {
            logger.error(className, "사용자 생성 비즈니스 오류: " + e.getMessage());
            logger.error(className, "=== ASUserManagement.createUser ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error(className, "사용자 생성 서비스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== ASUserManagement.createUser ERROR ===");
            throw new NewBusinessException("사용자 생성 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 사용자 인증을 수행합니다.
     *
     * @param username 사용자명
     * @param password 비밀번호
     * @return 인증된 사용자 정보
     * @throws NewBusinessException 비즈니스 예외
     */
    @Transactional(readOnly = true)
    public UserDDTO authenticateUser(String username, String password) throws NewBusinessException {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== ASUserManagement.authenticateUser START ===");
            logger.info(className, "사용자 인증 서비스 시작: " + username);

            // 입력값 검증
            if (!validateAuthenticationInput(username, password)) {
                logger.error(className, "사용자 인증 입력값 검증 실패");
                throw new NewBusinessException("사용자 인증 입력값 검증 실패");
            }

            // PC 호출하여 사용자 인증
            UserDDTO result = pcUserManagement.authenticateUser(username, password);

            logger.info(className, "사용자 인증 서비스 완료: " + result.getUsername());
            logger.info(className, "=== ASUserManagement.authenticateUser END ===");
            return result;

        } catch (NewBusinessException e) {
            logger.error(className, "사용자 인증 비즈니스 오류: " + e.getMessage());
            logger.error(className, "=== ASUserManagement.authenticateUser ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error(className, "사용자 인증 서비스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== ASUserManagement.authenticateUser ERROR ===");
            throw new NewBusinessException("사용자 인증 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 모든 사용자를 조회합니다.
     *
     * @return 사용자 목록
     * @throws NewBusinessException 비즈니스 예외
     */
    @Transactional(readOnly = true)
    public List<UserDDTO> getAllUsers() throws NewBusinessException {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== ASUserManagement.getAllUsers START ===");
            logger.info(className, "사용자 목록 조회 서비스 시작");

            // PC 호출하여 사용자 목록 조회
            List<UserDDTO> result = pcUserManagement.getAllUsers();

            logger.info(className, "사용자 목록 조회 서비스 완료: " + result.size() + "명");
            logger.info(className, "=== ASUserManagement.getAllUsers END ===");
            return result;

        } catch (NewBusinessException e) {
            logger.error(className, "사용자 목록 조회 비즈니스 오류: " + e.getMessage());
            logger.error(className, "=== ASUserManagement.getAllUsers ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error(className, "사용자 목록 조회 서비스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== ASUserManagement.getAllUsers ERROR ===");
            throw new NewBusinessException("사용자 목록 조회 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 사용자 ID로 사용자를 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 사용자 정보
     * @throws NewBusinessException 비즈니스 예외
     */
    @Transactional(readOnly = true)
    public UserDDTO getUserById(String userId) throws NewBusinessException {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== ASUserManagement.getUserById START ===");
            logger.info(className, "사용자 ID 조회 서비스 시작: " + userId);

            // 입력값 검증
            if (!validateUserId(userId)) {
                logger.error(className, "사용자 ID 검증 실패");
                throw new NewBusinessException("사용자 ID 검증 실패");
            }

            // PC 호출하여 사용자 조회
            UserDDTO result = pcUserManagement.getUserById(userId);

            logger.info(className, "사용자 ID 조회 서비스 완료: " + result.getUsername());
            logger.info(className, "=== ASUserManagement.getUserById END ===");
            return result;

        } catch (NewBusinessException e) {
            logger.error(className, "사용자 ID 조회 비즈니스 오류: " + e.getMessage());
            logger.error(className, "=== ASUserManagement.getUserById ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error(className, "사용자 ID 조회 서비스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== ASUserManagement.getUserById ERROR ===");
            throw new NewBusinessException("사용자 ID 조회 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 사용자명으로 사용자를 조회합니다.
     *
     * @param username 사용자명
     * @return 사용자 정보
     * @throws NewBusinessException 비즈니스 예외
     */
    @Transactional(readOnly = true)
    public UserDDTO getUserByUsername(String username) throws NewBusinessException {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== ASUserManagement.getUserByUsername START ===");
            logger.info(className, "사용자명 조회 서비스 시작: " + username);

            // 입력값 검증
            if (!validateUsername(username)) {
                logger.error(className, "사용자명 검증 실패");
                throw new NewBusinessException("사용자명 검증 실패");
            }

            // PC 호출하여 사용자 조회
            UserDDTO result = pcUserManagement.getUserByUsername(username);

            logger.info(className, "사용자명 조회 서비스 완료: " + result.getUsername());
            logger.info(className, "=== ASUserManagement.getUserByUsername END ===");
            return result;

        } catch (NewBusinessException e) {
            logger.error(className, "사용자명 조회 비즈니스 오류: " + e.getMessage());
            logger.error(className, "=== ASUserManagement.getUserByUsername ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error(className, "사용자명 조회 서비스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== ASUserManagement.getUserByUsername ERROR ===");
            throw new NewBusinessException("사용자명 조회 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 사용자 정보를 수정합니다.
     *
     * @param userId 사용자 ID
     * @param user 수정할 사용자 정보
     * @return 수정된 사용자 정보
     * @throws NewBusinessException 비즈니스 예외
     */
    @Transactional
    public UserDDTO updateUser(String userId, UserDDTO user) throws NewBusinessException {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== ASUserManagement.updateUser START ===");
            logger.info(className, "사용자 수정 서비스 시작: " + userId);

            // 입력값 검증
            if (!validateUserUpdate(userId, user)) {
                logger.error(className, "사용자 수정 입력값 검증 실패");
                throw new NewBusinessException("사용자 수정 입력값 검증 실패");
            }

            // PC 호출하여 사용자 수정
            UserDDTO result = pcUserManagement.updateUser(userId, user);

            logger.info(className, "사용자 수정 서비스 완료: " + result.getUsername());
            logger.info(className, "=== ASUserManagement.updateUser END ===");
            return result;

        } catch (NewBusinessException e) {
            logger.error(className, "사용자 수정 비즈니스 오류: " + e.getMessage());
            logger.error(className, "=== ASUserManagement.updateUser ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error(className, "사용자 수정 서비스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== ASUserManagement.updateUser ERROR ===");
            throw new NewBusinessException("사용자 수정 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 사용자를 삭제합니다.
     *
     * @param userId 사용자 ID
     * @return 삭제 결과
     * @throws NewBusinessException 비즈니스 예외
     */
    @Transactional
    public boolean deleteUser(String userId) throws NewBusinessException {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== ASUserManagement.deleteUser START ===");
            logger.info(className, "사용자 삭제 서비스 시작: " + userId);

            // 입력값 검증
            if (!validateUserId(userId)) {
                logger.error(className, "사용자 ID 검증 실패");
                throw new NewBusinessException("사용자 ID 검증 실패");
            }

            // PC 호출하여 사용자 삭제
            boolean result = pcUserManagement.deleteUser(userId);

            logger.info(className, "사용자 삭제 서비스 완료: " + result);
            logger.info(className, "=== ASUserManagement.deleteUser END ===");
            return result;

        } catch (NewBusinessException e) {
            logger.error(className, "사용자 삭제 비즈니스 오류: " + e.getMessage());
            logger.error(className, "=== ASUserManagement.deleteUser ERROR ===");
            throw e;
        } catch (Exception e) {
            logger.error(className, "사용자 삭제 서비스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== ASUserManagement.deleteUser ERROR ===");
            throw new NewBusinessException("사용자 삭제 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 사용자 생성 입력값을 검증합니다.
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
     * 사용자 수정 입력값을 검증합니다.
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

        return true;
    }
} 
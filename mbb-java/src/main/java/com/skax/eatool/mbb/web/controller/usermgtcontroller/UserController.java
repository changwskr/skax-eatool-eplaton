package com.skax.eatool.mbb.web.controller.usermgtcontroller;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.as.usermgtas.ASUserManagement;
import com.skax.eatool.mbb.dc.dto.UserDDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 사용자 관리 컨트롤러
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Tag(name = "User Management", description = "사용자 관리 API")
public class UserController {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private ASUserManagement asUserManagement;
    
    /**
     * 사용자 등록
     * 
     * @param userRequest 사용자 등록 요청
     * @return 등록된 사용자 정보
     */
    @PostMapping("/register")
    @Operation(summary = "사용자 등록", description = "새로운 사용자를 등록합니다.")
    public ResponseEntity<UserDDTO> registerUser(@RequestBody Map<String, Object> userRequest) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== User Registration Request ===");
        
        try {
            // 1. 요청 데이터를 UserDDTO로 변환
            UserDDTO user = new UserDDTO();
            user.setUsername((String) userRequest.get("username"));
            user.setPassword((String) userRequest.get("password"));
            user.setEmail((String) userRequest.get("email"));
            user.setRole((String) userRequest.get("role"));
            
            // 2. AS 호출하여 사용자 등록
            UserDDTO registeredUser = asUserManagement.createUser(user);
            
            logger.info(className, "사용자 등록 성공: " + registeredUser.getUsername());
            return ResponseEntity.ok(registeredUser);
            
        } catch (NewBusinessException e) {
            logger.error(className, "사용자 등록 비즈니스 오류: " + e.getMessage());
            return ResponseEntity.badRequest().build();
            
        } catch (Exception e) {
            logger.error(className, "사용자 등록 오류: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 사용자 로그인
     * 
     * @param loginRequest 로그인 요청
     * @return 인증된 사용자 정보
     */
    @PostMapping("/login")
    @Operation(summary = "사용자 로그인", description = "사용자 인증을 수행합니다.")
    public ResponseEntity<UserDDTO> loginUser(@RequestBody Map<String, Object> loginRequest) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== User Login Request ===");
        
        try {
            String username = (String) loginRequest.get("username");
            String password = (String) loginRequest.get("password");
            
            // 1. AS 호출하여 사용자 인증
            UserDDTO authenticatedUser = asUserManagement.authenticateUser(username, password);
            
            logger.info(className, "사용자 로그인 성공: " + authenticatedUser.getUsername());
            return ResponseEntity.ok(authenticatedUser);
            
        } catch (NewBusinessException e) {
            logger.error(className, "사용자 로그인 비즈니스 오류: " + e.getMessage());
            return ResponseEntity.badRequest().build();
            
        } catch (Exception e) {
            logger.error(className, "사용자 로그인 오류: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 모든 사용자 조회
     * 
     * @return 사용자 목록
     */
    @GetMapping("/list")
    @Operation(summary = "사용자 목록 조회", description = "모든 사용자 목록을 조회합니다.")
    public ResponseEntity<List<UserDDTO>> getAllUsers() {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== Get All Users Request ===");
        
        try {
            // AS 호출하여 사용자 목록 조회
            List<UserDDTO> users = asUserManagement.getAllUsers();
            
            logger.info(className, "사용자 목록 조회 성공: " + users.size() + "명");
            return ResponseEntity.ok(users);
            
        } catch (NewBusinessException e) {
            logger.error(className, "사용자 목록 조회 비즈니스 오류: " + e.getMessage());
            return ResponseEntity.badRequest().build();
            
        } catch (Exception e) {
            logger.error(className, "사용자 목록 조회 오류: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 사용자 ID로 사용자 조회
     * 
     * @param userId 사용자 ID
     * @return 사용자 정보
     */
    @GetMapping("/{userId}")
    @Operation(summary = "사용자 ID 조회", description = "사용자 ID로 사용자 정보를 조회합니다.")
    public ResponseEntity<UserDDTO> getUserById(@PathVariable String userId) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== Get User by ID Request ===");
        
        try {
            // AS 호출하여 사용자 조회
            UserDDTO user = asUserManagement.getUserById(userId);
            
            logger.info(className, "사용자 ID 조회 성공: " + user.getUsername());
            return ResponseEntity.ok(user);
            
        } catch (NewBusinessException e) {
            logger.error(className, "사용자 ID 조회 비즈니스 오류: " + e.getMessage());
            return ResponseEntity.badRequest().build();
            
        } catch (Exception e) {
            logger.error(className, "사용자 ID 조회 오류: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 사용자명으로 사용자 조회
     * 
     * @param username 사용자명
     * @return 사용자 정보
     */
    @GetMapping("/username/{username}")
    @Operation(summary = "사용자명 조회", description = "사용자명으로 사용자 정보를 조회합니다.")
    public ResponseEntity<UserDDTO> getUserByUsername(@PathVariable String username) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== Get User by Username Request ===");
        
        try {
            // AS 호출하여 사용자 조회
            UserDDTO user = asUserManagement.getUserByUsername(username);
            
            logger.info(className, "사용자명 조회 성공: " + user.getUsername());
            return ResponseEntity.ok(user);
            
        } catch (NewBusinessException e) {
            logger.error(className, "사용자명 조회 비즈니스 오류: " + e.getMessage());
            return ResponseEntity.badRequest().build();
            
        } catch (Exception e) {
            logger.error(className, "사용자명 조회 오류: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 사용자 정보 수정
     * 
     * @param userId 사용자 ID
     * @param userRequest 수정할 사용자 정보
     * @return 수정된 사용자 정보
     */
    @PutMapping("/{userId}")
    @Operation(summary = "사용자 정보 수정", description = "사용자 정보를 수정합니다.")
    public ResponseEntity<UserDDTO> updateUser(@PathVariable String userId, @RequestBody Map<String, Object> userRequest) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== Update User Request ===");
        
        try {
            // 1. 요청 데이터를 UserDDTO로 변환
            UserDDTO user = new UserDDTO();
            user.setUsername((String) userRequest.get("username"));
            user.setPassword((String) userRequest.get("password"));
            user.setEmail((String) userRequest.get("email"));
            user.setRole((String) userRequest.get("role"));
            
            // 2. AS 호출하여 사용자 수정
            UserDDTO updatedUser = asUserManagement.updateUser(userId, user);
            
            logger.info(className, "사용자 수정 성공: " + updatedUser.getUsername());
            return ResponseEntity.ok(updatedUser);
            
        } catch (NewBusinessException e) {
            logger.error(className, "사용자 수정 비즈니스 오류: " + e.getMessage());
            return ResponseEntity.badRequest().build();
            
        } catch (Exception e) {
            logger.error(className, "사용자 수정 오류: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 사용자 삭제
     * 
     * @param userId 사용자 ID
     * @return 삭제 결과
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다.")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== Delete User Request ===");
        
        try {
            // AS 호출하여 사용자 삭제
            boolean result = asUserManagement.deleteUser(userId);
            
            if (result) {
                logger.info(className, "사용자 삭제 성공: " + userId);
                return ResponseEntity.ok("사용자가 성공적으로 삭제되었습니다.");
            } else {
                logger.error(className, "사용자 삭제 실패: " + userId);
                return ResponseEntity.badRequest().body("사용자 삭제에 실패했습니다.");
            }
            
        } catch (NewBusinessException e) {
            logger.error(className, "사용자 삭제 비즈니스 오류: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            logger.error(className, "사용자 삭제 오류: " + e.getMessage());
            return ResponseEntity.internalServerError().body("사용자 삭제 중 오류가 발생했습니다.");
        }
    }
} 
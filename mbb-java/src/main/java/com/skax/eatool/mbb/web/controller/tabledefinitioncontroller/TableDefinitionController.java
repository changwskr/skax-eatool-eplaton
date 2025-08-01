package com.skax.eatool.mbb.web.controller.tabledefinitioncontroller;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.as.tablecreationas.ASTableCreation;
import com.skax.eatool.mbb.as.ddlgeneratoras.ASDdlGenerator;
import com.skax.eatool.mbb.dc.dto.TableDefinitionDDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 테이블 정의서 입력 컨트롤러
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Controller
@RequestMapping("/table-definition")
public class TableDefinitionController {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private ASTableCreation asTableCreation;
    
    @Autowired
    private ASDdlGenerator asDdlGenerator;
    
    /**
     * 테이블 정의서 입력 페이지를 표시합니다.
     *
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping
    public String showTableDefinitionPage(Model model) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "테이블 정의서 입력 페이지 요청");
        
        model.addAttribute("title", "테이블 정의서 입력");
        return "table-definition";
    }
    
    /**
     * 테이블 정의 목록을 조회합니다.
     *
     * @return 테이블 정의 목록
     */
    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> getTableDefinitionList() {
        String className = this.getClass().getSimpleName();
        Map<String, Object> response = new HashMap<>();
        
        try {
            logger.info(className, "=== TableDefinitionController.getTableDefinitionList START ===");
            
            // AS 의존성 주입 확인
            if (asDdlGenerator == null) {
                logger.error(className, "AS 의존성 주입 실패: asDdlGenerator is null");
                response.put("success", false);
                response.put("message", "서비스 의존성 주입 오류");
                return response;
            }
            
            logger.info(className, "AS 의존성 주입 확인 완료: " + asDdlGenerator.getClass().getSimpleName());
            
            // AS 호출하여 테이블 정의 목록 조회
            logger.info(className, "AS 호출 시작: " + asDdlGenerator.getClass().getSimpleName() + ".getTableDefinitionList");
            Map<String, Object> result = asDdlGenerator.getTableDefinitionList();
            logger.info(className, "AS 호출 완료, 테이블 정의 목록 조회 성공");
            
            response.put("success", true);
            response.put("data", result.get("data"));
            response.put("message", "테이블 정의 목록을 성공적으로 조회했습니다.");
            
            logger.info(className, "=== TableDefinitionController.getTableDefinitionList END ===");
            
        } catch (Exception e) {
            logger.error(className, "테이블 정의 목록 조회 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== TableDefinitionController.getTableDefinitionList ERROR ===");
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "테이블 정의 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 테이블을 생성합니다.
     *
     * @param tableDefinition 테이블 정의서
     * @return 생성 결과
     */
    @PostMapping("/create-table")
    @ResponseBody
    public Map<String, Object> createTable(@RequestBody TableDefinitionDDTO tableDefinition) {
        String className = this.getClass().getSimpleName();
        Map<String, Object> response = new HashMap<>();
        
        try {
            logger.info(className, "=== TableDefinitionController.createTable START ===");
            logger.info(className, "테이블 생성 요청 수신: " + tableDefinition.getTableName());
            logger.info(className, "요청 데이터: " + tableDefinition.toString());
            
            // AS 의존성 주입 확인
            if (asTableCreation == null) {
                logger.error(className, "AS 의존성 주입 실패: asTableCreation is null");
                response.put("success", false);
                response.put("message", "서비스 의존성 주입 오류");
                return response;
            }
            
            logger.info(className, "AS 의존성 주입 확인 완료: " + asTableCreation.getClass().getSimpleName());
            
            // AS 호출하여 테이블 생성
            logger.info(className, "AS 호출 시작: " + asTableCreation.getClass().getSimpleName() + ".createTable");
            boolean result = asTableCreation.createTable(tableDefinition);
            logger.info(className, "AS 호출 완료, 결과: " + result);
            
            if (result) {
                logger.info(className, "테이블 생성 성공: " + tableDefinition.getTableName());
                response.put("success", true);
                response.put("message", "테이블이 성공적으로 생성되었습니다: " + tableDefinition.getTableName());
            } else {
                logger.error(className, "테이블 생성 실패: " + tableDefinition.getTableName());
                response.put("success", false);
                response.put("message", "테이블 생성에 실패했습니다.");
            }
            
            logger.info(className, "=== TableDefinitionController.createTable END ===");
            
        } catch (Exception e) {
            logger.error(className, "테이블 생성 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== TableDefinitionController.createTable ERROR ===");
            e.printStackTrace(); // 스택 트레이스 출력
            response.put("success", false);
            response.put("message", "테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return response;
    }
} 
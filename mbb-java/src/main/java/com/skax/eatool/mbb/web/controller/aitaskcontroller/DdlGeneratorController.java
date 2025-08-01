package com.skax.eatool.mbb.web.controller.aitaskcontroller;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.as.ddlgeneratoras.ASDdlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * DDL 생성 컨트롤러
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Controller
@RequestMapping("/ddl-generator")
public class DdlGeneratorController {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private ASDdlGenerator asDdlGenerator;
    
    /**
     * DDL 생성 페이지를 표시합니다.
     *
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping
    public String showDdlGeneratorPage(Model model) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "DDL 생성기 페이지 요청");
        
        model.addAttribute("title", "DDL 생성");
        return "ddl-generator";
    }
    
    /**
     * 테이블 정의를 기반으로 DDL을 생성합니다.
     *
     * @param request 테이블 정의 및 설정 정보
     * @return 생성된 DDL
     */
    @PostMapping("/generate")
    @ResponseBody
    public Map<String, Object> generateDDL(@RequestBody Map<String, Object> request) {
        String className = this.getClass().getSimpleName();
        Map<String, Object> response = new HashMap<>();
        
        try {
            logger.info(className, "=== DdlGeneratorController.generateDDL START ===");
            
            String tableName = (String) request.get("tableName");
            Map<String, Object> tableDefinition = (Map<String, Object>) request.get("tableDefinition");
            String databaseType = (String) request.get("databaseType");
            String schemaName = (String) request.get("schemaName");
            
            logger.info(className, "DDL 생성 요청: " + tableName);
            logger.info(className, "데이터베이스 타입: " + databaseType);
            logger.info(className, "스키마명: " + schemaName);
            
            // AS 의존성 주입 확인
            if (asDdlGenerator == null) {
                logger.error(className, "AS 의존성 주입 실패: asDdlGenerator is null");
                response.put("success", false);
                response.put("message", "서비스 의존성 주입 오류");
                return response;
            }
            
            logger.info(className, "AS 의존성 주입 확인 완료: " + asDdlGenerator.getClass().getSimpleName());
            
            // AS 호출하여 DDL 생성
            logger.info(className, "AS 호출 시작: " + asDdlGenerator.getClass().getSimpleName() + ".generateDDL");
            String ddl = asDdlGenerator.generateDDL(tableName, tableDefinition, databaseType, schemaName);
            logger.info(className, "AS 호출 완료, DDL 생성 성공");
            
            response.put("success", true);
            response.put("ddl", ddl);
            response.put("message", "DDL이 성공적으로 생성되었습니다.");
            
            logger.info(className, "=== DdlGeneratorController.generateDDL END ===");
            
        } catch (Exception e) {
            logger.error(className, "DDL 생성 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== DdlGeneratorController.generateDDL ERROR ===");
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "DDL 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return response;
    }
} 
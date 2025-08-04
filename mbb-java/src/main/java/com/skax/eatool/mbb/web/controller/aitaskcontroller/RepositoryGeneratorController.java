package com.skax.eatool.mbb.web.controller.aitaskcontroller;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.as.repositoryas.ASRepositoryGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Repository 생성 Controller
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Controller
@RequestMapping("/repository-generator")
public class RepositoryGeneratorController {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    private final String className = this.getClass().getSimpleName();
    
    @Autowired
    private ASRepositoryGeneration asRepositoryGeneration;
    
    /**
     * Repository 생성 페이지
     */
    @GetMapping
    public String repositoryGeneratorPage(Model model) {
        String methodName = "repositoryGeneratorPage";
        logger.info(className, "[" + methodName + "] Repository 생성 페이지 접속");
        
        model.addAttribute("title", "Repository 자동 생성");
        return "repository-generator";
    }
    
    /**
     * Repository 생성 API
     */
    @PostMapping("/generate")
    @ResponseBody
    public NewKBData generateRepository(@RequestBody Map<String, Object> requestData) {
        String methodName = "generateRepository";
        logger.info(className, "[" + methodName + "] Repository 생성 API 호출");
        
        try {
            // AS 호출
            NewKBData result = asRepositoryGeneration.generateRepository(requestData);
            
            logger.info(className, "[" + methodName + "] Repository 생성 완료");
            return result;
            
        } catch (Exception e) {
            logger.error(className, "[" + methodName + "] Repository 생성 중 오류 발생: " + e.getMessage(), e);
            
            NewKBData errorResponse = new NewKBData();
            errorResponse.setValid(false);
            errorResponse.setErrorMessage("Repository 생성 중 오류 발생: " + e.getMessage());
            
            return errorResponse;
        }
    }
    
    /**
     * 테스트용 엔드포인트
     */
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        logger.info(className, "Repository 생성 테스트 엔드포인트 호출");
        return "Repository Generator Controller is working!";
    }
}

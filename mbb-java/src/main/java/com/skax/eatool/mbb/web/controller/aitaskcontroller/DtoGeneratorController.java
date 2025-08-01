package com.skax.eatool.mbb.web.controller.aitaskcontroller;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.as.dtoas.ASDtoGeneration;
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
 * DTO 생성기 컨트롤러
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Controller
@RequestMapping("/dto-generator")
public class DtoGeneratorController {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private ASDtoGeneration asDtoGeneration;
    
    /**
     * DTO 생성기 페이지를 표시합니다.
     *
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping
    public String showDtoGeneratorPage(Model model) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "DTO 생성기 페이지 요청");
        
        model.addAttribute("title", "DTO 생성기");
        return "dto-generator";
    }
    
    /**
     * DTO 생성 API
     *
     * @param request 요청 데이터
     * @return DTO 생성 결과
     */
    @PostMapping("/generate")
    @ResponseBody
    public ResponseEntity<NewKBData> generateDto(@RequestBody Map<String, Object> request) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "DTO 생성 API 호출");
        
        try {
            // AS 호출
            NewKBData result = asDtoGeneration.generateDto(request);
            
            logger.info(className, "DTO 생성 API 완료");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error(className, "DTO 생성 API 중 오류 발생: " + e.getMessage(), e);
            
            NewKBData errorResponse = new NewKBData();
            errorResponse.setValid(false);
            errorResponse.setErrorMessage("DTO 생성 API 중 오류 발생: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
} 
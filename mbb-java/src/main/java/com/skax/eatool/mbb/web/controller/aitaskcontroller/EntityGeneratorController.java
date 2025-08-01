package com.skax.eatool.mbb.web.controller.aitaskcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.as.entityas.ASEntityGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Entity 생성기 컨트롤러
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Controller
@RequestMapping("/entity-generator")
public class EntityGeneratorController {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private ASEntityGeneration asEntityGeneration;
    
    /**
     * Entity 생성기 페이지를 표시합니다.
     *
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping
    public String showEntityGeneratorPage(Model model) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "Entity 생성기 페이지 요청");
        
        try {
            // AS 서비스를 통해 테이블 목록 조회
            List<String> tables = asEntityGeneration.getAvailableTables();
            
            // 테이블 목록을 JSON 문자열로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String tablesJson = objectMapper.writeValueAsString(tables);
            
            model.addAttribute("availableTables", tablesJson);
            logger.info(className, "테이블 목록 로드 완료: " + tables.size() + "개 테이블");
            
        } catch (Exception e) {
            logger.error(className, "테이블 목록 로드 중 오류 발생: " + e.getMessage(), e);
            model.addAttribute("availableTables", "[]");
        }
        
        model.addAttribute("title", "Entity 생성");
        model.addAttribute("subtitle", "기존 테이블 정보를 기반으로 Entity 클래스 생성");
        
        return "entity/entity-generation";
    }
} 
package com.skax.eatool.mbb.web.controller.aitaskcontroller;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Template 생성기 컨트롤러
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Controller
@RequestMapping("/template-generator")
public class TemplateGeneratorController {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    /**
     * Template 생성기 페이지를 표시합니다.
     *
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping
    public String showTemplateGeneratorPage(Model model) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "Template 생성기 페이지 요청");
        
        model.addAttribute("title", "Template 생성기");
        return "template-generator";
    }
} 
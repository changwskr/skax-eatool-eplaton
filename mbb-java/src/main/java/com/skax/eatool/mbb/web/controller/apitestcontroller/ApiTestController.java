package com.skax.eatool.mbb.web.controller.apitestcontroller;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * API 테스트 컨트롤러
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Controller
@RequestMapping("/api-test")
public class ApiTestController {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    /**
     * API 테스트 페이지를 표시합니다.
     *
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping
    public String showApiTestPage(Model model) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "API 테스트 페이지 요청");
        
        model.addAttribute("title", "API 테스트");
        return "api-test";
    }
} 
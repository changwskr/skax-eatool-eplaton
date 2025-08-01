package com.skax.eatool.mbb.web.controller.homecontroller;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 홈 컨트롤러
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Controller
public class HomeController {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    /**
     * 홈 페이지를 표시합니다.
     *
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping("/")
    public String home(Model model) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "홈 페이지 요청");
        
        model.addAttribute("title", "SKAX EA Tool Eplaton - AI 코딩 워크플로우");
        model.addAttribute("subtitle", "AI 기반 코드 생성 및 테이블 관리 시스템");
        
        return "home";
    }
} 
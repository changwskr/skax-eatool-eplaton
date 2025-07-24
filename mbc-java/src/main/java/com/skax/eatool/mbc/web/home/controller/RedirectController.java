package com.skax.eatool.mbc.web.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 리다이렉트 컨트롤러
 * 기존 경로에서 새로운 경로로 리다이렉트
 * 
 * @author SKAX Project Team
 * @version 1.0
 */
@Controller
@RequestMapping("/web")
public class RedirectController {
    
    private static final Logger logger = LoggerFactory.getLogger(RedirectController.class);

    /**
     * 사용자 관리 페이지 리다이렉트
     */
    @GetMapping("/user-management")
    public String redirectToUserManagement() {
        logger.info("사용자 관리 페이지 리다이렉트: /web/user-management -> /web/admin/user-management");
        return "redirect:/web/admin/user-management";
    }

    /**
     * 보고서 관리 페이지 리다이렉트
     */
    @GetMapping("/report-management")
    public String redirectToReportManagement() {
        logger.info("보고서 관리 페이지 리다이렉트: /web/report-management -> /web/admin/report-management");
        return "redirect:/web/admin/report-management";
    }
} 
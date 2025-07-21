package com.skax.eatool.mbc.web.account.controller;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 계정 관리 화면 컨트롤러
 * 
 * 프로그램명: AccountController.java
 * 설명: 계정 관리 관련 웹 페이지를 제공하는 UI 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 계정 생성 페이지 (ACMBC71001 호출)
 * - 계정 조회 페이지 (ACMBC72001 호출)
 * - 계정 목록 페이지 (ACMBC74001 호출)
 * 
 * @version 1.0
 */
@Controller
@RequestMapping("/mbc/as")
public class AccountController {

    private final NewIKesaLogger logger = NewKesaLogHelper.getBiz();

    /**
     * 계정 생성 페이지 표시 (ACMBC71001 호출용)
     * 
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping("/account/create")
    public String showAccountCreatePage(Model model) {
        logger.info("계정 생성 페이지 요청", "AccountController");

        // ACMBC71001 API 정보 설정
        model.addAttribute("pageTitle", "계정 생성");
        model.addAttribute("apiEndpoint", "/mbc/account/create");
        model.addAttribute("apiMethod", "POST");
        model.addAttribute("controllerName", "ACMBC71001");

        return "web/account/create";
    }

    /**
     * 계정 조회 페이지 표시 (ACMBC72001 호출용)
     * 
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping("/account/read")
    public String showAccountReadPage(Model model) {
        logger.info("계정 조회 페이지 요청", "AccountController");

        // ACMBC72001 API 정보 설정
        model.addAttribute("pageTitle", "계정 조회");
        model.addAttribute("apiEndpoint", "/mbc/account/read");
        model.addAttribute("apiMethod", "GET");
        model.addAttribute("controllerName", "ACMBC72001");

        return "web/account/read";
    }

    /**
     * 계정 목록 페이지 표시 (ACMBC74001 호출용)
     * 
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping("/account/list")
    public String showAccountListPage(Model model) {
        logger.info("계정 목록 페이지 요청", "AccountController");

        // ACMBC74001 API 정보 설정
        model.addAttribute("pageTitle", "계정 목록");
        model.addAttribute("apiEndpoint", "/mbc/account/list");
        model.addAttribute("apiMethod", "GET");
        model.addAttribute("controllerName", "ACMBC74001");

        return "web/account/list";
    }

    /**
     * 계정 관리 메인 페이지
     * 
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping("/account/manage")
    public String showAccountManagePage(Model model) {
        logger.info("계정 관리 메인 페이지 요청", "AccountController");

        model.addAttribute("pageTitle", "계정 관리 시스템");

        return "web/account/manage";
    }
}
package com.skax.eatool.mba.web.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * MBA 인증 컨트롤러
 * 
 * 로그인, 로그아웃 등 인증 관련 페이지를 처리합니다.
 * 
 * @author skax.eatool
 * @version 1.0.0
 * @since 2024
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    /**
     * 로그인 페이지 표시
     */
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
} 
package com.skax.eatool.mba.web.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * MBA 홈 컨트롤러
 * 
 * 홈 페이지와 관련된 요청을 처리합니다.
 * 
 * @author KBSTAR
 * @version 1.0.0
 * @since 2024
 */
@Controller
@Tag(name = "Home", description = "홈 페이지 관련 API")
public class HomeController {

    /**
     * 홈 페이지 표시
     */
    @GetMapping({"/", "/home"})
    @Operation(summary = "홈 페이지", description = "MBA 애플리케이션 홈 페이지를 표시합니다.")
    public String home(Model model) {
        model.addAttribute("title", "MBA - Master Business Application");
        model.addAttribute("currentTime", LocalDateTime.now());
        model.addAttribute("version", "1.0.0");
        return "home/index";
    }

    /**
     * 사용자 관리 페이지
     */
    @GetMapping("/user-management")
    @Operation(summary = "사용자 관리", description = "사용자 관리 페이지를 표시합니다.")
    public String userManagement(Model model) {
        model.addAttribute("title", "사용자 관리 - MBA");
        model.addAttribute("currentTime", LocalDateTime.now());
        return "user/user-management";
    }

    /**
     * 계정 관리 페이지
     */
    @GetMapping("/account-management")
    @Operation(summary = "계정 관리", description = "계정 관리 페이지를 표시합니다.")
    public String accountManagement(Model model) {
        model.addAttribute("title", "계정 관리 - MBA");
        model.addAttribute("currentTime", LocalDateTime.now());
        return "account/account-management";
    }

    /**
     * 리포트 관리 페이지
     */
    @GetMapping("/report-management")
    @Operation(summary = "리포트 관리", description = "리포트 관리 페이지를 표시합니다.")
    public String reportManagement(Model model) {
        model.addAttribute("title", "리포트 관리 - MBA");
        model.addAttribute("currentTime", LocalDateTime.now());
        return "report/report-management";
    }

    /**
     * 시스템 모니터링 페이지
     */
    @GetMapping("/monitoring")
    @Operation(summary = "시스템 모니터링", description = "시스템 모니터링 페이지를 표시합니다.")
    public String monitoring(Model model) {
        model.addAttribute("title", "시스템 모니터링 - MBA");
        model.addAttribute("currentTime", LocalDateTime.now());
        return "monitoring/dashboard";
    }

    /**
     * 홈 API 엔드포인트
     */
    @GetMapping("/api")
    @ResponseBody
    @Operation(summary = "홈 API", description = "홈 페이지 정보를 JSON 형태로 반환합니다.")
    public Map<String, Object> homeApi() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "MBA - Master Business Application");
        response.put("version", "1.0.0");
        response.put("status", "running");
        response.put("timestamp", LocalDateTime.now());
        response.put("description", "마스터 비즈니스 애플리케이션");
        return response;
    }

    /**
     * 상태 확인 API
     */
    @GetMapping("/status")
    @ResponseBody
    @Operation(summary = "상태 확인", description = "애플리케이션 상태를 확인합니다.")
    public Map<String, Object> status() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("application", "MBA");
        status.put("timestamp", LocalDateTime.now());
        status.put("uptime", System.currentTimeMillis());
        return status;
    }
} 
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
@RequestMapping("/mba/home")
@Tag(name = "Home", description = "홈 페이지 관련 API")
public class HomeController {

    /**
     * 홈 페이지 표시
     */
    @GetMapping("")
    @Operation(summary = "홈 페이지", description = "MBA 애플리케이션 홈 페이지를 표시합니다.")
    public String home(Model model) {
        model.addAttribute("title", "MBA - Master Business Application");
        model.addAttribute("currentTime", LocalDateTime.now());
        model.addAttribute("version", "1.0.0");
        return "home/index";
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
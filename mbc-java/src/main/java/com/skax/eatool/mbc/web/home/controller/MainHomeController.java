package com.skax.eatool.mbc.web.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

/**
 * MBC 메인 홈 컨트롤러
 * 
 * @author SKAX Project Team
 * @version 1.0
 */
@Controller
public class MainHomeController {
    
    private static final Logger logger = LoggerFactory.getLogger(MainHomeController.class);

    /**
     * 홈화면 표시 - home.html만 사용
     */
    @GetMapping({"/home", ""})
    public String home(Model model) {
        logger.info("=== MainHomeController.home START ===");
        
        // 기본 정보
        model.addAttribute("title", "MBC - Master Business Component");
        model.addAttribute("currentTime", LocalDateTime.now());
        model.addAttribute("version", "1.0.0");
        model.addAttribute("description", "마스터 비즈니스 컴포넌트");
        
        // 시스템 정보
        Map<String, Object> systemInfo = new HashMap<>();
        systemInfo.put("systemName", "SKAX-EATOOL");
        systemInfo.put("version", "2.0.0");
        systemInfo.put("environment", "개발환경");
        systemInfo.put("databaseStatus", "정상");
        model.addAttribute("systemInfo", systemInfo);
        
        // 비즈니스 통계 (실시간 차트용)
        Map<String, Object> businessStats = new HashMap<>();
        
        // 계정 통계
        Map<String, Object> accountStats = new HashMap<>();
        accountStats.put("totalAccounts", 150);
        accountStats.put("activeAccounts", 120);
        accountStats.put("inactiveAccounts", 25);
        accountStats.put("pendingAccounts", 5);
        accountStats.put("lastUpdate", LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        businessStats.put("accountStats", accountStats);
        
        // 사용자 통계
        Map<String, Object> userStats = new HashMap<>();
        userStats.put("totalUsers", 85);
        userStats.put("activeUsers", 75);
        userStats.put("inactiveUsers", 10);
        businessStats.put("userStats", userStats);
        
        // 시스템 통계
        Map<String, Object> systemStats = new HashMap<>();
        systemStats.put("activeSessions", 12);
        systemStats.put("cpuUsage", "45%");
        systemStats.put("memoryUsage", "62%");
        systemStats.put("diskUsage", "78%");
        businessStats.put("systemStats", systemStats);
        
        model.addAttribute("businessStats", businessStats);
        
        // 네비게이션 메뉴
        List<Map<String, Object>> navigationMenus = new ArrayList<>();
        
        // 계정 관리 메뉴
        Map<String, Object> accountMenu = new HashMap<>();
        accountMenu.put("title", "계정 관리");
        accountMenu.put("description", "계정 생성, 조회, 수정, 삭제");
        accountMenu.put("color", "#667eea");
        accountMenu.put("icon", "fas fa-user-circle");
        
        List<Map<String, Object>> accountSubMenus = new ArrayList<>();
        Map<String, Object> createAccount = new HashMap<>();
        createAccount.put("title", "계정 생성");
        createAccount.put("url", "/mbc/web/admin/account/create");
        createAccount.put("method", "GET");
        createAccount.put("icon", "fas fa-plus");
        accountSubMenus.add(createAccount);
        
        Map<String, Object> listAccount = new HashMap<>();
        listAccount.put("title", "계정 목록");
        listAccount.put("url", "/mbc/web/admin/account-management");
        listAccount.put("method", "GET");
        listAccount.put("icon", "fas fa-list");
        accountSubMenus.add(listAccount);
        
        accountMenu.put("subMenus", accountSubMenus);
        navigationMenus.add(accountMenu);
        
        // 사용자 관리 메뉴
        Map<String, Object> userMenu = new HashMap<>();
        userMenu.put("title", "사용자 관리");
        userMenu.put("description", "사용자 생성, 조회, 수정, 삭제");
        userMenu.put("color", "#764ba2");
        userMenu.put("icon", "fas fa-users");
        
        List<Map<String, Object>> userSubMenus = new ArrayList<>();
        Map<String, Object> createUser = new HashMap<>();
        createUser.put("title", "사용자 생성");
        createUser.put("url", "/mbc/web/admin/user/create");
        createUser.put("method", "GET");
        createUser.put("icon", "fas fa-user-plus");
        userSubMenus.add(createUser);
        
        Map<String, Object> listUser = new HashMap<>();
        listUser.put("title", "사용자 목록");
        listUser.put("url", "/mbc/web/admin/user-management");
        listUser.put("method", "GET");
        listUser.put("icon", "fas fa-list");
        userSubMenus.add(listUser);
        
        userMenu.put("subMenus", userSubMenus);
        navigationMenus.add(userMenu);
        
        // 시스템 관리 메뉴
        Map<String, Object> systemMenu = new HashMap<>();
        systemMenu.put("title", "시스템 관리");
        systemMenu.put("description", "시스템 설정 및 모니터링");
        systemMenu.put("color", "#f093fb");
        systemMenu.put("icon", "fas fa-cogs");
        
        List<Map<String, Object>> systemSubMenus = new ArrayList<>();
        Map<String, Object> healthCheck = new HashMap<>();
        healthCheck.put("title", "상태 확인");
        healthCheck.put("url", "/mbc/monitoring/health");
        healthCheck.put("method", "GET");
        healthCheck.put("icon", "fas fa-heartbeat");
        systemSubMenus.add(healthCheck);
        
        Map<String, Object> swagger = new HashMap<>();
        swagger.put("title", "API 문서");
        swagger.put("url", "/mbc/swagger-ui/index.html");
        swagger.put("method", "GET");
        swagger.put("icon", "fas fa-book");
        systemSubMenus.add(swagger);
        
        Map<String, Object> apiDocs = new HashMap<>();
        apiDocs.put("title", "API 스펙");
        apiDocs.put("url", "/mbc/v3/api-docs");
        apiDocs.put("method", "GET");
        apiDocs.put("icon", "fas fa-file-code");
        systemSubMenus.add(apiDocs);
        
        Map<String, Object> swaggerTest = new HashMap<>();
        swaggerTest.put("title", "API 테스트");
        swaggerTest.put("url", "/mbc/swagger/test");
        swaggerTest.put("method", "GET");
        swaggerTest.put("icon", "fas fa-vial");
        systemSubMenus.add(swaggerTest);
        
        systemMenu.put("subMenus", systemSubMenus);
        navigationMenus.add(systemMenu);
        
        // 보고서 메뉴
        Map<String, Object> reportMenu = new HashMap<>();
        reportMenu.put("title", "보고서");
        reportMenu.put("description", "다양한 보고서 및 통계");
        reportMenu.put("color", "#4facfe");
        reportMenu.put("icon", "fas fa-chart-bar");
        
        List<Map<String, Object>> reportSubMenus = new ArrayList<>();
        Map<String, Object> reportManagement = new HashMap<>();
        reportManagement.put("title", "보고서 관리");
        reportManagement.put("url", "/mbc/web/admin/report-management");
        reportManagement.put("method", "GET");
        reportManagement.put("icon", "fas fa-chart-line");
        reportSubMenus.add(reportManagement);
        
        Map<String, Object> userStatistics = new HashMap<>();
        userStatistics.put("title", "사용자 통계");
        userStatistics.put("url", "/mbc/web/admin/user-statistics");
        userStatistics.put("method", "GET");
        userStatistics.put("icon", "fas fa-chart-pie");
        reportSubMenus.add(userStatistics);
        
        reportMenu.put("subMenus", reportSubMenus);
        navigationMenus.add(reportMenu);
        
        model.addAttribute("navigationMenus", navigationMenus);
        
        // 최근 활동
        List<Map<String, Object>> recentActivities = new ArrayList<>();
        
        Map<String, Object> activity1 = new HashMap<>();
        activity1.put("type", "USER");
        activity1.put("name", "관리자 계정 생성");
        activity1.put("date", LocalDateTime.now().minusHours(2).toString());
        recentActivities.add(activity1);
        
        Map<String, Object> activity2 = new HashMap<>();
        activity2.put("type", "ACCOUNT");
        activity2.put("name", "테스트 계정 생성");
        activity2.put("date", LocalDateTime.now().minusHours(4).toString());
        recentActivities.add(activity2);
        
        Map<String, Object> activity3 = new HashMap<>();
        activity3.put("type", "SYSTEM");
        activity3.put("name", "시스템 업데이트");
        activity3.put("date", LocalDateTime.now().minusHours(6).toString());
        recentActivities.add(activity3);
        
        model.addAttribute("recentActivities", recentActivities);
        
        logger.info("=== MainHomeController.home END ===");
        
        return "home";
    }

    /**
     * 루트 경로 API 엔드포인트
     */
    @GetMapping("/api")
    @ResponseBody
    public Map<String, Object> homeApi() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "MBC - Master Business Component");
        response.put("version", "1.0.0");
        response.put("status", "running");
        response.put("timestamp", LocalDateTime.now());
        response.put("description", "마스터 비즈니스 컴포넌트");
        return response;
    }

    /**
     * 상태 확인 API
     */
    @GetMapping("/status")
    @ResponseBody
    public Map<String, Object> status() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("application", "MBC");
        status.put("timestamp", LocalDateTime.now());
        status.put("uptime", System.currentTimeMillis());
        return status;
    }

    /**
     * 실시간 계정 통계 API
     */
    @GetMapping("/api/report/account/statistics")
    @ResponseBody
    public Map<String, Object> getAccountStatistics() {
        logger.info("=== MainHomeController.getAccountStatistics START ===");
        
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        
        try {
            // 계정 통계 데이터
            Map<String, Object> summaryData = new HashMap<>();
            summaryData.put("totalCount", 150);
            summaryData.put("activeCount", 120);
            summaryData.put("inactiveCount", 25);
            summaryData.put("pendingCount", 5);
            
            // 계정 타입별 통계
            List<Map<String, Object>> typeStatistics = new ArrayList<>();
            
            Map<String, Object> type1 = new HashMap<>();
            type1.put("account_type", "일반 계정");
            type1.put("count", 80);
            typeStatistics.add(type1);
            
            Map<String, Object> type2 = new HashMap<>();
            type2.put("account_type", "관리자 계정");
            type2.put("count", 45);
            typeStatistics.add(type2);
            
            Map<String, Object> type3 = new HashMap<>();
            type3.put("account_type", "시스템 계정");
            type3.put("count", 25);
            typeStatistics.add(type3);
            
            summaryData.put("typeStatistics", typeStatistics);
            data.put("summaryData", summaryData);
            
            response.put("success", true);
            response.put("message", "계정 통계 조회 성공");
            response.put("data", data);
            
        } catch (Exception e) {
            logger.error("계정 통계 조회 중 오류: " + e.getMessage(), e);
            response.put("success", false);
            response.put("message", "계정 통계 조회 실패: " + e.getMessage());
        }
        
        logger.info("=== MainHomeController.getAccountStatistics END ===");
        return response;
    }

    /**
     * 실시간 시스템 통계 API
     */
    @GetMapping("/api/system/statistics")
    @ResponseBody
    public Map<String, Object> getSystemStatistics() {
        logger.info("=== MainHomeController.getSystemStatistics START ===");
        
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        
        try {
            // 시스템 리소스 정보
            Map<String, Object> systemInfo = new HashMap<>();
            systemInfo.put("cpuUsage", Math.random() * 80 + 20); // 20-100%
            systemInfo.put("memoryUsage", Math.random() * 60 + 40); // 40-100%
            systemInfo.put("diskUsage", Math.random() * 50 + 50); // 50-100%
            systemInfo.put("networkStatus", "정상");
            systemInfo.put("databaseStatus", "정상");
            systemInfo.put("applicationStatus", "정상");
            
            data.put("systemInfo", systemInfo);
            response.put("success", true);
            response.put("message", "시스템 통계 조회 성공");
            response.put("data", data);
            
        } catch (Exception e) {
            logger.error("시스템 통계 조회 중 오류: " + e.getMessage(), e);
            response.put("success", false);
            response.put("message", "시스템 통계 조회 실패: " + e.getMessage());
        }
        
        logger.info("=== MainHomeController.getSystemStatistics END ===");
        return response;
    }
}
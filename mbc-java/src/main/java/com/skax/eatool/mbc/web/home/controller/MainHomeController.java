package com.skax.eatool.mbc.web.home.controller;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 메인 홈페이지 컨트롤러
 * 
 * 프로그램명: MainHomeController.java
 * 설명: /mbc/home과 /mbc/ 경로로 접근하는 메인 홈페이지를 제공하는 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - /mbc/home 경로 처리
 * - /mbc/ 경로 처리
 * - SKAX-EATOOL 홈화면 리다이렉트
 * 
 * @version 1.0
 */
@Controller
@RequestMapping("/mbc")
public class MainHomeController {

    private final NewIKesaLogger logger = NewKesaLogHelper.getBiz();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * /mbc/home 경로 처리
     * 
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping("/home")
    public String showMainHome(Model model) {
        logger.info("메인 홈페이지 요청 (/home)", "MainHomeController");
        return showSkaxHome(model);
    }

    /**
     * /mbc/ 경로 처리
     * 
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping("/")
    public String showRootHome(Model model) {
        logger.info("루트 홈페이지 요청 (/)", "MainHomeController");
        return showSkaxHome(model);
    }

    /**
     * SKAX-EATOOL 홈화면 표시
     * 
     * @param model 모델
     * @return 뷰 이름
     */
    private String showSkaxHome(Model model) {
        logger.info("SKAX-EATOOL 홈화면 요청", "MainHomeController");

        try {
            // 현재 시간
            String currentTime = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
            model.addAttribute("currentTime", currentTime);

            // 시스템 정보
            Map<String, Object> systemInfo = getSystemInfo();
            model.addAttribute("systemInfo", systemInfo);

            // 업무별 통계
            Map<String, Object> businessStats = getBusinessStats();
            model.addAttribute("businessStats", businessStats);

            // 최근 활동
            List<Map<String, Object>> recentActivities = getRecentActivities();
            model.addAttribute("recentActivities", recentActivities);

            // 업무별 네비게이션 메뉴
            List<Map<String, Object>> navigationMenus = getNavigationMenus();
            model.addAttribute("navigationMenus", navigationMenus);

            logger.info("SKAX-EATOOL 홈화면 데이터 로드 완료", "MainHomeController");

        } catch (Exception e) {
            logger.error("SKAX-EATOOL 홈화면 데이터 로드 중 오류: " + e.getMessage(), "MainHomeController");
            // 기본값 설정
            setDefaultValues(model);
        }

        return "web/home";
    }

    /**
     * 시스템 정보 조회
     */
    private Map<String, Object> getSystemInfo() {
        Map<String, Object> systemInfo = new HashMap<>();

        try {
            systemInfo.put("systemName", "SKAX-EATOOL");
            systemInfo.put("version", "2.0.0");
            systemInfo.put("environment", "개발환경");
            systemInfo.put("databaseStatus", "정상");
            systemInfo.put("lastUpdate", LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            // 데이터베이스 연결 상태 확인
            try {
                jdbcTemplate.queryForObject("SELECT 1", Integer.class);
                systemInfo.put("databaseStatus", "정상");
            } catch (Exception e) {
                systemInfo.put("databaseStatus", "연결 오류");
            }

        } catch (Exception e) {
            logger.warn("시스템 정보 조회 실패: " + e.getMessage(), "MainHomeController");
            systemInfo.put("systemName", "SKAX-EATOOL");
            systemInfo.put("version", "2.0.0");
            systemInfo.put("environment", "개발환경");
            systemInfo.put("databaseStatus", "확인 불가");
        }

        return systemInfo;
    }

    /**
     * 업무별 통계 조회
     */
    private Map<String, Object> getBusinessStats() {
        Map<String, Object> stats = new HashMap<>();

        try {
            // 계정 관련 통계
            int totalAccounts = 0;
            int totalUsers = 0;
            int totalTransactions = 0;

            try {
                totalAccounts = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ACCOUNT", Integer.class);
            } catch (Exception e) {
                logger.warn("계정 통계 조회 실패: " + e.getMessage(), "MainHomeController");
            }

            try {
                totalUsers = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USER_INFO", Integer.class);
            } catch (Exception e) {
                logger.warn("사용자 통계 조회 실패: " + e.getMessage(), "MainHomeController");
            }

            try {
                totalTransactions = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TRANSACTION", Integer.class);
            } catch (Exception e) {
                logger.warn("거래 통계 조회 실패: " + e.getMessage(), "MainHomeController");
            }

            stats.put("totalAccounts", totalAccounts);
            stats.put("totalUsers", totalUsers);
            stats.put("totalTransactions", totalTransactions);
            stats.put("activeSessions", 5); // 임시 값

        } catch (Exception e) {
            logger.warn("업무 통계 조회 실패: " + e.getMessage(), "MainHomeController");
            stats.put("totalAccounts", 0);
            stats.put("totalUsers", 0);
            stats.put("totalTransactions", 0);
            stats.put("activeSessions", 0);
        }

        return stats;
    }

    /**
     * 최근 활동 조회
     */
    private List<Map<String, Object>> getRecentActivities() {
        List<Map<String, Object>> activities = new ArrayList<>();

        try {
            // 실제 데이터베이스에서 조회 시도
            String sql = "SELECT USER_NAME, ROLE, STATUS, CREATED_DATE FROM USER_INFO ORDER BY CREATED_DATE DESC LIMIT 5";
            List<Map<String, Object>> dbActivities = jdbcTemplate.queryForList(sql);

            // 데이터베이스 결과에 type 속성 추가
            for (Map<String, Object> activity : dbActivities) {
                activity.put("type", "USER"); // 사용자 활동으로 설정
                activity.put("name", activity.get("USER_NAME")); // name 속성 추가
                activity.put("date", activity.get("CREATED_DATE")); // date 속성 추가
            }

            activities = dbActivities;

            // 데이터가 없으면 기본 데이터 제공
            if (activities.isEmpty()) {
                activities = getDefaultActivities();
            }

        } catch (Exception e) {
            logger.warn("최근 활동 조회 실패: " + e.getMessage(), "MainHomeController");
            activities = getDefaultActivities();
        }

        return activities;
    }

    /**
     * 기본 활동 데이터
     */
    private List<Map<String, Object>> getDefaultActivities() {
        List<Map<String, Object>> activities = new ArrayList<>();

        Map<String, Object> activity1 = new HashMap<>();
        activity1.put("type", "USER");
        activity1.put("name", "관리자 계정 생성");
        activity1.put("date", "2024-01-15 14:30:00");
        activities.add(activity1);

        Map<String, Object> activity2 = new HashMap<>();
        activity2.put("type", "ACCOUNT");
        activity2.put("name", "테스트 계정 생성");
        activity2.put("date", "2024-01-15 13:45:00");
        activities.add(activity2);

        Map<String, Object> activity3 = new HashMap<>();
        activity3.put("type", "SYSTEM");
        activity3.put("name", "시스템 업데이트");
        activity3.put("date", "2024-01-15 12:00:00");
        activities.add(activity3);

        return activities;
    }

    /**
     * 네비게이션 메뉴 구성
     */
    private List<Map<String, Object>> getNavigationMenus() {
        List<Map<String, Object>> menus = new ArrayList<>();

        // 계정 관리 메뉴
        Map<String, Object> accountMenu = new HashMap<>();
        accountMenu.put("id", "account");
        accountMenu.put("title", "계정 관리");
        accountMenu.put("icon", "fas fa-user-circle");
        accountMenu.put("color", "#667eea");
        accountMenu.put("description", "계정 생성, 조회, 수정, 삭제");

        List<Map<String, Object>> accountSubMenus = new ArrayList<>();

        Map<String, Object> createAccount = new HashMap<>();
        createAccount.put("title", "계정 생성");
        createAccount.put("url", "/mbc/account/create");
        createAccount.put("icon", "fas fa-plus-circle");
        accountSubMenus.add(createAccount);

        Map<String, Object> listAccount = new HashMap<>();
        listAccount.put("title", "계정 목록");
        listAccount.put("url", "/mbc/account/list");
        listAccount.put("icon", "fas fa-list");
        accountSubMenus.add(listAccount);

        Map<String, Object> readAccount = new HashMap<>();
        readAccount.put("title", "계정 조회");
        readAccount.put("url", "/mbc/account/read");
        readAccount.put("icon", "fas fa-search");
        accountSubMenus.add(readAccount);

        accountMenu.put("subMenus", accountSubMenus);
        menus.add(accountMenu);

        // 사용자 관리 메뉴
        Map<String, Object> userMenu = new HashMap<>();
        userMenu.put("id", "user");
        userMenu.put("title", "사용자 관리");
        userMenu.put("icon", "fas fa-users");
        userMenu.put("color", "#764ba2");
        userMenu.put("description", "사용자 등록, 권한 관리");

        List<Map<String, Object>> userSubMenus = new ArrayList<>();

        Map<String, Object> createUser = new HashMap<>();
        createUser.put("title", "사용자 등록");
        createUser.put("url", "/mbc/user/create");
        createUser.put("icon", "fas fa-user-plus");
        userSubMenus.add(createUser);

        Map<String, Object> listUser = new HashMap<>();
        listUser.put("title", "사용자 목록");
        listUser.put("url", "/mbc/user/list");
        listUser.put("icon", "fas fa-list");
        userSubMenus.add(listUser);

        userMenu.put("subMenus", userSubMenus);
        menus.add(userMenu);

        // 시스템 관리 메뉴
        Map<String, Object> systemMenu = new HashMap<>();
        systemMenu.put("id", "system");
        systemMenu.put("title", "시스템 관리");
        systemMenu.put("icon", "fas fa-cogs");
        systemMenu.put("color", "#f093fb");
        systemMenu.put("description", "시스템 설정, 모니터링");

        List<Map<String, Object>> systemSubMenus = new ArrayList<>();

        Map<String, Object> database = new HashMap<>();
        database.put("title", "데이터베이스");
        database.put("url", "/mbc/h2-console");
        database.put("icon", "fas fa-database");
        systemSubMenus.add(database);

        Map<String, Object> monitoring = new HashMap<>();
        monitoring.put("title", "시스템 모니터링");
        monitoring.put("url", "/mbc/monitoring");
        monitoring.put("icon", "fas fa-chart-line");
        systemSubMenus.add(monitoring);

        systemMenu.put("subMenus", systemSubMenus);
        menus.add(systemMenu);

        // 보고서 메뉴
        Map<String, Object> reportMenu = new HashMap<>();
        reportMenu.put("id", "report");
        reportMenu.put("title", "보고서");
        reportMenu.put("icon", "fas fa-chart-bar");
        reportMenu.put("color", "#4facfe");
        reportMenu.put("description", "통계 보고서, 분석");

        List<Map<String, Object>> reportSubMenus = new ArrayList<>();

        Map<String, Object> accountReport = new HashMap<>();
        accountReport.put("title", "계정 통계");
        accountReport.put("url", "/mbc/report/account");
        accountReport.put("icon", "fas fa-chart-pie");
        reportSubMenus.add(accountReport);

        Map<String, Object> userReport = new HashMap<>();
        userReport.put("title", "사용자 통계");
        userReport.put("url", "/mbc/report/user");
        userReport.put("icon", "fas fa-chart-area");
        reportSubMenus.add(userReport);

        reportMenu.put("subMenus", reportSubMenus);
        menus.add(reportMenu);

        return menus;
    }

    /**
     * 기본값 설정
     */
    private void setDefaultValues(Model model) {
        model.addAttribute("currentTime", LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss")));

        Map<String, Object> systemInfo = new HashMap<>();
        systemInfo.put("systemName", "SKAX-EATOOL");
        systemInfo.put("version", "2.0.0");
        systemInfo.put("environment", "개발환경");
        systemInfo.put("databaseStatus", "확인 불가");
        model.addAttribute("systemInfo", systemInfo);

        Map<String, Object> businessStats = new HashMap<>();
        businessStats.put("totalAccounts", 0);
        businessStats.put("totalUsers", 0);
        businessStats.put("totalTransactions", 0);
        businessStats.put("activeSessions", 0);
        model.addAttribute("businessStats", businessStats);

        model.addAttribute("recentActivities", getDefaultActivities());
        model.addAttribute("navigationMenus", getNavigationMenus());
    }
}
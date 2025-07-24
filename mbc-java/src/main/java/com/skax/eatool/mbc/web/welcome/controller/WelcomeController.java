package com.skax.eatool.mbc.web.welcome.controller;

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
 * Welcome 화면 컨트롤러
 * 
 * 프로그램명: WelcomeController.java
 * 설명: 시스템 Welcome 페이지를 제공하는 UI 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 시스템 Welcome 페이지 표시
 * - 시스템 정보 및 통계 제공
 * - 최근 활동 내역 표시
 * 
 * @version 1.0
 */
@Controller
@RequestMapping("/as")
public class WelcomeController {

    private final NewIKesaLogger logger = NewKesaLogHelper.getBiz();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Welcome 페이지 표시
     * 
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping("/welcome")
    public String showWelcomePage(Model model) {
        logger.info("Welcome 페이지 요청", "WelcomeController");

        try {
            // 현재 시간
            String currentTime = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
            model.addAttribute("currentTime", currentTime);

            // 시스템 정보
            Map<String, Object> systemInfo = getSystemInfo();
            model.addAttribute("systemInfo", systemInfo);

            // 사용자 통계
            Map<String, Object> userStats = getUserStats();
            model.addAttribute("userStats", userStats);

            // 계정 통계
            Map<String, Object> accountStats = getAccountStats();
            model.addAttribute("accountStats", accountStats);

            // 최근 활동
            List<Map<String, Object>> recentActivities = getRecentActivities();
            model.addAttribute("recentActivities", recentActivities);

            logger.info("Welcome 페이지 데이터 로드 완료", "WelcomeController");

        } catch (Exception e) {
            logger.error("Welcome 페이지 데이터 로드 중 오류: " + e.getMessage(), "WelcomeController");
            setDefaultValues(model);
        }

        return "web/welcome";
    }

    /**
     * 시스템 정보 조회
     */
    private Map<String, Object> getSystemInfo() {
        Map<String, Object> systemInfo = new HashMap<>();

        try {
            systemInfo.put("applicationName", "MBC 시스템");
            systemInfo.put("version", "1.0.0");
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
            logger.warn("시스템 정보 조회 실패: " + e.getMessage(), "WelcomeController");
            systemInfo.put("applicationName", "MBC 시스템");
            systemInfo.put("version", "1.0.0");
            systemInfo.put("environment", "개발환경");
            systemInfo.put("databaseStatus", "확인 불가");
        }

        return systemInfo;
    }

    /**
     * 사용자 통계 조회
     */
    private Map<String, Object> getUserStats() {
        Map<String, Object> userStats = new HashMap<>();

        try {
            int totalUsers = 0;
            int activeUsers = 0;
            int adminUsers = 0;

            try {
                totalUsers = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USER_INFO", Integer.class);
            } catch (Exception e) {
                logger.warn("전체 사용자 수 조회 실패: " + e.getMessage(), "WelcomeController");
            }

            try {
                activeUsers = jdbcTemplate.queryForObject(
                        "SELECT COUNT(*) FROM USER_INFO WHERE STATUS = 'ACTIVE'", Integer.class);
            } catch (Exception e) {
                logger.warn("활성 사용자 수 조회 실패: " + e.getMessage(), "WelcomeController");
            }

            try {
                adminUsers = jdbcTemplate.queryForObject(
                        "SELECT COUNT(*) FROM USER_INFO WHERE ROLE = 'ADMIN'", Integer.class);
            } catch (Exception e) {
                logger.warn("관리자 수 조회 실패: " + e.getMessage(), "WelcomeController");
            }

            userStats.put("totalUsers", totalUsers);
            userStats.put("activeUsers", activeUsers);
            userStats.put("adminUsers", adminUsers);

        } catch (Exception e) {
            logger.warn("사용자 통계 조회 실패: " + e.getMessage(), "WelcomeController");
            userStats.put("totalUsers", 0);
            userStats.put("activeUsers", 0);
            userStats.put("adminUsers", 0);
        }

        return userStats;
    }

    /**
     * 계정 통계 조회
     */
    private Map<String, Object> getAccountStats() {
        Map<String, Object> accountStats = new HashMap<>();

        try {
            int totalAccounts = 0;
            int totalTransactions = 0;

            try {
                totalAccounts = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ACCOUNT", Integer.class);
            } catch (Exception e) {
                logger.warn("전체 계정 수 조회 실패: " + e.getMessage(), "WelcomeController");
            }

            try {
                totalTransactions = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TRANSACTION", Integer.class);
            } catch (Exception e) {
                logger.warn("전체 거래 수 조회 실패: " + e.getMessage(), "WelcomeController");
            }

            accountStats.put("totalAccounts", totalAccounts);
            accountStats.put("totalTransactions", totalTransactions);

        } catch (Exception e) {
            logger.warn("계정 통계 조회 실패: " + e.getMessage(), "WelcomeController");
            accountStats.put("totalAccounts", 0);
            accountStats.put("totalTransactions", 0);
        }

        return accountStats;
    }

    /**
     * 최근 활동 조회
     */
    private List<Map<String, Object>> getRecentActivities() {
        List<Map<String, Object>> activities = new ArrayList<>();

        try {
            // 실제 데이터베이스에서 조회 시도
            String sql = "SELECT USER_NAME, ROLE, STATUS, CREATED_DATE FROM USER_INFO ORDER BY CREATED_DATE DESC LIMIT 5";
            activities = jdbcTemplate.queryForList(sql);

            // 데이터가 없으면 기본 데이터 제공
            if (activities.isEmpty()) {
                activities = getDefaultActivities();
            }

        } catch (Exception e) {
            logger.warn("최근 활동 조회 실패: " + e.getMessage(), "WelcomeController");
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
     * 기본값 설정
     */
    private void setDefaultValues(Model model) {
        model.addAttribute("currentTime", LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss")));

        Map<String, Object> systemInfo = new HashMap<>();
        systemInfo.put("applicationName", "MBC 시스템");
        systemInfo.put("version", "1.0.0");
        systemInfo.put("environment", "개발환경");
        systemInfo.put("databaseStatus", "확인 불가");
        model.addAttribute("systemInfo", systemInfo);

        Map<String, Object> userStats = new HashMap<>();
        userStats.put("totalUsers", 0);
        userStats.put("activeUsers", 0);
        userStats.put("adminUsers", 0);
        model.addAttribute("userStats", userStats);

        Map<String, Object> accountStats = new HashMap<>();
        accountStats.put("totalAccounts", 0);
        accountStats.put("totalTransactions", 0);
        model.addAttribute("accountStats", accountStats);

        model.addAttribute("recentActivities", getDefaultActivities());
    }
}
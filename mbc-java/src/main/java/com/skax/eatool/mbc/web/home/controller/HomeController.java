package com.skax.eatool.mbc.web.home.controller;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.mbc.as.accountas.ASMBC72001;
import com.skax.eatool.mbc.pc.dto.AccountPDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 웹 홈 컨트롤러
 * 
 * @author SKAX Project Team
 * @version 1.0
 */
@Controller
@RequestMapping("/web")
public class HomeController {
    
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final NewIKesaLogger kesaLogger = NewKesaLogHelper.getBiz();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ASMBC72001 accountReadService;  // 계정 조회 서비스

    /**
     * 웹 홈화면 표시
     */
    @GetMapping("/home")
    public String showWebHome(Model model) {
        logger.info("=== HomeController.showWebHome START ===");
        kesaLogger.info("웹 홈화면 요청", "HomeController");

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

            kesaLogger.info("웹 홈화면 데이터 로드 완료", "HomeController");

        } catch (Exception e) {
            kesaLogger.error("웹 홈화면 데이터 로드 중 오류: " + e.getMessage(), "HomeController");
            // 기본값 설정
            setDefaultValues(model);
        }

        logger.info("=== HomeController.showWebHome END ===");
        return "web/home";
    }

    /**
     * 계정관리 API 테스트 페이지
     */
    @GetMapping("/account-test")
    public String showAccountTestPage(Model model) {
        logger.info("=== HomeController.showAccountTestPage START ===");
        kesaLogger.info("계정관리 API 테스트 페이지 요청", "HomeController");

        try {
            // API 엔드포인트 정보
            Map<String, Object> apiInfo = new HashMap<>();
            apiInfo.put("baseUrl", "/api/account");
            apiInfo.put("endpoints", getAccountApiEndpoints());
            
            model.addAttribute("apiInfo", apiInfo);
            model.addAttribute("currentTime", LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss")));

            kesaLogger.info("계정관리 API 테스트 페이지 데이터 로드 완료", "HomeController");

        } catch (Exception e) {
            kesaLogger.error("계정관리 API 테스트 페이지 데이터 로드 중 오류: " + e.getMessage(), "HomeController");
        }

        logger.info("=== HomeController.showAccountTestPage END ===");
        return "web/account/test";
    }

    /**
     * 계정 관리 페이지
     */
    @GetMapping("/account-management")
    public String showAccountManagementPage(Model model) {
        logger.info("=== HomeController.showAccountManagementPage START ===");
        kesaLogger.info("계정 관리 페이지 요청", "HomeController");

        try {
            // 현재 시간
            String currentTime = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
            model.addAttribute("currentTime", currentTime);

            // API 엔드포인트 정보
            Map<String, Object> apiInfo = new HashMap<>();
            apiInfo.put("baseUrl", "/api/accounts");
            apiInfo.put("endpoints", getAccountApiEndpoints());
            
            model.addAttribute("apiInfo", apiInfo);

            kesaLogger.info("계정 관리 페이지 데이터 로드 완료", "HomeController");

        } catch (Exception e) {
            kesaLogger.error("계정 관리 페이지 데이터 로드 중 오류: " + e.getMessage(), "HomeController");
        }

        logger.info("=== HomeController.showAccountManagementPage END ===");
        return "account/account-management";
    }

    /**
     * 사용자 관리 페이지
     */
    @GetMapping("/user-management")
    public String showUserManagementPage(Model model) {
        logger.info("=== HomeController.showUserManagementPage START ===");
        kesaLogger.info("사용자 관리 페이지 요청", "HomeController");

        try {
            // API 엔드포인트 정보
            Map<String, Object> apiInfo = new HashMap<>();
            apiInfo.put("baseUrl", "/api/user");
            apiInfo.put("endpoints", getUserApiEndpoints());
            
            model.addAttribute("apiInfo", apiInfo);
            model.addAttribute("currentTime", LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss")));

            kesaLogger.info("사용자 관리 페이지 데이터 로드 완료", "HomeController");

        } catch (Exception e) {
            kesaLogger.error("사용자 관리 페이지 데이터 로드 중 오류: " + e.getMessage(), "HomeController");
        }

        logger.info("=== HomeController.showUserManagementPage END ===");
        return "user/user-management";
    }
    
    /**
     * 보고서 관리 페이지 표시
     */
    @GetMapping("/report-management")
    public String showReportManagementPage(Model model) {
        logger.info("=== HomeController.showReportManagementPage START ===");
        kesaLogger.info("보고서 관리 페이지 요청", "HomeController");

        try {
            // API 엔드포인트 정보
            Map<String, Object> apiInfo = new HashMap<>();
            apiInfo.put("baseUrl", "/api/report");
            apiInfo.put("endpoints", getReportApiEndpoints());
            
            model.addAttribute("apiInfo", apiInfo);
            model.addAttribute("currentTime", LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss")));

            kesaLogger.info("보고서 관리 페이지 데이터 로드 완료", "HomeController");

        } catch (Exception e) {
            kesaLogger.error("보고서 관리 페이지 데이터 로드 중 오류: " + e.getMessage(), "HomeController");
        }

        logger.info("=== HomeController.showReportManagementPage END ===");
        return "report/report-management";
    }

    /**
     * 사용자 통계 전용 페이지 표시
     */
    @GetMapping("/user-statistics")
    public String showUserStatisticsPage(Model model) {
        logger.info("=== HomeController.showUserStatisticsPage START ===");
        kesaLogger.info("사용자 통계 페이지 요청", "HomeController");

        try {
            model.addAttribute("currentTime", LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss")));

            kesaLogger.info("사용자 통계 페이지 데이터 로드 완료", "HomeController");

        } catch (Exception e) {
            kesaLogger.error("사용자 통계 페이지 데이터 로드 중 오류: " + e.getMessage(), "HomeController");
        }

        logger.info("=== HomeController.showUserStatisticsPage END ===");
        return "report/user-statistics";
    }

    /**
     * 계정관리 API 엔드포인트 정보 반환
     */
    @GetMapping("/api-info/account/endpoints")
    @ResponseBody
    public Map<String, Object> getAccountApiEndpoints() {
        Map<String, Object> endpoints = new HashMap<>();
        
        // 계정 목록 조회
        Map<String, Object> listAccount = new HashMap<>();
        listAccount.put("method", "GET");
        listAccount.put("url", "/api/account/list");
        listAccount.put("description", "계정 목록 조회");
        listAccount.put("parameters", new String[]{"searchKeyword", "searchType", "page", "size"});
        listAccount.put("requestBody", getListAccountRequestBody());
        endpoints.put("listAccount", listAccount);
        
        // 계정 상세 조회
        Map<String, Object> getAccount = new HashMap<>();
        getAccount.put("method", "GET");
        getAccount.put("url", "/api/account/detail/{accountId}");
        getAccount.put("description", "계정 상세 정보 조회");
        getAccount.put("parameters", new String[]{"accountId (path)"});
        endpoints.put("getAccount", getAccount);
        
        // 계정 생성
        Map<String, Object> createAccount = new HashMap<>();
        createAccount.put("method", "POST");
        createAccount.put("url", "/api/account/create");
        createAccount.put("description", "새 계정 생성");
        createAccount.put("requestBody", getCreateAccountRequestBody());
        endpoints.put("createAccount", createAccount);
        
        // 계정 수정
        Map<String, Object> updateAccount = new HashMap<>();
        updateAccount.put("method", "PUT");
        updateAccount.put("url", "/api/account/{accountId}");
        updateAccount.put("description", "계정 정보 수정");
        updateAccount.put("parameters", new String[]{"accountId (path)"});
        updateAccount.put("requestBody", getUpdateAccountRequestBody());
        endpoints.put("updateAccount", updateAccount);
        
        // 계정 삭제
        Map<String, Object> deleteAccount = new HashMap<>();
        deleteAccount.put("method", "DELETE");
        deleteAccount.put("url", "/api/account/{accountId}");
        deleteAccount.put("description", "계정 삭제");
        deleteAccount.put("parameters", new String[]{"accountId (path)"});
        endpoints.put("deleteAccount", deleteAccount);
        
        return endpoints;
    }

    /**
     * 계정 생성 API 요청 본문 예시
     */
    private Map<String, Object> getCreateAccountRequestBody() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("accountId", "ACC001");
        requestBody.put("accountName", "홍길동");
        requestBody.put("accountType", "SAVINGS");
        requestBody.put("accountBalance", "1000000");
        requestBody.put("accountStatus", "ACTIVE");
        return requestBody;
    }

    /**
     * 계정 목록 조회 API 요청 본문 예시
     */
    private Map<String, Object> getListAccountRequestBody() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("searchKeyword", "");
        requestBody.put("accountType", "");
        requestBody.put("pageNumber", 1);
        requestBody.put("pageSize", 10);
        return requestBody;
    }

    /**
     * 계정 수정 API 요청 본문 예시
     */
    private Map<String, Object> getUpdateAccountRequestBody() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("accountName", "홍길동 (수정)");
        requestBody.put("accountType", "SAVINGS");
        requestBody.put("accountBalance", "1500000");
        requestBody.put("accountStatus", "ACTIVE");
        return requestBody;
    }

    /**
     * 시스템 정보 조회
     */
    private Map<String, Object> getSystemInfo() {
        logger.info("=== HomeController.getSystemInfo START ===");
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
            kesaLogger.warn("시스템 정보 조회 실패: " + e.getMessage(), "HomeController");
            systemInfo.put("systemName", "SKAX-EATOOL");
            systemInfo.put("version", "2.0.0");
            systemInfo.put("environment", "개발환경");
            systemInfo.put("databaseStatus", "확인 불가");
        }

        logger.info("=== HomeController.getSystemInfo END ===");
        return systemInfo;
    }

    /**
     * 업무별 통계 조회
     */
    private Map<String, Object> getBusinessStats() {
        logger.info("=== HomeController.getBusinessStats START ===");
        Map<String, Object> stats = new HashMap<>();

        try {
            // 계정 통계
            Map<String, Object> accountStats = getAccountStats();
            stats.put("accountStats", accountStats);

            // 사용자 통계
            Map<String, Object> userStats = getUserStats();
            stats.put("userStats", userStats);

            // 시스템 통계
            Map<String, Object> systemStats = getSystemStats();
            stats.put("systemStats", systemStats);

        } catch (Exception e) {
            logger.error("업무별 통계 조회 중 오류: " + e.getMessage());
            kesaLogger.error("업무별 통계 조회 중 오류: " + e.getMessage(), "HomeController");
            
            // 기본값 설정
            stats.put("accountStats", getDefaultAccountStats());
            stats.put("userStats", getDefaultUserStats());
            stats.put("systemStats", getDefaultSystemStats());
        }

        logger.info("=== HomeController.getBusinessStats END ===");
        return stats;
    }

    /**
     * 계정 통계 조회
     */
    private Map<String, Object> getAccountStats() {
        logger.info("=== HomeController.getAccountStats START ===");
        Map<String, Object> accountStats = new HashMap<>();

        try {
            // NewKBData 생성하여 계정 목록 조회
            NewKBData kbData = new NewKBData();
            NewGenericDto inputDto = kbData.getInputGenericDto();
            
            // 전체 계정 조회를 위한 파라미터 설정
            inputDto.put("searchKeyword", "");
            inputDto.put("pageNumber", 1);
            inputDto.put("pageSize", 1000); // 충분히 큰 값으로 설정
            
            // 계정 조회 서비스 호출
            NewKBData result = accountReadService.execute(kbData);
            
            if (result != null && result.getOutputGenericDto() != null) {
                Object accountListObj = result.getOutputGenericDto().using(NewGenericDto.OUTDATA).get("AccountPDTO");
                
                if (accountListObj instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<AccountPDTO> accountList = (List<AccountPDTO>) accountListObj;
                    
                    int totalAccounts = accountList.size();
                    int activeAccounts = 0;
                    int inactiveAccounts = 0;
                    
                    for (AccountPDTO account : accountList) {
                        if ("ACTIVE".equals(account.getAccountStatus())) {
                            activeAccounts++;
                        } else {
                            inactiveAccounts++;
                        }
                    }
                    
                    accountStats.put("totalAccounts", totalAccounts);
                    accountStats.put("activeAccounts", activeAccounts);
                    accountStats.put("inactiveAccounts", inactiveAccounts);
                    accountStats.put("lastUpdate", LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                } else {
                    accountStats = getDefaultAccountStats();
                }
            } else {
                accountStats = getDefaultAccountStats();
            }
            
        } catch (Exception e) {
            logger.error("계정 통계 조회 중 오류: " + e.getMessage());
            kesaLogger.error("계정 통계 조회 중 오류: " + e.getMessage(), "HomeController");
            accountStats = getDefaultAccountStats();
        }

        logger.info("=== HomeController.getAccountStats END ===");
        return accountStats;
    }

    /**
     * 기본 계정 통계
     */
    private Map<String, Object> getDefaultAccountStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalAccounts", 0);
        stats.put("activeAccounts", 0);
        stats.put("inactiveAccounts", 0);
        stats.put("lastUpdate", LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return stats;
    }

    /**
     * 사용자 통계 조회
     */
    private Map<String, Object> getUserStats() {
        logger.info("=== HomeController.getUserStats START ===");
        Map<String, Object> userStats = new HashMap<>();

        try {
            int totalUsers = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USER_INFO", Integer.class);
            userStats.put("totalUsers", totalUsers);
            userStats.put("lastUpdate", LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } catch (Exception e) {
            logger.error("사용자 통계 조회 중 오류: " + e.getMessage());
            kesaLogger.error("사용자 통계 조회 중 오류: " + e.getMessage(), "HomeController");
            userStats.put("totalUsers", 0);
            userStats.put("lastUpdate", LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        logger.info("=== HomeController.getUserStats END ===");
        return userStats;
    }

    /**
     * 기본 사용자 통계
     */
    private Map<String, Object> getDefaultUserStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", 0);
        stats.put("lastUpdate", LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return stats;
    }

    /**
     * 시스템 통계 조회
     */
    private Map<String, Object> getSystemStats() {
        logger.info("=== HomeController.getSystemStats START ===");
        Map<String, Object> systemStats = new HashMap<>();

        try {
            // 활성 세션 수 조회
            int activeSessions = 5; // 임시 값
            systemStats.put("activeSessions", activeSessions);
            systemStats.put("lastUpdate", LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } catch (Exception e) {
            logger.error("시스템 통계 조회 중 오류: " + e.getMessage());
            kesaLogger.error("시스템 통계 조회 중 오류: " + e.getMessage(), "HomeController");
            systemStats.put("activeSessions", 0);
            systemStats.put("lastUpdate", LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        logger.info("=== HomeController.getSystemStats END ===");
        return systemStats;
    }

    /**
     * 기본 시스템 통계
     */
    private Map<String, Object> getDefaultSystemStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("activeSessions", 0);
        stats.put("lastUpdate", LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return stats;
    }

    /**
     * 최근 활동 조회
     */
    private List<Map<String, Object>> getRecentActivities() {
        logger.info("=== HomeController.getRecentActivities START ===");
        List<Map<String, Object>> activities = new ArrayList<>();

        try {
            // 실제 데이터베이스에서 조회 시도
            String sql = "SELECT USER_NAME, ROLE, STATUS, CREATED_DATE FROM USER_INFO ORDER BY CREATED_DATE DESC LIMIT 5";
            List<Map<String, Object>> dbActivities = jdbcTemplate.queryForList(sql);

            // 데이터베이스 결과를 템플릿 형식에 맞게 변환
            for (Map<String, Object> dbActivity : dbActivities) {
                Map<String, Object> activity = new HashMap<>();
                activity.put("type", "USER");
                activity.put("name", dbActivity.get("USER_NAME"));
                activity.put("date", dbActivity.get("CREATED_DATE"));
                activities.add(activity);
            }

            // 데이터가 없으면 기본 데이터 제공
            if (activities.isEmpty()) {
                activities = getDefaultActivities();
            }

        } catch (Exception e) {
            kesaLogger.warn("최근 활동 조회 실패: " + e.getMessage(), "HomeController");
            activities = getDefaultActivities();
        }

        logger.info("=== HomeController.getRecentActivities END ===");
        return activities;
    }

    /**
     * 기본 활동 데이터
     */
    private List<Map<String, Object>> getDefaultActivities() {
        logger.info("=== HomeController.getDefaultActivities START ===");
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

        Map<String, Object> activity4 = new HashMap<>();
        activity4.put("type", "USER");
        activity4.put("name", "사용자 등록");
        activity4.put("date", "2024-01-15 11:30:00");
        activities.add(activity4);

        Map<String, Object> activity5 = new HashMap<>();
        activity5.put("type", "SYSTEM");
        activity5.put("name", "데이터베이스 연결");
        activity5.put("date", "2024-01-15 10:15:00");
        activities.add(activity5);

        logger.info("=== HomeController.getDefaultActivities END ===");
        return activities;
    }

    /**
     * 네비게이션 메뉴 구성
     */
    private List<Map<String, Object>> getNavigationMenus() {
        logger.info("=== HomeController.getNavigationMenus START ===");
        List<Map<String, Object>> menus = new ArrayList<>();

        // 계정 관리 메뉴
        Map<String, Object> accountMenu = new HashMap<>();
        accountMenu.put("id", "account");
        accountMenu.put("title", "계정 관리");
        accountMenu.put("icon", "fas fa-user-circle");
        accountMenu.put("color", "#667eea");
        accountMenu.put("description", "계정 생성, 조회, 수정, 삭제");

        List<Map<String, Object>> accountSubMenus = new ArrayList<>();

        Map<String, Object> apiTest = new HashMap<>();
        apiTest.put("title", "API 테스트");
        apiTest.put("url", "/web/account-test");
        apiTest.put("icon", "fas fa-code");
        apiTest.put("method", "GET");
        apiTest.put("description", "계정관리 API를 테스트합니다");
        accountSubMenus.add(apiTest);

        Map<String, Object> accountManagement = new HashMap<>();
        accountManagement.put("title", "계정 관리");
        accountManagement.put("url", "/web/account-management");
        accountManagement.put("icon", "fas fa-user-cog");
        accountManagement.put("method", "GET");
        accountManagement.put("description", "계정 생성, 조회, 수정, 삭제");
        accountSubMenus.add(accountManagement);

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

        Map<String, Object> userManagement = new HashMap<>();
        userManagement.put("title", "사용자 관리");
        userManagement.put("url", "/web/user-management");
        userManagement.put("icon", "fas fa-user-cog");
        userManagement.put("method", "GET");
        userManagement.put("description", "사용자 등록, 조회, 수정, 삭제");
        userSubMenus.add(userManagement);

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
        database.put("url", "/h2-console");
        database.put("icon", "fas fa-database");
        database.put("method", "GET");
        systemSubMenus.add(database);

        Map<String, Object> monitoring = new HashMap<>();
        monitoring.put("title", "시스템 모니터링");
        monitoring.put("url", "/mbc/monitoring");
        monitoring.put("icon", "fas fa-chart-line");
        monitoring.put("method", "GET");
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

        Map<String, Object> reportManagement = new HashMap<>();
        reportManagement.put("title", "보고서 관리");
        reportManagement.put("url", "/web/report-management");
        reportManagement.put("icon", "fas fa-chart-bar");
        reportManagement.put("method", "GET");
        reportManagement.put("description", "통계 보고서 및 분석");
        reportSubMenus.add(reportManagement);

        Map<String, Object> accountReport = new HashMap<>();
        accountReport.put("title", "계정 통계");
        accountReport.put("url", "/web/report-management");
        accountReport.put("icon", "fas fa-chart-pie");
        accountReport.put("method", "GET");
        accountReport.put("description", "계정 관련 통계 차트 및 분석");
        reportSubMenus.add(accountReport);

        Map<String, Object> userReport = new HashMap<>();
        userReport.put("title", "사용자 통계");
        userReport.put("url", "/web/user-statistics");
        userReport.put("icon", "fas fa-chart-area");
        userReport.put("method", "GET");
        userReport.put("description", "사용자 관련 통계 차트 및 분석");
        reportSubMenus.add(userReport);

        reportMenu.put("subMenus", reportSubMenus);
        menus.add(reportMenu);

        logger.info("=== HomeController.getNavigationMenus END ===");
        return menus;
    }

    /**
     * 사용자관리 API 엔드포인트 정보 반환
     */
    @GetMapping("/api-info/user/endpoints")
    @ResponseBody
    public Map<String, Object> getUserApiEndpoints() {
        Map<String, Object> endpoints = new HashMap<>();
        
        // 사용자 목록 조회
        Map<String, Object> listUser = new HashMap<>();
        listUser.put("method", "GET");
        listUser.put("url", "/api/user/list");
        listUser.put("description", "사용자 목록 조회");
        listUser.put("parameters", new String[]{"searchKeyword", "searchType", "page", "size"});
        listUser.put("requestBody", getListUserRequestBody());
        endpoints.put("listUser", listUser);
        
        // 사용자 상세 조회
        Map<String, Object> getUser = new HashMap<>();
        getUser.put("method", "GET");
        getUser.put("url", "/api/user/detail/{userId}");
        getUser.put("description", "사용자 상세 정보 조회");
        getUser.put("parameters", new String[]{"userId (path)"});
        endpoints.put("getUser", getUser);
        
        // 사용자 생성
        Map<String, Object> createUser = new HashMap<>();
        createUser.put("method", "POST");
        createUser.put("url", "/api/user/create");
        createUser.put("description", "새 사용자 생성");
        createUser.put("requestBody", getCreateUserRequestBody());
        endpoints.put("createUser", createUser);
        
        // 사용자 수정
        Map<String, Object> updateUser = new HashMap<>();
        updateUser.put("method", "PUT");
        updateUser.put("url", "/api/user/{userId}");
        updateUser.put("description", "사용자 정보 수정");
        updateUser.put("parameters", new String[]{"userId (path)"});
        updateUser.put("requestBody", getUpdateUserRequestBody());
        endpoints.put("updateUser", updateUser);
        
        // 사용자 삭제
        Map<String, Object> deleteUser = new HashMap<>();
        deleteUser.put("method", "DELETE");
        deleteUser.put("url", "/api/user/{userId}");
        deleteUser.put("description", "사용자 삭제");
        deleteUser.put("parameters", new String[]{"userId (path)"});
        endpoints.put("deleteUser", deleteUser);
        
        return endpoints;
    }

    /**
     * 계정 생성 API 요청 본문 예시
     */
    private Map<String, Object> getCreateUserRequestBody() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", "USER001");
        requestBody.put("userName", "홍길동");
        requestBody.put("userRole", "ADMIN");
        requestBody.put("userStatus", "ACTIVE");
        return requestBody;
    }

    /**
     * 계정 목록 조회 API 요청 본문 예시
     */
    private Map<String, Object> getListUserRequestBody() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("searchKeyword", "");
        requestBody.put("userRole", "");
        requestBody.put("pageNumber", 1);
        requestBody.put("pageSize", 10);
        return requestBody;
    }

    /**
     * 계정 수정 API 요청 본문 예시
     */
    private Map<String, Object> getUpdateUserRequestBody() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userName", "홍길동 (수정)");
        requestBody.put("userRole", "ADMIN");
        requestBody.put("userStatus", "ACTIVE");
        return requestBody;
    }

    /**
     * 보고서 관리 API 엔드포인트 정보 반환
     */
    @GetMapping("/api-info/report/endpoints")
    @ResponseBody
    public Map<String, Object> getReportApiEndpoints() {
        Map<String, Object> endpoints = new HashMap<>();
        
        // 계정 통계 보고서
        Map<String, Object> accountReport = new HashMap<>();
        accountReport.put("method", "GET");
        accountReport.put("url", "/api/report/account");
        accountReport.put("description", "계정 통계 보고서 조회");
        accountReport.put("parameters", new String[]{"startDate", "endDate"});
        accountReport.put("requestBody", null); // GET 요청이므로 본문 없음
        endpoints.put("accountReport", accountReport);
        
        // 사용자 통계 보고서
        Map<String, Object> userReport = new HashMap<>();
        userReport.put("method", "GET");
        userReport.put("url", "/api/report/user");
        userReport.put("description", "사용자 통계 보고서 조회");
        userReport.put("parameters", new String[]{"startDate", "endDate"});
        userReport.put("requestBody", null); // GET 요청이므로 본문 없음
        endpoints.put("userReport", userReport);
        
        return endpoints;
    }

    /**
     * 기본값 설정
     */
    private void setDefaultValues(Model model) {
        logger.info("=== HomeController.setDefaultValues START ===");
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
        logger.info("=== HomeController.setDefaultValues END ===");
    }
}
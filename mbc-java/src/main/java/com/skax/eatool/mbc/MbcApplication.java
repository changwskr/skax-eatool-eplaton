package com.skax.eatool.mbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MBC (Management Business Control) Spring Boot Application
 * 
 * 메인 애플리케이션 클래스로, Spring Boot 애플리케이션을 시작하고
 * 초기화 과정을 관리합니다.
 * 
 * @author KBSTAR
 * @version 1.0.0
 * @since 2024
 */
@SpringBootApplication(exclude = {
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class,
        org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.class,
        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class
})
@ComponentScan(basePackages = {
        "com.skax.eatool.mbc.as",
        "com.skax.eatool.mbc.dc",
        "com.skax.eatool.mbc.pc",
        "com.skax.eatool.mbc.ic",
        // "com.skax.eatool.mbc.fc", // mbc01-java의 UserServiceImpl 오류 방지를 위해 제거
        "com.skax.eatool.mbc.web",
        "com.skax.eatool.mbc.config",
        "com.skax.eatool.mbc.controller"
}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*mbc01.*"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*UserServiceImpl.*"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*fc\\.foundation\\.service\\.impl.*"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*DataAccessTest.*")
})
public class MbcApplication {

    private static final Logger logger = LoggerFactory.getLogger(MbcApplication.class);

    /**
     * 애플리케이션 메인 진입점
     * 
     * @param args 명령행 인수
     */
    public static void main(String[] args) {
        logger.info("=== MbcApplication START ===");
        try {
            logger.info("=== MBC 애플리케이션 시작 ===", "MbcApplication");

            // 시스템 정보 출력
            printSystemInfo();

            // Spring Boot 애플리케이션 시작
            ApplicationContext context = SpringApplication.run(MbcApplication.class, args);

            // 애플리케이션 시작 완료 로그
            printStartupInfo(context);

            logger.info("=== MBC 애플리케이션 시작 완료 ===", "MbcApplication");

        } catch (Exception e) {
            logger.error("MBC 애플리케이션 시작 실패: " + e.getMessage(), "MbcApplication");
            System.err.println("애플리케이션 시작 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        logger.info("=== MbcApplication END ===");
    }

    /**
     * 시스템 정보 출력
     */
    private static void printSystemInfo() {
        logger.info("시스템 정보:", "MbcApplication");
        logger.info("- Java 버전: " + System.getProperty("java.version"), "MbcApplication");
        logger.info("- Java 홈: " + System.getProperty("java.home"), "MbcApplication");
        logger.info("- OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"),
                "MbcApplication");
        logger.info("- 사용자: " + System.getProperty("user.name"), "MbcApplication");
        logger.info("- 작업 디렉토리: " + System.getProperty("user.dir"), "MbcApplication");
    }

    /**
     * 애플리케이션 시작 정보 출력
     * 
     * @param context Spring ApplicationContext
     */
    private static void printStartupInfo(ApplicationContext context) {
        logger.info("=== 애플리케이션 시작 정보 ===", "MbcApplication");

        // 활성 프로파일 확인
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        if (activeProfiles.length > 0) {
            logger.info("- 활성 프로파일: " + String.join(", ", activeProfiles), "MbcApplication");
        } else {
            logger.info("- 활성 프로파일: default", "MbcApplication");
        }

        // 서버 포트 확인
        String serverPort = context.getEnvironment().getProperty("server.port", "8080");
        logger.info("- 서버 포트: " + serverPort, "MbcApplication");

        // 데이터베이스 정보 확인
        String dbUrl = context.getEnvironment().getProperty("spring.datasource.url", "H2 인메모리");
        logger.info("- 데이터베이스: " + dbUrl, "MbcApplication");

        // Bean 개수 확인
        int beanCount = context.getBeanDefinitionNames().length;
        logger.info("- 등록된 Bean 개수: " + beanCount, "MbcApplication");

        // 접속 URL 정보
        logger.info("=== 접속 URL ===", "MbcApplication");
        logger.info("- 애플리케이션: http://localhost:" + serverPort, "MbcApplication");
        logger.info("- Swagger UI: http://localhost:" + serverPort + "/swagger-ui.html", "MbcApplication");
        logger.info("- API 문서: http://localhost:" + serverPort + "/v3/api-docs", "MbcApplication");

        // 개발 환경인 경우 H2 콘솔 정보 추가
        if (isDevProfile(activeProfiles)) {
            logger.info("- H2 콘솔: http://localhost:" + serverPort + "/h2-console", "MbcApplication");
        }

        logger.info("=== 애플리케이션이 성공적으로 시작되었습니다 ===", "MbcApplication");
    }

    /**
     * 개발 프로파일 여부 확인
     * 
     * @param activeProfiles 활성 프로파일 배열
     * @return 개발 프로파일 여부
     */
    private static boolean isDevProfile(String[] activeProfiles) {
        for (String profile : activeProfiles) {
            if ("dev".equals(profile)) {
                return true;
            }
        }
        return false;
    }
}
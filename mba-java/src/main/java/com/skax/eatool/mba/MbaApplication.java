package com.skax.eatool.mba;

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
 * MBA (Master Business Application) Spring Boot Application
 * 
 * 마스터 비즈니스 애플리케이션 클래스로, Spring Boot 애플리케이션을 시작하고
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
        "com.skax.eatool.mba.as",
        "com.skax.eatool.mba.dc",
        "com.skax.eatool.mba.pc",
        "com.skax.eatool.mba.ic",
        "com.skax.eatool.mba.ac",
        "com.skax.eatool.mba.web",
        "com.skax.eatool.mba.config",
        "com.skax.eatool.mba.controller"
}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*mbc01.*"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*UserServiceImpl.*"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*fc\\.foundation\\.service\\.impl.*"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*DataAccessTest.*")
})
public class MbaApplication {

    private static final Logger logger = LoggerFactory.getLogger(MbaApplication.class);

    /**
     * 애플리케이션 메인 진입점
     * 
     * @param args 명령행 인수
     */
    public static void main(String[] args) {
        logger.info("=== MbaApplication START ===");
        try {
            logger.info("=== MBA 애플리케이션 시작 ===", "MbaApplication");

            // 시스템 정보 출력
            printSystemInfo();

            // Spring Boot 애플리케이션 시작
            ApplicationContext context = SpringApplication.run(MbaApplication.class, args);

            // 애플리케이션 시작 완료 로그
            printStartupInfo(context);

            logger.info("=== MBA 애플리케이션 시작 완료 ===", "MbaApplication");

        } catch (Exception e) {
            logger.error("MBA 애플리케이션 시작 실패: " + e.getMessage(), "MbaApplication");
            System.err.println("애플리케이션 시작 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        logger.info("=== MbaApplication END ===");
    }

    /**
     * 시스템 정보 출력
     */
    private static void printSystemInfo() {
        logger.info("시스템 정보:", "MbaApplication");
        logger.info("- Java 버전: " + System.getProperty("java.version"), "MbaApplication");
        logger.info("- Java 홈: " + System.getProperty("java.home"), "MbaApplication");
        logger.info("- OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"),
                "MbaApplication");
        logger.info("- 사용자: " + System.getProperty("user.name"), "MbaApplication");
        logger.info("- 작업 디렉토리: " + System.getProperty("user.dir"), "MbaApplication");
    }

    /**
     * 애플리케이션 시작 정보 출력
     * 
     * @param context Spring ApplicationContext
     */
    private static void printStartupInfo(ApplicationContext context) {
        logger.info("=== 애플리케이션 시작 정보 ===", "MbaApplication");

        // 활성 프로파일 확인
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        if (activeProfiles.length > 0) {
            logger.info("- 활성 프로파일: " + String.join(", ", activeProfiles), "MbaApplication");
        } else {
            logger.info("- 활성 프로파일: default", "MbaApplication");
        }

        // 서버 포트 확인
        String serverPort = context.getEnvironment().getProperty("server.port", "8081");
        logger.info("- 서버 포트: " + serverPort, "MbaApplication");

        // 데이터베이스 정보 확인
        String dbUrl = context.getEnvironment().getProperty("spring.datasource.url", "H2 인메모리");
        logger.info("- 데이터베이스: " + dbUrl, "MbaApplication");

        // Bean 개수 확인
        int beanCount = context.getBeanDefinitionNames().length;
        logger.info("- 등록된 Bean 개수: " + beanCount, "MbaApplication");

        // 접속 URL 정보
        logger.info("=== 접속 URL ===", "MbaApplication");
        logger.info("- 애플리케이션: http://localhost:" + serverPort, "MbaApplication");
        logger.info("- Swagger UI: http://localhost:" + serverPort + "/swagger-ui.html", "MbaApplication");
        logger.info("- API 문서: http://localhost:" + serverPort + "/v3/api-docs", "MbaApplication");

        // 개발 환경인 경우 H2 콘솔 정보 추가
        if (isDevProfile(activeProfiles)) {
            logger.info("- H2 콘솔: http://localhost:" + serverPort + "/h2-console", "MbaApplication");
        }

        logger.info("=== 애플리케이션이 성공적으로 시작되었습니다 ===", "MbaApplication");
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
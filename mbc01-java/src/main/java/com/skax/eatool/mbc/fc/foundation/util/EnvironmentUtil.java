package com.skax.eatool.mbc.fc.foundation.util;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 환경 설정 유틸리티 클래스
 * 
 * 프로그램 내에서 다양한 환경파일을 읽고 처리하는 유틸리티 메서드들을 제공합니다.
 * 
 * @author skax.eatool
 * @version 1.0.0
 * @since 2024
 */
public class EnvironmentUtil {

    private static final NewIKesaLogger logger = NewKesaLoggerFactory.getLogger("EnvironmentUtil");

    /**
     * 클래스패스에서 설정 파일 로드
     */
    public static Properties loadPropertiesFromClasspath(String fileName) {
        Properties props = new Properties();

        try (InputStream input = EnvironmentUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input != null) {
                props.load(input);
                logger.info("설정 파일 로드 성공: " + fileName, "EnvironmentUtil");
            } else {
                logger.warn("설정 파일을 찾을 수 없습니다: " + fileName, "EnvironmentUtil");
            }
        } catch (IOException e) {
            logger.error("설정 파일 로드 실패: " + fileName + " - " + e.getMessage(), "EnvironmentUtil");
        }

        return props;
    }

    /**
     * 파일 시스템에서 설정 파일 로드
     */
    public static Properties loadPropertiesFromFile(String filePath) {
        Properties props = new Properties();

        try (FileInputStream input = new FileInputStream(filePath)) {
            props.load(input);
            logger.info("설정 파일 로드 성공: " + filePath, "EnvironmentUtil");
        } catch (IOException e) {
            logger.error("설정 파일 로드 실패: " + filePath + " - " + e.getMessage(), "EnvironmentUtil");
        }

        return props;
    }

    /**
     * URL에서 설정 파일 로드
     */
    public static Properties loadPropertiesFromUrl(String urlString) {
        Properties props = new Properties();

        try {
            URL url = new URL(urlString);
            try (InputStream input = url.openStream()) {
                props.load(input);
                logger.info("설정 파일 로드 성공: " + urlString, "EnvironmentUtil");
            }
        } catch (IOException e) {
            logger.error("설정 파일 로드 실패: " + urlString + " - " + e.getMessage(), "EnvironmentUtil");
        }

        return props;
    }

    /**
     * 환경변수에서 설정 값 가져오기
     */
    public static String getEnvironmentVariable(String key) {
        return getEnvironmentVariable(key, null);
    }

    /**
     * 환경변수에서 설정 값 가져오기 (기본값 포함)
     */
    public static String getEnvironmentVariable(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null) {
            value = System.getProperty(key, defaultValue);
        }
        return value;
    }

    /**
     * 시스템 속성에서 설정 값 가져오기
     */
    public static String getSystemProperty(String key) {
        return getSystemProperty(key, null);
    }

    /**
     * 시스템 속성에서 설정 값 가져오기 (기본값 포함)
     */
    public static String getSystemProperty(String key, String defaultValue) {
        return System.getProperty(key, defaultValue);
    }

    /**
     * 설정 파일 존재 여부 확인
     */
    public static boolean isConfigFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    /**
     * 클래스패스에서 리소스 존재 여부 확인
     */
    public static boolean isResourceExists(String resourcePath) {
        return EnvironmentUtil.class.getClassLoader().getResource(resourcePath) != null;
    }

    /**
     * 환경별 설정 파일 경로 생성
     */
    public static String getConfigFilePath(String baseName, String profile) {
        if (profile == null || profile.isEmpty()) {
            return baseName + ".yml";
        }
        return baseName + "-" + profile + ".yml";
    }

    /**
     * 설정 값 유효성 검사
     */
    public static boolean validateConfigValue(String key, String value) {
        if (value == null || value.trim().isEmpty()) {
            logger.warn("설정 값이 비어있습니다: " + key, "EnvironmentUtil");
            return false;
        }
        return true;
    }

    /**
     * 필수 설정 값 확인
     */
    public static boolean validateRequiredConfig(String[] requiredKeys, Properties props) {
        for (String key : requiredKeys) {
            String value = props.getProperty(key);
            if (!validateConfigValue(key, value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 설정 값 타입 변환
     */
    public static Integer getIntValue(Properties props, String key, Integer defaultValue) {
        String value = props.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.warn("정수 변환 실패: " + key + " = " + value, "EnvironmentUtil");
            return defaultValue;
        }
    }

    public static Boolean getBooleanValue(Properties props, String key, Boolean defaultValue) {
        String value = props.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }

    public static Long getLongValue(Properties props, String key, Long defaultValue) {
        String value = props.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            logger.warn("Long 변환 실패: " + key + " = " + value, "EnvironmentUtil");
            return defaultValue;
        }
    }

    /**
     * 설정 정보 출력
     */
    public static void printProperties(Properties props, String title) {
        logger.info("=== " + title + " ===", "EnvironmentUtil");
        for (String key : props.stringPropertyNames()) {
            String value = props.getProperty(key);
            // 민감한 정보는 마스킹
            if (key.toLowerCase().contains("password") || key.toLowerCase().contains("secret")) {
                value = "***";
            }
            logger.info(key + " = " + value, "EnvironmentUtil");
        }
        logger.info("=== " + title + " 완료 ===", "EnvironmentUtil");
    }

    /**
     * 환경 정보 요약 생성
     */
    public static String generateEnvironmentSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== 환경 정보 요약 ===\n");
        summary.append("Java 버전: ").append(System.getProperty("java.version")).append("\n");
        summary.append("OS: ").append(System.getProperty("os.name")).append(" ")
                .append(System.getProperty("os.version")).append("\n");
        summary.append("사용자: ").append(System.getProperty("user.name")).append("\n");
        summary.append("작업 디렉토리: ").append(System.getProperty("user.dir")).append("\n");
        summary.append("사용 가능한 프로세서: ").append(Runtime.getRuntime().availableProcessors()).append("\n");
        summary.append("총 메모리: ").append(Runtime.getRuntime().totalMemory() / 1024 / 1024).append(" MB\n");
        summary.append("최대 메모리: ").append(Runtime.getRuntime().maxMemory() / 1024 / 1024).append(" MB\n");
        summary.append("=== 환경 정보 요약 완료 ===");

        return summary.toString();
    }

    /**
     * 설정 파일 백업
     */
    public static boolean backupConfigFile(String sourcePath, String backupPath) {
        try {
            File sourceFile = new File(sourcePath);
            File backupFile = new File(backupPath);

            if (!sourceFile.exists()) {
                logger.warn("백업할 파일이 존재하지 않습니다: " + sourcePath, "EnvironmentUtil");
                return false;
            }

            // 백업 디렉토리 생성
            File backupDir = backupFile.getParentFile();
            if (backupDir != null && !backupDir.exists()) {
                backupDir.mkdirs();
            }

            // 파일 복사
            java.nio.file.Files.copy(sourceFile.toPath(), backupFile.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            logger.info("설정 파일 백업 완료: " + sourcePath + " -> " + backupPath, "EnvironmentUtil");
            return true;

        } catch (IOException e) {
            logger.error("설정 파일 백업 실패: " + e.getMessage(), "EnvironmentUtil");
            return false;
        }
    }
}
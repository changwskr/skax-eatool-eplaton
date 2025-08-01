package com.skax.eatool.ksa.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SLF4J 기반 KesaLogHelper
 * 
 * @author AI Assistant
 * @version 2.0
 * @since 2024-01-01
 */
public class NewKesaLogHelper {

    private NewKesaLogHelper() {
        // 유틸리티 클래스이므로 생성자 숨김
    }

    /**
     * 클래스 기반 로거 생성
     * 
     * @param clazz 로거를 생성할 클래스
     * @return NewIKesaLogger 인스턴스
     */
    public static NewIKesaLogger getLogger(Class<?> clazz) {
        return new Slf4jKesaLogger(clazz);
    }

    /**
     * 이름 기반 로거 생성
     * 
     * @param name 로거 이름
     * @return NewIKesaLogger 인스턴스
     */
    public static NewIKesaLogger getLogger(String name) {
        return new Slf4jKesaLogger(name);
    }

    /**
     * 비즈니스 로거 생성 (기본 비즈니스 로거)
     * 
     * @return NewIKesaLogger 인스턴스
     */
    public static NewIKesaLogger getBiz() {
        return new Slf4jKesaLogger("BIZ");
    }

    /**
     * 시스템 로거 생성 (기본 시스템 로거)
     * 
     * @return NewIKesaLogger 인스턴스
     */
    public static NewIKesaLogger getSystem() {
        return new Slf4jKesaLogger("SYSTEM");
    }

    /**
     * 보안 로거 생성 (기본 보안 로거)
     * 
     * @return NewIKesaLogger 인스턴스
     */
    public static NewIKesaLogger getSecurity() {
        return new Slf4jKesaLogger("SECURITY");
    }

    /**
     * 성능 로거 생성 (기본 성능 로거)
     * 
     * @return NewIKesaLogger 인스턴스
     */
    public static NewIKesaLogger getPerformance() {
        return new Slf4jKesaLogger("PERFORMANCE");
    }

    /**
     * 디버그 로그 출력
     * 
     * @param logger 로거 인스턴스
     * @param message 로그 메시지
     */
    public static void debug(NewIKesaLogger logger, String message) {
        if (logger != null) {
            logger.debug(message);
        }
    }

    /**
     * 정보 로그 출력
     * 
     * @param logger 로거 인스턴스
     * @param message 로그 메시지
     */
    public static void info(NewIKesaLogger logger, String message) {
        if (logger != null) {
            logger.info(message);
        }
    }

    /**
     * 경고 로그 출력
     * 
     * @param logger 로거 인스턴스
     * @param message 로그 메시지
     */
    public static void warn(NewIKesaLogger logger, String message) {
        if (logger != null) {
            logger.warn(message);
        }
    }

    /**
     * 오류 로그 출력
     * 
     * @param logger 로거 인스턴스
     * @param message 로그 메시지
     */
    public static void error(NewIKesaLogger logger, String message) {
        if (logger != null) {
            logger.error(message);
        }
    }

    /**
     * 오류 로그 출력 (예외 포함)
     * 
     * @param logger 로거 인스턴스
     * @param message 로그 메시지
     * @param throwable 예외 객체
     */
    public static void error(NewIKesaLogger logger, String message, Throwable throwable) {
        if (logger != null) {
            logger.error(message, throwable);
        }
    }

    /**
     * SLF4J Logger 직접 반환
     * 
     * @param clazz 로거를 생성할 클래스
     * @return SLF4J Logger 인스턴스
     */
    public static Logger getSlf4jLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * SLF4J Logger 직접 반환
     * 
     * @param name 로거 이름
     * @return SLF4J Logger 인스턴스
     */
    public static Logger getSlf4jLogger(String name) {
        return LoggerFactory.getLogger(name);
    }
}
package com.skax.eatool.ksa.logger;

/**
 * SLF4J 기반 KesaLoggerFactory
 * 
 * @author AI Assistant
 * @version 2.0
 * @since 2024-01-01
 */
public class NewKesaLoggerFactory {

    /**
     * 기본 로거 생성
     * 
     * @return NewIKesaLogger 인스턴스
     */
    public static NewIKesaLogger getLogger() {
        return NewKesaLogHelper.getBiz();
    }

    /**
     * 이름 기반 로거 생성
     * 
     * @param name 로거 이름
     * @return NewIKesaLogger 인스턴스
     */
    public static NewIKesaLogger getLogger(String name) {
        return NewKesaLogHelper.getLogger(name);
    }

    /**
     * 클래스 기반 로거 생성
     * 
     * @param clazz 로거를 생성할 클래스
     * @return NewIKesaLogger 인스턴스
     */
    public static NewIKesaLogger getLogger(Class<?> clazz) {
        return NewKesaLogHelper.getLogger(clazz);
    }

    /**
     * 비즈니스 로거 생성
     * 
     * @return NewIKesaLogger 인스턴스
     */
    public static NewIKesaLogger getBizLogger() {
        return NewKesaLogHelper.getBiz();
    }

    /**
     * 시스템 로거 생성
     * 
     * @return NewIKesaLogger 인스턴스
     */
    public static NewIKesaLogger getSystemLogger() {
        return NewKesaLogHelper.getSystem();
    }

    /**
     * 보안 로거 생성
     * 
     * @return NewIKesaLogger 인스턴스
     */
    public static NewIKesaLogger getSecurityLogger() {
        return NewKesaLogHelper.getSecurity();
    }

    /**
     * 성능 로거 생성
     * 
     * @return NewIKesaLogger 인스턴스
     */
    public static NewIKesaLogger getPerformanceLogger() {
        return NewKesaLogHelper.getPerformance();
    }
}
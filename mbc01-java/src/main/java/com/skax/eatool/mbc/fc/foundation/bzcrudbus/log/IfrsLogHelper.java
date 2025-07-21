package com.skax.eatool.mbc.fc.foundation.bzcrudbus.log;

/**
 * IFRS 로그 헬퍼 클래스
 * 
 * 프로그램명: IfrsLogHelper.java
 * 설명: IFRS 로거를 생성하는 헬퍼 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - IFRS 로거 생성
 * - 로거 인스턴스 관리
 * - 로거 설정 관리
 * 
 * @version 1.0
 */
public class IfrsLogHelper {

    /**
     * 기본 생성자
     */
    private IfrsLogHelper() {
    }

    /**
     * 서버 로거를 반환한다.
     * 
     * @return 서버 로거
     */
    public static IIfrsLogger getServer() {
        return IfrsLoggerFactory.getLogger("server");
    }

    /**
     * 비즈니스 로거를 반환한다.
     * 
     * @return 비즈니스 로거
     */
    public static IIfrsLogger getBiz() {
        return IfrsLoggerFactory.getLogger("biz");
    }

    /**
     * 데이터 로거를 반환한다.
     * 
     * @return 데이터 로거
     */
    public static IIfrsLogger getData() {
        return IfrsLoggerFactory.getLogger("data");
    }

    /**
     * 시스템 로거를 반환한다.
     * 
     * @return 시스템 로거
     */
    public static IIfrsLogger getSystem() {
        return IfrsLoggerFactory.getLogger("system");
    }

    /**
     * 보안 로거를 반환한다.
     * 
     * @return 보안 로거
     */
    public static IIfrsLogger getSecurity() {
        return IfrsLoggerFactory.getLogger("security");
    }

    /**
     * 에러 로거를 반환한다.
     * 
     * @return 에러 로거
     */
    public static IIfrsLogger getError() {
        return IfrsLoggerFactory.getLogger("error");
    }

    /**
     * 디버그 로거를 반환한다.
     * 
     * @return 디버그 로거
     */
    public static IIfrsLogger getDebug() {
        return IfrsLoggerFactory.getLogger("debug");
    }

    /**
     * 트랜잭션 로거를 반환한다.
     * 
     * @return 트랜잭션 로거
     */
    public static IIfrsLogger getTransaction() {
        return IfrsLoggerFactory.getLogger("transaction");
    }

    /**
     * 성능 로거를 반환한다.
     * 
     * @return 성능 로거
     */
    public static IIfrsLogger getPerformance() {
        return IfrsLoggerFactory.getLogger("performance");
    }
}

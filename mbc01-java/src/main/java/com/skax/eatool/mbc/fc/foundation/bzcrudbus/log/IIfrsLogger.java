package com.skax.eatool.mbc.fc.foundation.bzcrudbus.log;

import java.util.Enumeration;
import java.util.ResourceBundle;
import org.apache.log4j.*;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.LoggingEvent;

/**
 * IFRS 로거 인터페이스
 * 
 * 프로그램명: IIfrsLogger.java
 * 설명: IFRS 시스템의 로깅 기능을 정의하는 인터페이스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 다양한 로그 레벨 지원 (DEBUG, INFO, WARN, ERROR, FATAL, TRACE)
 * - 시퀀스 기반 로깅
 * - 포맷된 로깅
 * - 예외 처리 로깅
 * - 앱ender 관리
 * - 로거 설정 관리
 * 
 * @version 1.0
 */
public interface IIfrsLogger {

    /**
     * 앱ender를 추가한다.
     * 
     * @param appender 추가할 앱ender
     */
    public abstract void addAppender(Appender appender);

    /**
     * assertion 로그를 기록한다.
     * 
     * @param flag assertion 조건
     * @param s    로그 메시지
     */
    public abstract void assertLog(boolean flag, String s);

    /**
     * 로깅 이벤트를 앱ender들에게 전달한다.
     * 
     * @param loggingevent 로깅 이벤트
     */
    public abstract void callAppenders(LoggingEvent loggingevent);

    /**
     * DEBUG 레벨 로그를 기록한다.
     * 
     * @param obj 로그 메시지
     */
    public abstract void debug(Object obj);

    /**
     * 시퀀스와 함께 DEBUG 레벨 로그를 기록한다.
     * 
     * @param seq 시퀀스 번호
     * @param obj 로그 메시지
     */
    public abstract void debug(String seq, Object obj);

    /**
     * DEBUG 레벨 로그를 기록한다 (예외 포함).
     * 
     * @param obj       로그 메시지
     * @param throwable 예외 객체
     */
    public abstract void debug(Object obj, Throwable throwable);

    /**
     * 포맷된 DEBUG 레벨 로그를 기록한다.
     * 
     * @param obj 로그 메시지
     */
    public abstract void debugF(Object obj);

    /**
     * ERROR 레벨 로그를 기록한다.
     * 
     * @param obj 로그 메시지
     */
    public abstract void error(Object obj);

    /**
     * 시퀀스와 함께 ERROR 레벨 로그를 기록한다.
     * 
     * @param seq 시퀀스 번호
     * @param obj 로그 메시지
     */
    public abstract void error(String seq, Object obj);

    /**
     * ERROR 레벨 로그를 기록한다 (예외 포함).
     * 
     * @param obj       로그 메시지
     * @param throwable 예외 객체
     */
    public abstract void error(Object obj, Throwable throwable);

    /**
     * 포맷된 ERROR 레벨 로그를 기록한다.
     * 
     * @param obj 로그 메시지
     */
    public abstract void errorF(Object obj);

    /**
     * FATAL 레벨 로그를 기록한다.
     * 
     * @param obj 로그 메시지
     */
    public abstract void fatal(Object obj);

    /**
     * 시퀀스와 함께 FATAL 레벨 로그를 기록한다.
     * 
     * @param seq 시퀀스 번호
     * @param obj 로그 메시지
     */
    public abstract void fatal(String seq, Object obj);

    /**
     * FATAL 레벨 로그를 기록한다 (예외 포함).
     * 
     * @param obj       로그 메시지
     * @param throwable 예외 객체
     */
    public abstract void fatal(Object obj, Throwable throwable);

    /**
     * 포맷된 FATAL 레벨 로그를 기록한다.
     * 
     * @param obj 로그 메시지
     */
    public abstract void fatalF(Object obj);

    /**
     * additivity 설정을 반환한다.
     * 
     * @return additivity 설정
     */
    public abstract boolean getAdditivity();

    /**
     * 모든 앱ender를 반환한다.
     * 
     * @return 앱ender 열거
     */
    public abstract Enumeration getAllAppenders();

    /**
     * 지정된 이름의 앱ender를 반환한다.
     * 
     * @param s 앱ender 이름
     * @return 앱ender 객체
     */
    public abstract Appender getAppender(String s);

    /**
     * 유효한 레벨을 반환한다.
     * 
     * @return 유효한 레벨
     */
    public abstract Level getEffectiveLevel();

    /**
     * 로거 저장소를 반환한다.
     * 
     * @return 로거 저장소
     */
    public abstract LoggerRepository getLoggerRepository();

    /**
     * 로거 이름을 반환한다.
     * 
     * @return 로거 이름
     */
    public abstract String getName();

    /**
     * 부모 카테고리를 반환한다.
     * 
     * @return 부모 카테고리
     */
    public abstract Category getParent();

    /**
     * 로거 레벨을 반환한다.
     * 
     * @return 로거 레벨
     */
    public abstract Level getLevel();

    /**
     * 리소스 번들을 반환한다.
     * 
     * @return 리소스 번들
     */
    public abstract ResourceBundle getResourceBundle();

    /**
     * INFO 레벨 로그를 기록한다.
     * 
     * @param obj 로그 메시지
     */
    public abstract void info(Object obj);

    /**
     * 시퀀스와 함께 INFO 레벨 로그를 기록한다.
     * 
     * @param seq 시퀀스 번호
     * @param obj 로그 메시지
     */
    public abstract void info(String seq, Object obj);

    /**
     * INFO 레벨 로그를 기록한다 (예외 포함).
     * 
     * @param obj       로그 메시지
     * @param throwable 예외 객체
     */
    public abstract void info(Object obj, Throwable throwable);

    /**
     * 포맷된 INFO 레벨 로그를 기록한다.
     * 
     * @param obj 로그 메시지
     */
    public abstract void infoF(Object obj);

    /**
     * 앱ender가 연결되어 있는지 확인한다.
     * 
     * @param appender 확인할 앱ender
     * @return 연결되어 있으면 true
     */
    public abstract boolean isAttached(Appender appender);

    /**
     * DEBUG 레벨이 활성화되어 있는지 확인한다.
     * 
     * @return 활성화되어 있으면 true
     */
    public abstract boolean isDebugEnabled();

    /**
     * 지정된 우선순위가 활성화되어 있는지 확인한다.
     * 
     * @param priority 우선순위
     * @return 활성화되어 있으면 true
     */
    public abstract boolean isEnabledFor(Priority priority);

    /**
     * INFO 레벨이 활성화되어 있는지 확인한다.
     * 
     * @return 활성화되어 있으면 true
     */
    public abstract boolean isInfoEnabled();

    /**
     * L7D 로그를 기록한다 (예외 포함).
     * 
     * @param priority  우선순위
     * @param s         메시지 키
     * @param throwable 예외 객체
     */
    public abstract void l7dlog(Priority priority, String s, Throwable throwable);

    /**
     * L7D 로그를 기록한다 (파라미터 및 예외 포함).
     * 
     * @param priority  우선순위
     * @param s         메시지 키
     * @param aobj      파라미터 배열
     * @param throwable 예외 객체
     */
    public abstract void l7dlog(Priority priority, String s, Object aobj[], Throwable throwable);

    /**
     * 로그를 기록한다 (예외 포함).
     * 
     * @param priority  우선순위
     * @param obj       로그 메시지
     * @param throwable 예외 객체
     */
    public abstract void log(Priority priority, Object obj, Throwable throwable);

    /**
     * 로그를 기록한다.
     * 
     * @param priority 우선순위
     * @param obj      로그 메시지
     */
    public abstract void log(Priority priority, Object obj);

    /**
     * 로그를 기록한다 (호출자 클래스명 포함).
     * 
     * @param s         호출자 클래스명
     * @param priority  우선순위
     * @param obj       로그 메시지
     * @param throwable 예외 객체
     */
    public abstract void log(String s, Priority priority, Object obj, Throwable throwable);

    /**
     * 모든 앱ender를 제거한다.
     */
    public abstract void removeAllAppenders();

    /**
     * 앱ender를 제거한다.
     * 
     * @param appender 제거할 앱ender
     */
    public abstract void removeAppender(Appender appender);

    /**
     * 지정된 이름의 앱ender를 제거한다.
     * 
     * @param s 앱ender 이름
     */
    public abstract void removeAppender(String s);

    /**
     * additivity 설정을 변경한다.
     * 
     * @param flag additivity 설정
     */
    public abstract void setAdditivity(boolean flag);

    /**
     * 로거 레벨을 설정한다.
     * 
     * @param level 로거 레벨
     */
    public abstract void setLevel(Level level);

    /**
     * 리소스 번들을 설정한다.
     * 
     * @param resourcebundle 리소스 번들
     */
    public abstract void setResourceBundle(ResourceBundle resourcebundle);

    /**
     * WARN 레벨 로그를 기록한다.
     * 
     * @param obj 로그 메시지
     */
    public abstract void warn(Object obj);

    /**
     * 시퀀스와 함께 WARN 레벨 로그를 기록한다.
     * 
     * @param seq 시퀀스 번호
     * @param obj 로그 메시지
     */
    public abstract void warn(String seq, Object obj);

    /**
     * WARN 레벨 로그를 기록한다 (예외 포함).
     * 
     * @param obj       로그 메시지
     * @param throwable 예외 객체
     */
    public abstract void warn(Object obj, Throwable throwable);

    /**
     * 포맷된 WARN 레벨 로그를 기록한다.
     * 
     * @param obj 로그 메시지
     */
    public abstract void warnF(Object obj);

    /**
     * TRACE 레벨 로그를 기록한다.
     * 
     * @param obj 로그 메시지
     */
    public abstract void trace(Object obj);

    /**
     * 시퀀스와 함께 TRACE 레벨 로그를 기록한다.
     * 
     * @param seq 시퀀스 번호
     * @param obj 로그 메시지
     */
    public abstract void trace(String seq, Object obj);

    /**
     * TRACE 레벨 로그를 기록한다 (예외 포함).
     * 
     * @param obj       로그 메시지
     * @param throwable 예외 객체
     */
    public abstract void trace(Object obj, Throwable throwable);

    /**
     * 포맷된 TRACE 레벨 로그를 기록한다.
     * 
     * @param obj 로그 메시지
     */
    public abstract void traceF(Object obj);

    /**
     * TRACE 레벨이 활성화되어 있는지 확인한다.
     * 
     * @return 활성화되어 있으면 true
     */
    public abstract boolean isTraceEnabled();
}

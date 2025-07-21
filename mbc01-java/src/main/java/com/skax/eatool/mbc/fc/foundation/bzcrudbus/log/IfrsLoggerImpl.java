package com.skax.eatool.mbc.fc.foundation.bzcrudbus.log;

import java.util.Enumeration;
import java.util.ResourceBundle;

import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.LoggingEvent;

/**
 * IFRS 로거 구현 클래스
 * 
 * 프로그램명: IfrsLoggerImpl.java
 * 설명: IIfrsLogger 인터페이스의 구현 클래스로 Log4j를 래핑한 로거
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - Log4j 로거 래핑
 * - 시퀀스 기반 로깅
 * - 포맷된 로깅
 * - 다양한 로그 레벨 지원
 * - 앱ender 관리
 * 
 * @version 1.0
 */
public class IfrsLoggerImpl implements IIfrsLogger {

    /** 내부 Log4j 로거 */
    private Logger logger;

    /** 시퀀스 접두사 */
    private static final String SEQ_PREFIX = "[#";

    /** 시퀀스 접미사 */
    private static final String SEQ_POSTFIX = "]==========";

    /** 포맷 접두사 */
    private static final String F_PREFIX = "================";

    /**
     * 로거를 지정하는 생성자
     * 
     * @param logger Log4j 로거 인스턴스
     */
    public IfrsLoggerImpl(Logger logger) {
        this.logger = logger;
    }

    /**
     * INFO 레벨 로그를 기록한다 (예외 포함).
     * 
     * @param message 로그 메시지
     * @param t       예외 객체
     */
    public void info(Object message, Throwable t) {
        logger.info(message, t);
    }

    /**
     * INFO 레벨 로그를 기록한다.
     * 
     * @param message 로그 메시지
     */
    public void info(Object message) {
        logger.info(message);
    }

    /**
     * 시퀀스와 함께 INFO 레벨 로그를 기록한다.
     * 
     * @param seq     시퀀스 번호
     * @param message 로그 메시지
     */
    public void info(String seq, Object message) {
        logger.info(SEQ_PREFIX + seq + SEQ_POSTFIX + message);
    }

    /**
     * 포맷된 INFO 레벨 로그를 기록한다.
     * 
     * @param message 로그 메시지
     */
    public void infoF(Object message) {
        logger.info(F_PREFIX + message);
    }

    /**
     * DEBUG 레벨 로그를 기록한다 (예외 포함).
     * 
     * @param message 로그 메시지
     * @param t       예외 객체
     */
    public void debug(Object message, Throwable t) {
        logger.debug(message, t);
    }

    /**
     * DEBUG 레벨 로그를 기록한다.
     * 
     * @param message 로그 메시지
     */
    public void debug(Object message) {
        logger.debug(message);
    }

    /**
     * 시퀀스와 함께 DEBUG 레벨 로그를 기록한다.
     * 
     * @param seq     시퀀스 번호
     * @param message 로그 메시지
     */
    public void debug(String seq, Object message) {
        logger.debug(SEQ_PREFIX + seq + SEQ_POSTFIX + message);
    }

    /**
     * 포맷된 DEBUG 레벨 로그를 기록한다.
     * 
     * @param message 로그 메시지
     */
    public void debugF(Object message) {
        logger.debug(F_PREFIX + message);
    }

    /**
     * ERROR 레벨 로그를 기록한다 (예외 포함).
     * 
     * @param message 로그 메시지
     * @param t       예외 객체
     */
    public void error(Object message, Throwable t) {
        logger.error(message, t);
    }

    /**
     * ERROR 레벨 로그를 기록한다.
     * 
     * @param message 로그 메시지
     */
    public void error(Object message) {
        logger.error(message);
    }

    /**
     * 시퀀스와 함께 ERROR 레벨 로그를 기록한다.
     * 
     * @param seq     시퀀스 번호
     * @param message 로그 메시지
     */
    public void error(String seq, Object message) {
        logger.error(SEQ_PREFIX + seq + SEQ_POSTFIX + message);
    }

    /**
     * 포맷된 ERROR 레벨 로그를 기록한다.
     * 
     * @param message 로그 메시지
     */
    public void errorF(Object message) {
        logger.error(F_PREFIX + message);
    }

    /**
     * FATAL 레벨 로그를 기록한다 (예외 포함).
     * 
     * @param message 로그 메시지
     * @param t       예외 객체
     */
    public void fatal(Object message, Throwable t) {
        logger.fatal(message, t);
    }

    /**
     * FATAL 레벨 로그를 기록한다.
     * 
     * @param message 로그 메시지
     */
    public void fatal(Object message) {
        logger.fatal(message);
    }

    /**
     * 시퀀스와 함께 FATAL 레벨 로그를 기록한다.
     * 
     * @param seq     시퀀스 번호
     * @param message 로그 메시지
     */
    public void fatal(String seq, Object message) {
        logger.fatal(SEQ_PREFIX + seq + SEQ_POSTFIX + message);
    }

    /**
     * 포맷된 FATAL 레벨 로그를 기록한다.
     * 
     * @param message 로그 메시지
     */
    public void fatalF(Object message) {
        logger.fatal(F_PREFIX + message);
    }

    /**
     * WARN 레벨 로그를 기록한다 (예외 포함).
     * 
     * @param message 로그 메시지
     * @param t       예외 객체
     */
    public void warn(Object message, Throwable t) {
        logger.warn(message, t);
    }

    /**
     * WARN 레벨 로그를 기록한다.
     * 
     * @param message 로그 메시지
     */
    public void warn(Object message) {
        logger.warn(message);
    }

    /**
     * 시퀀스와 함께 WARN 레벨 로그를 기록한다.
     * 
     * @param seq     시퀀스 번호
     * @param message 로그 메시지
     */
    public void warn(String seq, Object message) {
        logger.warn(SEQ_PREFIX + seq + SEQ_POSTFIX + message);
    }

    /**
     * 포맷된 WARN 레벨 로그를 기록한다.
     * 
     * @param message 로그 메시지
     */
    public void warnF(Object message) {
        logger.warn(F_PREFIX + message);
    }

    /**
     * TRACE 레벨 로그를 기록한다 (예외 포함).
     * 
     * @param message 로그 메시지
     * @param t       예외 객체
     */
    public void trace(Object message, Throwable t) {
        logger.trace(message, t);
    }

    /**
     * TRACE 레벨 로그를 기록한다.
     * 
     * @param message 로그 메시지
     */
    public void trace(Object message) {
        logger.trace(message);
    }

    /**
     * 시퀀스와 함께 TRACE 레벨 로그를 기록한다.
     * 
     * @param seq     시퀀스 번호
     * @param message 로그 메시지
     */
    public void trace(String seq, Object message) {
        logger.trace(SEQ_PREFIX + seq + SEQ_POSTFIX + message);
    }

    /**
     * 포맷된 TRACE 레벨 로그를 기록한다.
     * 
     * @param message 로그 메시지
     */
    public void traceF(Object message) {
        logger.trace(F_PREFIX + message);
    }

    /**
     * 앱ender를 추가한다.
     * 
     * @param newAppender 추가할 앱ender
     */
    public void addAppender(Appender newAppender) {
        logger.addAppender(newAppender);
    }

    /**
     * assertion 로그를 기록한다.
     * 
     * @param assertion assertion 조건
     * @param msg       로그 메시지
     */
    public void assertLog(boolean assertion, String msg) {
        logger.assertLog(assertion, msg);
    }

    /**
     * 로깅 이벤트를 앱ender들에게 전달한다.
     * 
     * @param event 로깅 이벤트
     */
    public void callAppenders(LoggingEvent event) {
        logger.callAppenders(event);
    }

    /**
     * 객체 비교를 수행한다.
     * 
     * @param o 비교할 객체
     * @return 동일하면 true
     */
    public boolean equals(Object o) {
        return logger.equals(o);
    }

    /**
     * additivity 설정을 반환한다.
     * 
     * @return additivity 설정
     */
    public boolean getAdditivity() {
        return logger.getAdditivity();
    }

    /**
     * 모든 앱ender를 반환한다.
     * 
     * @return 앱ender 열거
     */
    public Enumeration getAllAppenders() {
        return logger.getAllAppenders();
    }

    /**
     * 지정된 이름의 앱ender를 반환한다.
     * 
     * @param name 앱ender 이름
     * @return 앱ender 객체
     */
    public Appender getAppender(String name) {
        return logger.getAppender(name);
    }

    /**
     * 유효한 레벨을 반환한다.
     * 
     * @return 유효한 레벨
     */
    public Level getEffectiveLevel() {
        return logger.getEffectiveLevel();
    }

    /**
     * 로거 레벨을 반환한다.
     * 
     * @return 로거 레벨
     */
    public Level getLevel() {
        return logger.getLevel();
    }

    /**
     * 로거 저장소를 반환한다.
     * 
     * @return 로거 저장소
     */
    public LoggerRepository getLoggerRepository() {
        return logger.getLoggerRepository();
    }

    /**
     * 로거 이름을 반환한다.
     * 
     * @return 로거 이름
     */
    public String getName() {
        return logger.getName();
    }

    /**
     * 부모 카테고리를 반환한다.
     * 
     * @return 부모 카테고리
     */
    public Category getParent() {
        return logger.getParent();
    }

    public ResourceBundle getResourceBundle() {
        return logger.getResourceBundle();
    }

    public int hashCode() {
        return logger.hashCode();
    }

    public boolean isAttached(Appender appender) {
        return logger.isAttached(appender);
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public boolean isEnabledFor(Priority level) {
        return logger.isEnabledFor(level);
    }

    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public void l7dlog(Priority priority, String key, Object params[], Throwable t) {
        logger.l7dlog(priority, key, params, t);
    }

    public void l7dlog(Priority priority, String key, Throwable t) {
        logger.l7dlog(priority, key, t);
    }

    public void log(Priority priority, Object message, Throwable t) {
        logger.log(priority, message, t);
    }

    public void log(Priority priority, Object message) {
        logger.log(priority, message);
    }

    public void log(String callerFQCN, Priority level, Object message, Throwable t) {
        logger.log(callerFQCN, level, message, t);
    }

    public void removeAllAppenders() {
        logger.removeAllAppenders();
    }

    public void removeAppender(Appender appender) {
        logger.removeAppender(appender);
    }

    public void removeAppender(String name) {
        logger.removeAppender(name);
    }

    public void setAdditivity(boolean additive) {
        logger.setAdditivity(additive);
    }

    public void setLevel(Level level) {
        logger.setLevel(level);
    }

    public void setResourceBundle(ResourceBundle bundle) {
        logger.setResourceBundle(bundle);
    }

    public String toString() {
        return logger.toString();
    }

    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

}

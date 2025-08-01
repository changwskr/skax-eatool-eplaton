package com.skax.eatool.ksa.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SLF4J 기반 KesaLogger 구현체
 * 
 * @author AI Assistant
 * @version 2.0
 * @since 2024-01-01
 */
public class Slf4jKesaLogger implements NewIKesaLogger {
    
    private final Logger slf4jLogger;
    
    /**
     * 생성자
     * 
     * @param loggerName 로거 이름
     */
    public Slf4jKesaLogger(String loggerName) {
        this.slf4jLogger = LoggerFactory.getLogger(loggerName);
    }
    
    /**
     * 생성자
     * 
     * @param clazz 로거를 생성할 클래스
     */
    public Slf4jKesaLogger(Class<?> clazz) {
        this.slf4jLogger = LoggerFactory.getLogger(clazz);
    }
    
    @Override
    public void info(String seqNo, String message) {
        if (seqNo != null && !seqNo.isEmpty()) {
            slf4jLogger.info("[{}] {}", seqNo, message);
        } else {
            slf4jLogger.info(message);
        }
    }
    
    @Override
    public void debug(String seqNo, String message) {
        if (seqNo != null && !seqNo.isEmpty()) {
            slf4jLogger.debug("[{}] {}", seqNo, message);
        } else {
            slf4jLogger.debug(message);
        }
    }
    
    @Override
    public void error(String seqNo, String message) {
        if (seqNo != null && !seqNo.isEmpty()) {
            slf4jLogger.error("[{}] {}", seqNo, message);
        } else {
            slf4jLogger.error(message);
        }
    }
    
    @Override
    public void warn(String seqNo, String message) {
        if (seqNo != null && !seqNo.isEmpty()) {
            slf4jLogger.warn("[{}] {}", seqNo, message);
        } else {
            slf4jLogger.warn(message);
        }
    }
    
    @Override
    public boolean isDebugEnabled() {
        return slf4jLogger.isDebugEnabled();
    }
    
    @Override
    public void debug(String message) {
        slf4jLogger.debug(message);
    }
    
    @Override
    public void info(String message) {
        slf4jLogger.info(message);
    }
    
    @Override
    public void error(String message) {
        slf4jLogger.error(message);
    }
    
    @Override
    public void warn(String message) {
        slf4jLogger.warn(message);
    }
    
    @Override
    public void error(String message, Throwable throwable) {
        slf4jLogger.error(message, throwable);
    }
    
    @Override
    public void error(String seqNo, String message, Throwable throwable) {
        if (seqNo != null && !seqNo.isEmpty()) {
            slf4jLogger.error("[{}] {}", seqNo, message, throwable);
        } else {
            slf4jLogger.error(message, throwable);
        }
    }
    
    @Override
    public Logger getSlf4jLogger() {
        return slf4jLogger;
    }
} 
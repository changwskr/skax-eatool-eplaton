package com.skax.eatool.ksa.logger;

import org.slf4j.Logger;

/**
 * SLF4J 기반 KesaLogger 인터페이스
 * 
 * @author AI Assistant
 * @version 2.0
 * @since 2024-01-01
 */
public interface NewIKesaLogger {

    /**
     * INFO 레벨 로그 출력
     * 
     * @param seqNo 시퀀스 번호
     * @param message 로그 메시지
     */
    void info(String seqNo, String message);

    /**
     * DEBUG 레벨 로그 출력
     * 
     * @param seqNo 시퀀스 번호
     * @param message 로그 메시지
     */
    void debug(String seqNo, String message);

    /**
     * ERROR 레벨 로그 출력
     * 
     * @param seqNo 시퀀스 번호
     * @param message 로그 메시지
     */
    void error(String seqNo, String message);

    /**
     * WARN 레벨 로그 출력
     * 
     * @param seqNo 시퀀스 번호
     * @param message 로그 메시지
     */
    void warn(String seqNo, String message);

    /**
     * DEBUG 레벨 활성화 여부 확인
     * 
     * @return DEBUG 레벨 활성화 여부
     */
    boolean isDebugEnabled();

    /**
     * DEBUG 레벨 로그 출력 (시퀀스 번호 없음)
     * 
     * @param message 로그 메시지
     */
    void debug(String message);

    /**
     * INFO 레벨 로그 출력 (시퀀스 번호 없음)
     * 
     * @param message 로그 메시지
     */
    void info(String message);

    /**
     * ERROR 레벨 로그 출력 (시퀀스 번호 없음)
     * 
     * @param message 로그 메시지
     */
    void error(String message);

    /**
     * WARN 레벨 로그 출력 (시퀀스 번호 없음)
     * 
     * @param message 로그 메시지
     */
    void warn(String message);

    /**
     * ERROR 레벨 로그 출력 (예외 포함)
     * 
     * @param message 로그 메시지
     * @param throwable 예외 객체
     */
    void error(String message, Throwable throwable);

    /**
     * ERROR 레벨 로그 출력 (시퀀스 번호 + 예외)
     * 
     * @param seqNo 시퀀스 번호
     * @param message 로그 메시지
     * @param throwable 예외 객체
     */
    void error(String seqNo, String message, Throwable throwable);

    /**
     * 내부 SLF4J Logger 객체 반환
     * 
     * @return SLF4J Logger 객체
     */
    Logger getSlf4jLogger();
}
package com.skax.eatool.ksa.logger;

/**
 * Stub interface for IKesaLogger
 */
public interface NewIKesaLogger {

    void info(String seqNo, String message);

    void debug(String seqNo, String message);

    void error(String seqNo, String message);

    void warn(String seqNo, String message);

    boolean isDebugEnabled();

    void debug(String message);
}
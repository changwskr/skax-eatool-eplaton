package com.skax.eatool.ksa.logger;

/**
 * New KesaLoggerFactory class
 * 
 * This is a stub implementation to replace the missing KSA framework
 * KesaLoggerFactory
 */
public class NewKesaLoggerFactory {

    public static NewIKesaLogger getLogger() {
        return NewKesaLogHelper.getBiz();
    }

    public static NewIKesaLogger getLogger(String name) {
        return NewKesaLogHelper.getBiz();
    }
}
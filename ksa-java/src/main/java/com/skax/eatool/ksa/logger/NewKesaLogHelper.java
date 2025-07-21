package com.skax.eatool.ksa.logger;

/**
 * Stub class for KesaLogHelper
 */
public class NewKesaLogHelper {

    public NewKesaLogHelper() {
        // Stub constructor
    }

    // Add stub methods as needed
    public static void debug(NewIKesaLogger logger, String message) {
        // Stub method
    }

    public static void info(NewIKesaLogger logger, String message) {
        // Stub method
    }

    public static void warn(NewIKesaLogger logger, String message) {
        // Stub method
    }

    public static void error(NewIKesaLogger logger, String message) {
        // Stub method
    }

    public static void error(NewIKesaLogger logger, String message, Throwable throwable) {
        // Stub method
    }

    public static NewIKesaLogger getBiz() {
        return new NewIKesaLogger() {
            @Override
            public void info(String seqNo, String message) {
                // Stub implementation
            }

            @Override
            public void debug(String seqNo, String message) {
                // Stub implementation
            }

            @Override
            public void error(String seqNo, String message) {
                // Stub implementation
            }

            @Override
            public void warn(String seqNo, String message) {
                // Stub implementation
            }

            @Override
            public boolean isDebugEnabled() {
                return false;
            }

            @Override
            public void debug(String message) {
                // Stub implementation
            }
        };
    }
}
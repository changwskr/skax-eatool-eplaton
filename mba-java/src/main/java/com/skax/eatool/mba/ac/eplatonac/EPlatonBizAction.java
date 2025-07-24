package com.skax.eatool.mba.ac.eplatonac;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Abstract EPlaton Business Action for SKCC Oversea
 * 
 * Base class for all business actions in the EPlaton framework.
 * Provides common functionality for transaction handling and error management.
 */
@Component
public abstract class EPlatonBizAction {

    private static final Logger logger = LoggerFactory.getLogger(EPlatonBizAction.class);

    @Autowired
    protected TCF tcf;

    /**
     * Execute business action with transaction control
     */
    public EPlatonEvent execute(EPlatonEvent event) {
        String transactionId = generateTransactionId();
        
        try {
            logger.info("Starting business action: {} - Transaction: {}", 
                getClass().getSimpleName(), transactionId);
            
            // Start transaction logging
            logger.info("Transaction started: {} - {}", transactionId, getClass().getSimpleName());

            // Pre-action processing
            preAct(event);

            // Execute main action
            EPlatonEvent result = act(event);

            // Post-action processing
            postAct(result);

            // End transaction logging
            logger.info("Transaction completed: {} - Status: {}", transactionId, Constants.TXN_STATUS_SUCCESS);

            return result;

        } catch (Exception e) {
            logger.error("Business action failed: {} - Transaction: {}", 
                getClass().getSimpleName(), transactionId, e);
            
            // End transaction logging with error
            logger.error("Transaction failed: {} - Status: {}", transactionId, Constants.TXN_STATUS_FAILED);

            // Set error information in event
            if (event.getTPSVCINFODTO() != null) {
                event.getTPSVCINFODTO().setErrorcode("EACT001");
                event.getTPSVCINFODTO().setError_message("Business action failed: " + e.getMessage());
            }

            throw new RuntimeException("Business action execution failed", e);
        }
    }

    /**
     * Pre-action processing hook
     */
    protected void preAct(EPlatonEvent event) {
        logger.debug("Pre-action processing for: {}", getClass().getSimpleName());
        // Override in subclasses if needed
    }

    /**
     * Main action execution - must be implemented by subclasses
     */
    protected abstract EPlatonEvent act(EPlatonEvent event);

    /**
     * Post-action processing hook
     */
    protected void postAct(EPlatonEvent event) {
        logger.debug("Post-action processing for: {}", getClass().getSimpleName());
        // Override in subclasses if needed
    }

    /**
     * Execute specific business logic
     */
    protected EPlatonEvent doAct(EPlatonEvent event) {
        try {
            logger.info("Executing business logic: {} - Request: {}", 
                getClass().getSimpleName(), event.getTPSVCINFODTO().getReqName());

            // TODO: Implement specific business logic in subclasses
            // This is a placeholder implementation
            
            logger.info("Business logic completed successfully: {}", getClass().getSimpleName());
            return event;

        } catch (Exception e) {
            logger.error("Business logic execution failed: {}", getClass().getSimpleName(), e);
            throw new RuntimeException("Method invocation failed", e);
        }
    }

    /**
     * Generate unique transaction ID
     */
    private String generateTransactionId() {
        return "TXN_" + System.currentTimeMillis() + "_" + getClass().getSimpleName();
    }

  /**
   * Set error information
   */
  protected void setErrorInfo(EPlatonEvent event, String errorCode, String errorMessage) {
    TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
    String currentErrorCode = tpsvcinfo.getErrorcode();

    if (currentErrorCode != null && currentErrorCode.startsWith("I")) {
      tpsvcinfo.setErrorcode(errorCode);
      tpsvcinfo.setError_message(errorMessage);
    } else if (currentErrorCode != null && currentErrorCode.startsWith("E")) {
      String combinedErrorCode = errorCode + "|" + currentErrorCode;
      tpsvcinfo.setErrorcode(combinedErrorCode);
      tpsvcinfo.setError_message(errorMessage);
    } else {
      tpsvcinfo.setErrorcode(errorCode);
      tpsvcinfo.setError_message(errorMessage);
    }
  }

  /**
   * Check if event has error
   */
  protected boolean isError(EPlatonEvent event) {
    if (event == null || event.getTPSVCINFODTO() == null) {
      return true;
    }

    String errorCode = event.getTPSVCINFODTO().getErrorcode();
    if (errorCode == null || errorCode.isEmpty()) {
      return false;
    }

    char firstChar = errorCode.charAt(0);
    return firstChar == 'e' || firstChar == 's' || firstChar == 'E' || firstChar == 'S' || firstChar == '*';
  }

  /**
   * Get current event
   */
  protected EPlatonEvent getCurrentEvent() {
    // currentEvent field was removed, return null or implement as needed
    return null;
  }

  /**
   * Get TCF instance
   */
  protected TCF getTcf() {
    return tcf;
  }
}

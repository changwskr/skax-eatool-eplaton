package com.skax.eatool.mbc.ac.eplatonac;

/**
 * IEvent - Event interface for SKCC Oversea
 * 
 * Base interface for all event objects in the framework.
 * Defines common methods for event handling.
 */
public interface IEvent {
    
    /**
     * Set the action for this event
     * @param action the action to set
     */
    void setAction(String action);
    
    /**
     * Get the action for this event
     * @return the action
     */
    String getAction();
    
    /**
     * Set the request data for this event
     * @param request the request data
     */
    void setRequest(IDTO request);
    
    /**
     * Get the request data for this event
     * @return the request data
     */
    IDTO getRequest();
    
    /**
     * Set the response data for this event
     * @param response the response data
     */
    void setResponse(IDTO response);
    
    /**
     * Get the response data for this event
     * @return the response data
     */
    IDTO getResponse();
    
    /**
     * Set the source of this event
     * @param source the source to set
     */
    void setSource(String source);
    
    /**
     * Set the data for this event
     * @param data the data to set
     */
    void setData(Object data);
}

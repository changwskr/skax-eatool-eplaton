package com.skax.eatool.mbc.ac.eplatonac;

import java.io.Serializable;

/**
 * Base DTO (Data Transfer Object) class
 * Replaces com.chb.coses.framework.transfer.DTO
 */
public abstract class DTO implements IDTO, Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String status;
    private String message;

    /**
     * Default constructor
     */
    public DTO() {
    }

    /**
     * Clone this DTO
     */
    public abstract DTO clone();

    /**
     * Convert to string representation
     */
    @Override
    public abstract String toString();

    /**
     * Check if this DTO equals another object
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * Get hash code
     */
    @Override
    public abstract int hashCode();

    // IDTO interface implementation
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}

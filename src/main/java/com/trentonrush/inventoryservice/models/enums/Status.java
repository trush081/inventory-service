package com.trentonrush.inventoryservice.models.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Status {
    AVAILABLE,
    RESERVED,
    UNAVAILABLE;

    private static final Logger logger = LoggerFactory.getLogger(Status.class);

    public static Status fromString(String status) {
        try {
            return Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error("Unknown status: {}", status);
            throw new IllegalArgumentException("Unknown status: " + status);
        }
    }
}

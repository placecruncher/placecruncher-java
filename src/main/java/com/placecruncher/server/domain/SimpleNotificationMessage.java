package com.placecruncher.server.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A simple notification containing a message.
 */
public class SimpleNotificationMessage extends NotificationMessage {
    private final String message;

    @JsonCreator
    public SimpleNotificationMessage(@JsonProperty("message") String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String getText() {
        return message;
    }

    
}

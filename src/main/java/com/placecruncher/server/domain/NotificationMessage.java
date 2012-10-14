package com.placecruncher.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * A notification message
 */
@JsonTypeInfo(use=Id.CLASS)
public abstract class NotificationMessage  {
    // DSDXXX The type info will include an ugly @class property in the JSON
    // DSDXXX Maybe we should include a messageClass property in Notification.java to store the type info.

    /**
     * Gets the human readable form of the message.
     * @return Human readable text form of the message.
     */
    @JsonIgnore
    public abstract String getText();

}

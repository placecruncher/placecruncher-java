package com.placecruncher.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * A notification message
 */
@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="type")
public abstract class NotificationMessage  {
    /**
     * Gets the human readable form of the message.
     * @return Human readable text form of the message.
     */
    @JsonProperty
    public abstract String getText();
    
    @JsonIgnore
    protected void setText(String ignore) {
        // Ignoring the setting allows this property to be serialized into the Json but not deserialized
    }

}

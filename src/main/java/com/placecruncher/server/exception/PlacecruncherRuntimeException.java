// Copyright 2011 INQ Mobile. All rights reserved.
package com.placecruncher.server.exception;

import java.util.Arrays;

/**
 * Default runtime exception for cloud.
 */
public class PlacecruncherRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final int code;

    private final String messageArgs;

    public PlacecruncherRuntimeException(ExceptionCode exceptionCode) {
        super(buildMessage(exceptionCode, null));
        this.code = exceptionCode.getCode();
        this.messageArgs = null; // NOPMD
    }

    public PlacecruncherRuntimeException(ExceptionCode exceptionCode, String messageArgs) {
        super(buildMessage(exceptionCode, messageArgs));
        this.code = exceptionCode.getCode();
        this.messageArgs = messageArgs; // NOPMD
    }

    public PlacecruncherRuntimeException(ExceptionCode exceptionCode, Throwable cause) {
        super(buildMessage(exceptionCode, null));
        this.code = exceptionCode.getCode();
        this.messageArgs = null; // NOPMD
        initCause(cause);
    }

    public PlacecruncherRuntimeException(ExceptionCode exceptionCode, String messageArgs, Throwable cause) {
        super(exceptionCode.getMessage());
        this.code = exceptionCode.getCode();
        this.messageArgs = messageArgs;
        initCause(cause);
    }

    protected static String buildMessage(ExceptionCode exceptionCode, String messageArgs) {
        if (messageArgs == null) {
            return exceptionCode.getMessage();
        } else {
            return exceptionCode.getMessage() + ": " + messageArgs;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ServiceException[");
        sb.append("message=").append(getMessage());
        sb.append(", code=").append(code);
        sb.append(", messageArgs=").append(messageArgs == null ? "null" : Arrays.asList(messageArgs).toString());
        sb.append(']');
        return sb.toString();
    }


    public int getCode() {
        return code;
    }

    public String getMessageArgs() {
        return messageArgs;
    }
}

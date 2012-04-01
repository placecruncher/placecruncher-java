// Copyright 2011 INQ Mobile. All rights reserved.
package com.placecruncher.server.exception;

public enum ExceptionCode {
    UNKNOWN_ERROR(99999);

    protected int code;
    protected String messageKey;

    ExceptionCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return name();
    }

    public static ExceptionCode valueOf(int code) {
        ExceptionCode[] codes = ExceptionCode.values();
        for (ExceptionCode serviceExceptionCode : codes) {
            if (serviceExceptionCode.getCode() == code) {
                return serviceExceptionCode;
            }
        }
        return null;
    }
}

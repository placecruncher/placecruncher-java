// Copyright 2011 INQ Mobile. All rights reserved.
package com.placecruncher.server.controller;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class Meta {
    private int code = HttpServletResponse.SC_OK;

    private Integer errorCode;

    private String errorMessage;


    public Meta() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "Meta [code=" + code + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
    }
}

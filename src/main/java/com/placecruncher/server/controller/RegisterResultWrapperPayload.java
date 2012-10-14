package com.placecruncher.server.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RegisterResultWrapperPayload {
    private boolean userNameTaken;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isUserNameTaken() {
        return userNameTaken;
    }

    public void setUserNameTaken(boolean userNameTaken) {
        this.userNameTaken = userNameTaken;
    }
}

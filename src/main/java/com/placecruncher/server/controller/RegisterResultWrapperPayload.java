package com.placecruncher.server.controller;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
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

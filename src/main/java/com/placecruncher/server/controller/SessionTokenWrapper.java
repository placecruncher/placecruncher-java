package com.placecruncher.server.controller;

public class SessionTokenWrapper extends Response {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

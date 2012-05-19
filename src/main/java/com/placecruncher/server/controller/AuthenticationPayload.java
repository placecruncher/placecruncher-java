package com.placecruncher.server.controller;

import org.apache.commons.lang.StringUtils;

public class AuthenticationPayload {
    private String userName;
    private String password;
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean validate() {
        return !(StringUtils.isEmpty(password) || StringUtils.isEmpty(userName));
    }
    
    @Override
    public String toString() {
        return "RegisterPayload [userName=" + userName + ", password=" + password + "]";
    }

}

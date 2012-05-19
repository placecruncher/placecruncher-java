package com.placecruncher.server.controller;

import org.apache.commons.lang.StringUtils;

public class RegisterPayload {
    private String userName;
    private String password;
    private String email;
    
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
        return !(StringUtils.isEmpty(password) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(email));
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RegisterPayload [userName=" + userName + ", password="
                + password + ", email=" + email + "]";
    }
}

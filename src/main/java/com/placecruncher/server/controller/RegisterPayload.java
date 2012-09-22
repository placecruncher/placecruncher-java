package com.placecruncher.server.controller;

import org.apache.commons.lang.StringUtils;

public class RegisterPayload {
    private String userName;
    private String password;
    private String email;
    private DevicePayload device;
    
    private static String USERNAME_REGEX = "^[a-z0-9_-]{3,15}$";
    
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
    
    public void validate() {
        if (device != null) {
            device.validate();
        }
            
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(email)) {
            throw new IllegalArgumentException();
        }
        
        if (!userName.matches(USERNAME_REGEX)) {
            throw new IllegalArgumentException();
        }
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DevicePayload getDevice() {
        return device;
    }

    public void setDevice(DevicePayload device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "RegisterPayload [userName=" + userName + ", password=" + password + ", email=" + email + ", device=" + device + "]";
    }
}

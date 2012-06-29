package com.placecruncher.server.controller;

import org.apache.commons.lang.StringUtils;

import com.placecruncher.server.domain.DeviceType;

public class RegisterPayload {
    private String userName;
    private String password;
    private String email;
    private DevicePayload device;
    
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
        if (device != null) {
            DeviceType deviceType = DeviceType.getType(device.getDeviceType());
            if (StringUtils.isEmpty(device.getToken()) || deviceType==null) {
                return false;
            }
        }
            
        return !(StringUtils.isEmpty(password) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(email));
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

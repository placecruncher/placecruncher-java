package com.placecruncher.server.controller;

import org.apache.commons.lang.StringUtils;

import com.placecruncher.server.domain.DeviceType;

public class DevicePayload {
    public String deviceType;
    public String token;
    
    public String getDeviceType() {
        return deviceType;
    }
    
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }

    public void validate() {
        DeviceType deviceType = DeviceType.getType(this.getDeviceType());
        if (StringUtils.isEmpty(this.getToken()) || deviceType==null) {
            throw new IllegalArgumentException();
        }
    }
    
    @Override
    public String toString() {
        return "DevicePayload [deviceType=" + deviceType + ", token=" + token + "]";
    }
}

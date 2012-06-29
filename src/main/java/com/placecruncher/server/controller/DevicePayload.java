package com.placecruncher.server.controller;

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

    @Override
    public String toString() {
        return "DevicePayload [deviceType=" + deviceType + ", token=" + token + "]";
    }
}

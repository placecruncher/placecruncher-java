package com.placecruncher.server.domain;

public enum DeviceType {
    ANDRIOD, IPHONE;
    
    public static DeviceType getType(String type) {
        if ("iphone".equalsIgnoreCase(type)) {
            return DeviceType.IPHONE;
        } else if ("android".equalsIgnoreCase(type)) {
            return DeviceType.ANDRIOD;
        }
        return null;
    }
}

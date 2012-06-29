package com.placecruncher.server.dao;

import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.Device;

@Repository
public class DeviceDao extends AbstractDao<Integer, Device> {
    public DeviceDao() {
        super(Device.class);
    }
    
    
}

package com.placecruncher.server.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.Device;

@Repository
public class DeviceDao extends AbstractDao<Integer, Device> {
    public DeviceDao() {
        super(Device.class);
    }
    
    public void removeMemberDevice(int memberId) {
        Query query = createQuery("delete Device d where d.memberId = :memberId");
        query.setInteger("memberId", memberId);
        query.executeUpdate();
    }
}

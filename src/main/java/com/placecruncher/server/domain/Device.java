package com.placecruncher.server.domain;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.placecruncher.server.dao.DeviceDao;

@Entity
@Table(name="DEVICE")
@Configurable(dependencyCheck = true)
public class Device extends SuperEntity {
    private Integer id;
    private Member member;   
    private String token;
    private DeviceType deviceType;
    
    @Autowired
    private DeviceDao deviceDao;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable=false)
    public Integer getId() {
        return id;
    }
      
    protected void setId(Integer id) {
        this.id = id;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="memberId")
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void saveOrUpdate() {
        this.deviceDao.saveOrUpdate(this);
    }
    
    @Override
    public String toString() {
        return "Device [token=" + token + ", deviceType=" + deviceType + ", member=" + member + "]";
    }
}

package com.placecruncher.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.placecruncher.server.dao.MemberSourceRefDao;

@Entity
@Table(name="MEMBER_SOURCE_REF")
@Configurable(dependencyCheck = true)
public class MemberSourceRef extends SuperEntity {
    
    private Integer id;
    
    private Member member;
    private Source source;
    
    @Autowired
    private MemberSourceRefDao memberSourceRefDao;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable=false)
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "memberId", nullable = false, updatable = false)
    public Member getMember() {
        return member;
    }
    
    public void setMember(Member member) {
        this.member = member;
    }
    
    @OneToOne
    @JoinColumn(name = "sourceId", nullable = false, updatable = false)
    public Source getSource() {
        return source;
    }
    
    public void setSource(Source source) {
        this.source = source;
    }
    
    public void saveOrUpdate() {
        this.memberSourceRefDao.saveOrUpdate(this);
    }
    

}

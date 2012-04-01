package com.placecruncher.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.placecruncher.server.application.Constants;
import com.placecruncher.server.dao.MemberDao;

@Entity
@Table(name = "Member")
@Configurable(dependencyCheck = true)
public class Member extends AbstractEntity {
    private Integer id;
    private String email;

    @Autowired
    private MemberDao memberDao;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "EMAIL", length = Constants.EMAIL_MAXLEN)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void saveOrUpdate() {
        this.memberDao.saveOrUpdate(this);
    }
}

package com.placecruncher.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.placecruncher.server.dao.ApprovedEmailDao;

@Entity
@Table(name = "APPROVED_EMAIL")
@Configurable(dependencyCheck = true)
public class ApprovedEmail extends AbstractEntity {
    private Integer id;
    private String email;
    private Principal principal;
    
    @Autowired
    private ApprovedEmailDao approvedEmailDao;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PRINCIPAL_ID")
    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public void saveOrUpdate() {
        this.approvedEmailDao.saveOrUpdate(this);
    }
    
    @Override
    public String toString() {
        return "ApprovedEmail [id=" + id + ", email=" + email + ", principal="
                + principal + "]";
    }
}

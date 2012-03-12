package com.placecruncher.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name=Member.DB_TABLE)
@SequenceGenerator(name = Member.DB_SEQ, sequenceName = Member.DB_SEQ)
public class Member extends AbstractEntity {
    public static final String DB_TABLE = "MEMBER";
    public static final String DB_SEQ = DB_TABLE + "_SEQ";

    private Integer id;
    private Principal principal;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = DB_SEQ)
    @Column(name="ID", nullable=false)
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "PRINCIPAL_ID", nullable = true, unique = true)
    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }


}

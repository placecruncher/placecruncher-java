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

import com.placecruncher.server.application.Constants;


@Entity
@Table(name=Tag.DB_TABLE)
@SequenceGenerator(name = Tag.DB_SEQ, sequenceName = Tag.DB_SEQ)
public class Tag extends AbstractEntity {

    public static final String DB_TABLE = "TAG";
    public static final String DB_SEQ = DB_TABLE + "_SEQ";

    private Integer id;
    private String name;
    private Member member;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = DB_SEQ)
    @Column(name="ID", nullable=false)
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    @Column(name="NAME", nullable=false, length=Constants.NAME_MAXLEN)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne
    @JoinColumn(name = "MEMBER_ID", nullable = true)
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }

}

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
@Table(name=TagRef.DB_TABLE)
@SequenceGenerator(name = TagRef.DB_SEQ, sequenceName = TagRef.DB_SEQ)
public class TagRef extends AbstractEntity {

    public static final String DB_TABLE = "TAG_REF";
    public static final String DB_SEQ = DB_TABLE + "_SEQ";

    private Integer id;
    private Member member;
    private Tag tag;

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
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    @OneToOne
    @JoinColumn(name = "TAG_ID", nullable = false)
    public Tag getTag() {
        return tag;
    }
    public void setTag(Tag tag) {
        this.tag = tag;
    }

}

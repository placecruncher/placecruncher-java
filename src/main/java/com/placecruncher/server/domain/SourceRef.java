package com.placecruncher.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="SOURCE_REF", uniqueConstraints = {@UniqueConstraint(columnNames={"sourceId", "memberId"})})
public class SourceRef extends SuperEntity {
    private Integer id;
    private Member member;
    private Source source;

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
    @JoinColumn(name = "memberId", nullable = false)
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    @OneToOne
    @JoinColumn(name = "sourceId", nullable = false)
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

}

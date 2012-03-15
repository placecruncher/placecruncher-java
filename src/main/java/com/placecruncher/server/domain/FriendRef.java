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
@Table(name=FriendRef.DB_TABLE)
@SequenceGenerator(name = FriendRef.DB_SEQ, sequenceName = FriendRef.DB_SEQ)
public class FriendRef extends AbstractEntity {

    public static final String DB_TABLE = "FRIEND_REF";
    public static final String DB_SEQ = DB_TABLE + "_SEQ";

    private Integer id;
    private Member member;
    private Member friend;

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
    @JoinColumn(name = "MEMBER_ID", nullable = false, updatable = false)
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    @OneToOne
    @JoinColumn(name = "FRIEND_ID", nullable = false, updatable = false)
    public Member getFriend() {
        return friend;
    }
    public void setFriend(Member friend) {
        this.friend = friend;
    }
}

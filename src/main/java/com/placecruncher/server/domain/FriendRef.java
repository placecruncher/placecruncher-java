package com.placecruncher.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="FRIEND_REF")
public class FriendRef extends SuperEntity {
    private Integer id;
    private Member member;
    private Member friend;

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
    @JoinColumn(name = "friendId", nullable = false, updatable = false)
    public Member getFriend() {
        return friend;
    }
    public void setFriend(Member friend) {
        this.friend = friend;
    }
}

package com.placecruncher.server.controller;

import com.placecruncher.server.domain.Member;

public class MemberWrapper extends Response {
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "MemberWrapper [member=" + member + "]";
    }  
}

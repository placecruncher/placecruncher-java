package com.placecruncher.server.controller;

import com.placecruncher.server.domain.Member;

public class MemberWrapper extends Response {
    private Member member;

    protected MemberWrapper() {}

    public MemberWrapper(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public String toString() {
        return "MemberWrapper [member=" + member + "]";
    }
}

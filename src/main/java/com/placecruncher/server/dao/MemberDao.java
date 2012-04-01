package com.placecruncher.server.dao;

import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.Member;

@Repository
public class MemberDao extends AbstractDao<Integer, Member> {

    public MemberDao() {
        super(Member.class);
    }

}

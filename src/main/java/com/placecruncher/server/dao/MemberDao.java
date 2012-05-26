package com.placecruncher.server.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.Member;

@Repository
public class MemberDao extends AbstractDao<Integer, Member> {

    public MemberDao() {
        super(Member.class);
    }

    public Member findByUserName(String userName) {
        Query query = createQuery("select m from Principal as p join p.member as m where p.username = :username");
        query.setString("username", userName);
        return (Member) query.uniqueResult();
    }
}

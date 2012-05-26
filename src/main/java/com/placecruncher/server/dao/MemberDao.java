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
    public Member findByToken(String token) {
        // This will need to be made much smarter.
        Query query = createQuery("from Member m where m.token = :token");
        query.setString("token", token);
        return (Member) query.uniqueResult();
    }
    
    public Member findByUserNameAndPassword(String userName, String password) {
        // This will need to be made much smarter.
        Query query = createQuery("from Member m where m.username = :username and m.password = :password");
        query.setString("username", userName);
        query.setString("password", password);
        return (Member) query.uniqueResult();
    }

}

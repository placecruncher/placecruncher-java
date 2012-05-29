package com.placecruncher.server.dao;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.Member;

@Repository
public class MemberDao extends AbstractDao<Integer, Member> {

    public MemberDao() {
        super(Member.class);
    }

    public Member findByUserName(String userName) {
        return (Member)createCriteria()
            .add(Restrictions.eq("username", userName))
            .uniqueResult();
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

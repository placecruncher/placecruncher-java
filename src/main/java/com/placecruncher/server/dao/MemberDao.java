package com.placecruncher.server.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.MemberSourceRef;
import com.placecruncher.server.domain.Source;

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

    public Member findByEmail(String email) {
        // This will need to be made much smarter.
        Query query = createQuery("from Member m where m.email = :email");
        query.setString("email", email);
        return (Member) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<Member> findBySource(Source source) {
        Query query = createQuery("select msr.member from MemberSourceRef msr where msr.source = :source");
        query.setParameter("source", source);
        return query.list();
    }

    public boolean hasSource(Member member, Source source) {
        Query query = createQuery("select count(*) from MemberSourceRef msr where msr.source = :source and msr.member = :member");
        query.setParameter("source", source);
        query.setParameter("member", member);
        return (Long)query.uniqueResult() > 0;
    }

    public void addSource(Member member, Source source) {
        if (!hasSource(member, source)) {
            MemberSourceRef memberSourcRef = new MemberSourceRef();
            memberSourcRef.setMember(member);
            memberSourcRef.setSource(source);

            getCurrentSession().persist(memberSourcRef);
        }
    }
}

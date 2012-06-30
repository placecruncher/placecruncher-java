package com.placecruncher.server.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Source;
import com.placecruncher.server.domain.SourceRef;


@Repository
public class SourceDao extends AbstractDao<Integer, Source> {

    public SourceDao() {
        super(Source.class);
    }

    @SuppressWarnings("unchecked")
    public List<Source> findByStatus(Source.StatusEnum status) {
        Query query = createQuery("from Source s where s.status = :status");
        query.setParameter("status", status);
        return query.list();

    }

    public Source findByUrl(String url) {
        Query query = createQuery("from Source s where s.url = :url");
        query.setParameter("url", url);
        return (Source)query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<Source> findByMember(Member member) {
        Query query = createQuery("select source from SourceRef ref where ref.member := member");
        query.setParameter("member", member);
        return query.list();
    }

    public void addReference(Source source, Member member) {
        SourceRef ref = new SourceRef();
        ref.setSource(source);
        ref.setMember(member);
        getCurrentSession().save(ref);
    }

    @SuppressWarnings("unchecked")
    public List<Member> findReferences(Source source) {
        Query query = createQuery("select ref.member from SourceRef ref where ref.source = :source");
        query.setParameter("source", source);
        return query.list();
    }


}

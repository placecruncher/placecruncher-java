package com.placecruncher.server.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.ObjectRetrievalFailureException;
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
    public List<Source> find(Source.StatusEnum status, String url) {
        Criteria criteria = createCriteria();
        if (status != null) {
            criteria.add(Restrictions.eq("status", status));
        }
        if (url != null) {
            criteria.add(Restrictions.eq("url", url));
        }
        return criteria.list();
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

    public Source get(int id, Member member) {
        Query query = createQuery("select ref.source from SourceRef ref where ref.member = :member and ref.source.id=:id");
        query.setParameter("member", member);
        query.setParameter("id", id);
        return (Source)query.uniqueResult();
    }

    public List<Source> findByMember(Member member) {
        return findByMember(member, null);
    }

    @SuppressWarnings("unchecked")
    public List<Source> findByMember(Member member, String url) {
        // DSDXXX need a better way to handle search criteria without adding a new param each time.
        Criteria criteria = getCurrentSession().createCriteria(SourceRef.class);
        criteria.add(Restrictions.eq("member", member));
        if (url != null) {
            criteria.createCriteria("source").add(Restrictions.eq("url", url));
        }
        criteria.setProjection(Projections.property("source"));
        return criteria.list();
    }

    public boolean hasReference(Source source, Member member) {
        return get(source.getId(), member) != null;
    }

    public void addReference(Source source, Member member) {
        SourceRef ref = new SourceRef();
        ref.setSource(source);
        ref.setMember(member);
        getCurrentSession().save(ref);
    }

    public boolean removeReference(Source source, Member member) {
        Query query = createQuery("delete from SourceRef ref where ref.member = :member and ref.source = :source");
        query.setParameter("member", member);
        query.setParameter("source", source);
        return query.executeUpdate() != 0;
    }


    @SuppressWarnings("unchecked")
    public List<Member> findReferences(Source source) {
        Query query = createQuery("select ref.member from SourceRef ref where ref.source = :source");
        query.setParameter("source", source);
        return query.list();
    }



}

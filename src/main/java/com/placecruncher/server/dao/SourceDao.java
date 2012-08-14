package com.placecruncher.server.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.Source;


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

}

package com.placecruncher.server.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.Source;


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
}

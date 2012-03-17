package com.placecruncher.server.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.SeedDataLog;

@Repository
public class SeedDataLogDao  extends AbstractDao<Integer, SeedDataLog> {

    public SeedDataLogDao() {
        super(SeedDataLog.class);
    }

    public SeedDataLog getByName(String name)
    {
        Criteria query = createCriteria();
        query.add(Restrictions.eq("name", name));
        return (SeedDataLog)query.uniqueResult();
    }

}

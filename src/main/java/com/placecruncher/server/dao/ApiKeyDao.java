package com.placecruncher.server.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.ApiKey;;

@Repository
public class ApiKeyDao extends AbstractDao<Integer, ApiKey> {
    public ApiKeyDao() {
        super(ApiKey.class);
    }
    
    public ApiKey findByApiKey(String apiKey) {
        // This will need to be made much smarter.
        Query query = createQuery("from ApiKey a where a.key = :key");
        query.setString("key", apiKey);
        return (ApiKey)query.uniqueResult();
    }
}

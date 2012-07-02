package com.placecruncher.server.application.data;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.dao.ApiKeyDao;
import com.placecruncher.server.domain.ApiKey;

public class SecurityTestData extends AbstractSeedData {
    public static final String TEST_KEY="cloud_key";
    public static final String TEST_SECRET="cloud_secret";

    @Autowired
    private ApiKeyDao apiKeyDao;

    public Collection<String> getConfigurations() {
        return Collections.singletonList(TEST_CONFIGURATION);
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }

    @Override
    public int getOrder() {
        return SEED_SECURITY;
    }

    @Override
    public String getName() {
        return "Security Test Data";
    }

    @Override
    public void populate() {
        if (apiKeyDao.findByApiKey(TEST_KEY) == null) {
            ApiKey key = new ApiKey(TEST_KEY, TEST_SECRET);
            apiKeyDao.persist(key);
        }
    }

}

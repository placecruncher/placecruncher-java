package com.placecruncher.server.application.data;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.dao.ApiKeyDao;
import com.placecruncher.server.dao.MemberDao;
import com.placecruncher.server.domain.ApiKey;
import com.placecruncher.server.domain.MemberRole;
import com.placecruncher.server.service.MemberService;

public class SecurityTestData extends AbstractSeedData {
    public static final String TEST_KEY="cloud_key";
    public static final String TEST_SECRET="cloud_secret";
    public static final String TEST_USERNAME = "test_admin";
    public static final String TEST_EMAIL = "test_admin@placecruncher.com";
    public static final String TEST_PASSWORD = "w0rk13w0rk13";

    @Autowired
    private MemberService memberService;

    @Autowired
    private ApiKeyDao apiKeyDao;

    @Autowired
    private MemberDao memberDao;

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
        if (memberDao.findByUserName(TEST_USERNAME) == null) {
            memberService.registerUser(MemberRole.ROLE_ADMIN, TEST_USERNAME, TEST_PASSWORD, TEST_EMAIL, null);
        }
    }

}

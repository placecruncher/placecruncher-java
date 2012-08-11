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

    public static final String ADMIN_USERNAME = "test_admin";
    public static final String ADMIN_PASSWORD = "w0rk13w0rk13";

    public static final String MEMBER_USERNAME = "test_member";
    public static final String MEMBER_PASSWORD = "w0rk13w0rk13";


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
        if (memberDao.findByUserName(ADMIN_USERNAME) == null) {
            memberService.registerUser(MemberRole.ROLE_ADMIN, ADMIN_USERNAME, ADMIN_PASSWORD, ADMIN_USERNAME + "@placecruncher.com", null);
        }
        if (memberDao.findByUserName(MEMBER_USERNAME) == null) {
            memberService.registerUser(MemberRole.ROLE_USER, MEMBER_USERNAME, MEMBER_PASSWORD, MEMBER_USERNAME + "@placecruncher.com", null);
        }
    }

}

package com.placecruncher.server.application.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.placecruncher.server.dao.ApiKeyDao;
import com.placecruncher.server.dao.MemberDao;
import com.placecruncher.server.domain.ApiKey;
import com.placecruncher.server.domain.MemberRole;
import com.placecruncher.server.service.MemberService;

/**
 * Creates data for pre-defined users, roles, and stuff like that.
 */
public class SecurityData extends AbstractSeedData {
    @Value("${webclient.key}")
    private String webClientKey;

    @Value("${webclient.secret}")
    private String webClientSecret;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ApiKeyDao apiKeyDao;

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
        return "Security Seed Data";
    }

    @Override
    public void populate() {
        if (apiKeyDao.findByApiKey(webClientKey) == null) {
            ApiKey key = new ApiKey(webClientKey, webClientSecret);
            apiKeyDao.persist(key);
        }

        if (memberDao.findByUserName("admin") == null) {
            memberService.registerUser(MemberRole.ROLE_ADMIN, "admin", "secret", "admin@placecruncher.com", null);
        }

        if (memberDao.findByUserName("member") == null) {
            memberService.registerUser("member", "cRunch13", "member@placecruncher.com", null);
        }

        if (memberDao.findByUserName("cruncher") == null) {
            memberService.registerUser("cruncher", "Munch13", "cruncher@placecruncher.com", null);
        }
    }

}

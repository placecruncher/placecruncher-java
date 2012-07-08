package com.placecruncher.server.application.data;

import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.dao.MemberDao;
import com.placecruncher.server.domain.MemberRole;
import com.placecruncher.server.service.MemberService;

/**
 * Creates data for pre-defined users, roles, and stuff like that.
 */
public class SecurityData extends AbstractSeedData {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberDao memberDao;

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

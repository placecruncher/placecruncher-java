package com.placecruncher.server.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Principal;
import com.placecruncher.server.domain.PropertyBuilder;

public class MemberDaoTest extends DaoTestCase {
    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberFactory memberFactory;
    
    @Autowired
    private PrincipalFactory principalFactory;
    
    @Test
    public void create() {
        memberFactory.create();
        flush();
    }

    @Test
    public void findByUserName() {
    	Member m1 = memberFactory.create();
    	Principal p1 = principalFactory.create(new PropertyBuilder()
    	    .put("member", m1)
    	    .build());
    	
    	Member m2 = memberFactory.create();
        Principal p2 = principalFactory.create(new PropertyBuilder()
        .put("member", m2)
        .build());

        Assert.assertEquals(m1, memberDao.findByUserName(p1.getUsername()));
        Assert.assertEquals(m2, memberDao.findByUserName(p2.getUsername()));
        Assert.assertNull(memberDao.findByUserName("asdfjkl"));
    }
}

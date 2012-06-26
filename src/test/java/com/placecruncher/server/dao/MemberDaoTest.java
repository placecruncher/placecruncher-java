package com.placecruncher.server.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.PropertyBuilder;

public class MemberDaoTest extends DaoTestCase {
    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberFactory memberFactory;
    
    @Test
    public void create() {
        memberFactory.create();
        flush();
    }

    @Test
    public void findByUserName() {
    	Member m1 = memberFactory.create(new PropertyBuilder()
    	.put("username", "member1")
    	.build());
    	
        Member m2 = memberFactory.create(new PropertyBuilder()
        .put("username", "member2")
        .build());

        Assert.assertEquals(m1, memberDao.findByUserName(m1.getUsername()));
        Assert.assertEquals(m2, memberDao.findByUserName(m2.getUsername()));
        Assert.assertNull(memberDao.findByUserName("asdfjkl"));
    }
}

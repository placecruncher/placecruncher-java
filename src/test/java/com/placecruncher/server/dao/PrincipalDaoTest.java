package com.placecruncher.server.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.domain.Principal;

public class PrincipalDaoTest extends DaoTestCase {
    @Autowired
    private PrincipalFactory principalFactory;

    @Autowired
    private PrincipalDao principalDao;
    
    @Test
    public void create() {
        principalFactory.create();
        flush();
    }
    
    @Test
    public void findByUsername() {
        Principal p1 = principalFactory.create();
        Principal p2 = principalFactory.create();
        
        Assert.assertEquals(p1, principalDao.findByUserName(p1.getUsername()));
        Assert.assertEquals(p2, principalDao.findByUserName(p2.getUsername()));
        Assert.assertNull(principalDao.findByUserName("asdfjkl"));
    }
}

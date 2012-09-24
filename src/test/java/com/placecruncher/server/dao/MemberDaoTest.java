package com.placecruncher.server.dao;

import java.util.Arrays;
import java.util.Collections;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Source;

public class MemberDaoTest extends DaoTestCase {
    @Autowired
    private MemberDao memberDao;

    @Autowired
    private SourceFactory sourceFactory;

    @Autowired
    private MemberFactory memberFactory;

    @Test
    public void create() {
        memberFactory.create();
        flush();
    }

    @Test
    public void findByUserName() {
        Member m1 = memberFactory.create("username", "member1");

        Member m2 = memberFactory.create("username", "member2");

        Assert.assertEquals(m1, memberDao.findByUserName(m1.getUsername()));
        Assert.assertEquals(m2, memberDao.findByUserName(m2.getUsername()));
        Assert.assertNull(memberDao.findByUserName("asdfjkl"));
    }


    @Test
    public void findBySource() {
        Source s1 = sourceFactory.create();
        Source s2 = sourceFactory.create();
        Source s3 = sourceFactory.create();

        Member m1 = memberFactory.create();
        Member m2 = memberFactory.create();

        memberDao.addSource(m1, s1);
        memberDao.addSource(m1, s2);
        memberDao.addSource(m2, s2);

        Assert.assertTrue(CollectionUtils.isEqualCollection(memberDao.findBySource(s1), Arrays.asList(m1)));
        Assert.assertTrue(CollectionUtils.isEqualCollection(memberDao.findBySource(s2), Arrays.asList(m1, m2)));
        Assert.assertTrue(CollectionUtils.isEqualCollection(memberDao.findBySource(s3), Collections.EMPTY_LIST));
    }

    @Test
    public void addSource() {
        Source s1 = sourceFactory.create();
        Member m1 = memberFactory.create();

        Assert.assertFalse(memberDao.hasSource(m1, s1));
        memberDao.addSource(m1, s1);
        Assert.assertTrue(memberDao.hasSource(m1, s1));
    }

    @Test
    public void addSourceMulltipleTimes() {
        Source s1 = sourceFactory.create();
        Member m1 = memberFactory.create();

        memberDao.addSource(m1, s1);
        memberDao.addSource(m1, s1);

        Assert.assertEquals(Arrays.asList(m1), memberDao.findBySource(s1));

    }

}

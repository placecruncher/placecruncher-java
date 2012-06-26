package com.placecruncher.server.dao;

import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.domain.PropertyBuilder;
import com.placecruncher.server.domain.Source;

public class SourceDaoTest extends DaoTestCase {
    @Autowired
    private SourceDao sourceDao;

    @Autowired
    private SourceFactory sourceFactory;

    @Before
    public void setUp() {
    }

    @Test
    public void create() {
        sourceFactory.create();
        flush();
    }

    @Test
    public void findByStatus() {
        Source open = sourceFactory.create(new PropertyBuilder()
            .put("status", Source.StatusEnum.OPEN)
            .build());
        
        Source inProgress = sourceFactory.create(new PropertyBuilder()
            .put("status", Source.StatusEnum.IN_PROGRESS)
            .build());
        
        Source closed = sourceFactory.create(new PropertyBuilder()
            .put("status", Source.StatusEnum.CLOSED)
            .build());
        
        List<Source> sources = sourceDao.findByStatus(Source.StatusEnum.OPEN);
        Assert.assertEquals(Collections.singletonList(open), sources);

        sources = sourceDao.findByStatus(Source.StatusEnum.IN_PROGRESS);
        Assert.assertEquals(Collections.singletonList(inProgress), sources);

        sources = sourceDao.findByStatus(Source.StatusEnum.CLOSED);
        Assert.assertEquals(Collections.singletonList(closed), sources);

    }

    @Test
    public void findByUrl() {
        Source s1 = sourceFactory.create();
        Source s2 = sourceFactory.create();

        Assert.assertFalse(s1.getUrl().equals(s2.getUrl()));

        Assert.assertEquals(s1, sourceDao.findByUrl(s1.getUrl()));
        Assert.assertEquals(s2, sourceDao.findByUrl(s2.getUrl()));
    }
}

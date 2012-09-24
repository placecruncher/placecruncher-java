package com.placecruncher.server.dao;

import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.domain.Source;

public class SourceDaoTest extends DaoTestCase {
//    private final Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private SourceDao sourceDao;

    @Autowired
    private SourceFactory sourceFactory;

    @Test
    public void create() {
        sourceFactory.create();
        flush();
    }

    @Test
    public void findByStatus() {
        Source open = sourceFactory.create("status", Source.StatusEnum.OPEN);

        Source inProgress = sourceFactory.create("status", Source.StatusEnum.IN_PROGRESS);

        Source closed = sourceFactory.create("status", Source.StatusEnum.CLOSED);

        List<Source> sources = sourceDao.findByStatus(Source.StatusEnum.OPEN);
        Assert.assertEquals(Collections.singletonList(open), sources);

        sources = sourceDao.findByStatus(Source.StatusEnum.IN_PROGRESS);
        Assert.assertEquals(Collections.singletonList(inProgress), sources);

        sources = sourceDao.findByStatus(Source.StatusEnum.CLOSED);
        Assert.assertEquals(Collections.singletonList(closed), sources);

    }

    @Test
    public void findByUrl() {
        Source source1 = sourceFactory.create();
        Source source2 = sourceFactory.create();

        Assert.assertFalse(source1.getUrl().equals(source2.getUrl()));

        Assert.assertEquals(source1, sourceDao.findByUrl(source1.getUrl()));
        Assert.assertEquals(source2, sourceDao.findByUrl(source2.getUrl()));
    }



}

package com.placecruncher.server.dao;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Place;
import com.placecruncher.server.domain.PropertyBuilder;
import com.placecruncher.server.domain.Source;

public class SourceDaoTest extends DaoTestCase {
    private final Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private SourceDao sourceDao;

    @Autowired
    private SourceFactory sourceFactory;

    @Autowired
    private MemberFactory memberFactory;

    @Autowired
    private PlaceFactory placeFactory;

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
        Source source1 = sourceFactory.create();
        Source source2 = sourceFactory.create();

        Assert.assertFalse(source1.getUrl().equals(source2.getUrl()));

        Assert.assertEquals(source1, sourceDao.findByUrl(source1.getUrl()));
        Assert.assertEquals(source2, sourceDao.findByUrl(source2.getUrl()));
    }

    @Test
    public void addReference() {
        Source source1 = sourceFactory.create();
        Source source2 = sourceFactory.create();
        Source source3 = sourceFactory.create();

        Member member1 = memberFactory.create();
        Member member2 = memberFactory.create();

        sourceDao.addReference(source2, member1);

        sourceDao.addReference(source3, member1);
        sourceDao.addReference(source3, member2);

        Assert.assertTrue(sourceDao.findReferences(source1).isEmpty());
        Assert.assertTrue(sourceDao.findReferences(source2).containsAll(Arrays.asList(member1)));
        Assert.assertTrue(sourceDao.findReferences(source3).containsAll(Arrays.asList(member1, member2)));
    }

    @Test
    public void removeReference() {
        Source source1 = sourceFactory.create();

        Member member1 = memberFactory.create();
        Member member2 = memberFactory.create();

        sourceDao.addReference(source1, member1);
        sourceDao.addReference(source1, member2);

        Assert.assertTrue(sourceDao.findReferences(source1).containsAll(Arrays.asList(member1, member2)));
        sourceDao.removeReference(source1, member1);
        Assert.assertFalse(sourceDao.findReferences(source1).contains(member1));
        Assert.assertTrue(sourceDao.findReferences(source1).contains(member2));
    }

    @Test
    public void getByMember() {
        Source source1 = sourceFactory.create();
        Source source2 = sourceFactory.create();

        Member member1 = memberFactory.create();

        sourceDao.addReference(source1, member1);

        Assert.assertEquals(source1, sourceDao.get(source1.getId(), member1));
        Assert.assertNull(sourceDao.get(source2.getId(), member1));

    }

    @Test
    public void findByMember() {
        Source source1 = sourceFactory.create();
        Source source2 = sourceFactory.create();

        Member member1 = memberFactory.create();

        sourceDao.addReference(source1, member1);
        sourceDao.addReference(source2, member1);


        Assert.assertEquals(Arrays.asList(source1, source2), sourceDao.findByMember(member1));
    }

    @Test
    public void findByMemberAndUrl() {
        Source source1 = sourceFactory.create();
        Source source2 = sourceFactory.create();

        Member member1 = memberFactory.create();

        sourceDao.addReference(source1, member1);
        sourceDao.addReference(source2, member1);

        Assert.assertEquals(Arrays.asList(source1), sourceDao.findByMember(member1, source1.getUrl()));
    }

}

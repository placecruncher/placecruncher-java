package com.placecruncher.server.dao;

import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.Session;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Place;
import com.placecruncher.server.domain.PropertyBuilder;
import com.placecruncher.server.domain.Source;

public class PlaceDaoTest extends DaoTestCase {
    @Autowired
    private PlaceDao placeDao;

    @Autowired
    private SourceFactory sourceFactory;

    @Autowired
    private PlaceFactory placeFactory;

    @Autowired
    private MemberFactory memberFactory;

    @Test
    public void create() {
        placeFactory.create();
        flush();
    }

    @Test
    public void createWithSource() {
        placeFactory.create(new PropertyBuilder()
          .put("sources[0]", sourceFactory.create())
          .build());
        flush();
    }

    @Test
    public void findBySource() {
        Source source1 = sourceFactory.create();
        Source source2 = sourceFactory.create();
        Source source3 = sourceFactory.create();
        Place place1 = placeFactory.create(new PropertyBuilder()
            .put("sources[0]", source1)
            .put("sources[1]", source2)
            .build());

        Place place2 = placeFactory.create(new PropertyBuilder()
        .put("sources[0]", source2)
        .put("sources[1]", source3)
        .build());

        List<Place> places = placeDao.findBySource(source1);
        Assert.assertEquals(1, places.size());
        Assert.assertTrue(places.contains(place1));

        places = placeDao.findBySource(source2);
        Assert.assertEquals(2, places.size());
        Assert.assertTrue(places.contains(place1));
        Assert.assertTrue(places.contains(place2));

        places = placeDao.findBySource(source3);
        Assert.assertEquals(1, places.size());
        Assert.assertTrue(places.contains(place2));
    }

    @Test
    public void delete() {
        Place place = placeFactory.create();
        Assert.assertNotNull(placeDao.get(place.getId()));
        placeDao.delete(place);
        Assert.assertNull(placeDao.get(place.getId()));
    }

    @Test
    public void deleteWithSources() {
        Source source1 = sourceFactory.create();
        Source source2 = sourceFactory.create();
        Place place = placeFactory.create(new PropertyBuilder()
            .put("sources[0]", source1)
            .put("sources[1]", source2)
            .build());

        flush();
        Assert.assertNotNull(placeDao.get(place.getId()));
        placeDao.delete(place);
        Assert.assertNull(placeDao.get(place.getId()));
    }

    @Test
    public void removeSource() {
        Source source = sourceFactory.create();
        Place place = placeFactory.create(new PropertyBuilder()
        .put("sources[0]", source)
        .build());

        flush();

        Assert.assertTrue(placeDao.load(place.getId()).getSources().contains(source));
        place.getSources().remove(source);
        Session session = flushAndGetNewSession();
        Place updatedPlace = (Place)session.load(Place.class, place.getId());
        Assert.assertNotSame(place, updatedPlace);
        Assert.assertFalse(updatedPlace.getSources().contains(source));
    }

    @Test
    public void findByMember() {
        Member m1 = memberFactory.create();
        Member m2 = memberFactory.create();

        Place p1 = placeFactory.create();
        Place p2 = placeFactory.create();

        placeDao.linkPlace(p1, m1);
        placeDao.linkPlace(p2, m2);

        Assert.assertEquals(Collections.singletonList(p1), placeDao.findByMember(m1));
        Assert.assertEquals(Collections.singletonList(p2), placeDao.findByMember(m2));


    }

}

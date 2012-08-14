package com.placecruncher.server.dao;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Place;
import com.placecruncher.server.domain.PlaceList;
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
    public void deletePlaceWithSources() {
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
    public void removeSourceFromPlace() {
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

    private Place createPlaceWithSources(Source... sources) {
        PropertyBuilder pb = new PropertyBuilder();
        for (int i = 0; i < sources.length; i++) {
            pb.put("sources[" + i + "]", sources[i]);
        }
        return placeFactory.create(pb.build());
    }

    @Test
    public void findByList() {
        Member m1 = memberFactory.create();

        Source s1 = sourceFactory.create();
        Source s2 = sourceFactory.create();
        Place p1 = createPlaceWithSources(s1);
        Place p2 = createPlaceWithSources(s1, s2);
        Place p3 = createPlaceWithSources(s2);

        PlaceList l1 = placeDao.createPlaceList(s1, m1);
        PlaceList l2 = placeDao.createPlaceList(s2, m1);

        Assert.assertTrue(CollectionUtils.isEqualCollection(placeDao.findByList(l1), Arrays.asList(p1, p2)));
        Assert.assertTrue(CollectionUtils.isEqualCollection(placeDao.findByList(l2), Arrays.asList(p2, p3)));

    }

    @Test
    public void findByMember() {

        Member m1 = memberFactory.create();
        Member m2 = memberFactory.create();

        Source s1 = sourceFactory.create();
        Source s2 = sourceFactory.create();
        Place p1 = createPlaceWithSources(s1);
        Place p2 = createPlaceWithSources(s1, s2);
        Place p3 = createPlaceWithSources(s2);

        placeDao.createPlaceList(s1, m1);
        placeDao.createPlaceList(s2, m1);

        placeDao.createPlaceList(s2, m2);

        // m1 has places p1, p2, p3
        Assert.assertTrue(CollectionUtils.isEqualCollection(placeDao.findByMember(m1), Arrays.asList(p1, p2, p3)));

        // m2 has places p2, p3
        Assert.assertTrue(CollectionUtils.isEqualCollection(placeDao.findByMember(m2), Arrays.asList(p2, p3)));

    }

    @Test
    public void removePlaceList() {
        Member m1 = memberFactory.create();

        Source s1 = sourceFactory.create();
        Source s2 = sourceFactory.create();
        Place p1 = createPlaceWithSources(s1);
        Place p2 = createPlaceWithSources(s1, s2);
        Place p3 = createPlaceWithSources(s2);

        placeDao.createPlaceList(s1, m1);
        placeDao.createPlaceList(s2, m1);

        Assert.assertTrue(CollectionUtils.isEqualCollection(placeDao.findByMember(m1), Arrays.asList(p1, p2, p3)));

        // Remove places for s1
        placeDao.removePlaceList(m1, s1);
        Assert.assertTrue(CollectionUtils.isEqualCollection(placeDao.findByMember(m1), Arrays.asList(p2, p3)));

        // Remove places for s2
        placeDao.removePlaceList(m1, s2);
        Assert.assertTrue(placeDao.findByMember(m1).isEmpty());

    }

    @Test
    public void findPlaceList() {
        Member m1 = memberFactory.create();
        Member m2 = memberFactory.create();

        Source s1 = sourceFactory.create();
        Source s2 = sourceFactory.create();
        createPlaceWithSources(s1);
        createPlaceWithSources(s2);

        PlaceList l1 = placeDao.createPlaceList(s1, m1);
        PlaceList l2 = placeDao.createPlaceList(s2, m2);

        Assert.assertTrue(CollectionUtils.isEqualCollection(placeDao.findSourcePlaceList(m1, s1), Arrays.asList(l1)));
        Assert.assertTrue(placeDao.findSourcePlaceList(m1, s2).isEmpty());

        Assert.assertTrue(CollectionUtils.isEqualCollection(placeDao.findSourcePlaceList(m2, s2), Arrays.asList(l2)));
        Assert.assertTrue(placeDao.findSourcePlaceList(m2, s1).isEmpty());
    }

}

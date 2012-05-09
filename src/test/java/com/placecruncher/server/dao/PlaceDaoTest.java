package com.placecruncher.server.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.placecruncher.server.domain.Place;
import com.placecruncher.server.domain.Source;

public class PlaceDaoTest extends DaoTestCase {
    @Autowired
    private PlaceDao placeDao;

    @Autowired
    private SourceFactory sourceFactory;

    @Autowired
    private PlaceFactory placeFactory;

    @Test
    public void create() {
        placeFactory.create();
        flush();
    }

    @Test
    public void createWithSource() {
        placeFactory.create(sourceFactory.create());
        flush();
    }

    @Test
    public void findBySource() {
        Source source1 = sourceFactory.create();
        Source source2 = sourceFactory.create();
        Source source3 = sourceFactory.create();
        Place place1 = placeFactory.create(source1, source2);
        Place place2 = placeFactory.create(source2, source3);

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
        Place place = placeFactory.create(source1, source2);
        flush();
        Assert.assertNotNull(placeDao.get(place.getId()));
        placeDao.delete(place);
        Assert.assertNull(placeDao.get(place.getId()));
    }


}

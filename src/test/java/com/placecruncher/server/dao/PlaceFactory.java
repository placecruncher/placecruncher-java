package com.placecruncher.server.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placecruncher.server.domain.EntityFactory;
import com.placecruncher.server.domain.Place;
import com.placecruncher.server.domain.Source;

@Component
public class PlaceFactory implements EntityFactory<Place> {
    @Autowired
    private PlaceDao placeDao;

    public Place create(Object... values) {
        Place place = build(values);
        return placeDao.load(placeDao.persist(place));
    }

    public Place build(Object... values) {
        Place place = new Place();
        place.setName("Some name");
        for (Object o : values) {
            if (o instanceof Source) {
                place.getSources().add((Source)o);
            }
        }
        return place;
    }
}

package com.placecruncher.server.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placecruncher.server.domain.AbstractEntityFactory;
import com.placecruncher.server.domain.Place;

@Component
public class PlaceFactory extends AbstractEntityFactory<Place> {
    @Autowired
    private PlaceDao placeDao;

    public PlaceDao getDao() {
        return placeDao;
    }

    public Place build(Map<String, Object> properties) {
        Place place = new Place();
        place.setName("Some name");
        populate(place, properties);
        return place;
    }
}

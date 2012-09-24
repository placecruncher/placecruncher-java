package com.placecruncher.server.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placecruncher.server.domain.AbstractEntityFactory;
import com.placecruncher.server.domain.Place;

@Component
public class PlaceFactory extends AbstractEntityFactory<Place> {

    @Autowired
    public PlaceFactory(PlaceDao dao) {
        super(dao);
    }

    public Place instance(int id, Map<String, Object> properties) {
        Place place = new Place();
        place.setName("Some name");
        return place;
    }
}

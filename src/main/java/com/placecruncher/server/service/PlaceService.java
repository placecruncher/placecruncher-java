package com.placecruncher.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.controller.PlaceModel;
import com.placecruncher.server.dao.PlaceDao;
import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Place;
import com.placecruncher.server.domain.PlaceList;
import com.placecruncher.server.domain.Source;

@Service
public class PlaceService {

    @Autowired
    private PlaceDao placeDao;

    @Transactional
    public Place createPlace(PlaceModel model) {
        Place place = new Place();
        updatePlace(place, model);
        return placeDao.load(placeDao.persist(place));
    }

    @Transactional
    public void deletePlace(Place place) {
        placeDao.delete(place);
    }

    @Transactional
    public Place updatePlace(Place place, PlaceModel model) {
        place.setName(model.getName());
        place.setPhone(model.getPhone());
        place.setUrl(model.getUrl());
        place.setAddress(model.getAddress());
        place.setCity(model.getCity());
        place.setState(model.getState());
        place.setZipcode(model.getZipcode());
        place.setCountry(model.getCountry());
        return place;
    }

    /**
     * Create a place based on the source.
     * @param source The source.
     * @param member The owner of the list.
     * @return The new list.
     */
    @Transactional
    public PlaceList createPlaceList(Source source, Member member) {
        return placeDao.createPlaceList(source, member);
    }
}

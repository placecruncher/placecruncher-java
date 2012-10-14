package com.placecruncher.server.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.controller.PlaceModel;
import com.placecruncher.server.dao.PlaceDao;
import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Place;
import com.placecruncher.server.domain.Source;
import com.placecruncher.server.domain.SourceModel;

@Service
public class PlaceService {
    private final Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private PlaceDao placeDao;

    @Autowired
    private NotificationService notificationService;

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

    @Transactional
    public void addPlaces(Member member, Source source) {
        // Right now we only allow a source to be added to a member once
        if (placeDao.findSourceByMember(member, source) == null) {
            placeDao.createPlaceList(source, member);

            // Notify the member
            NewPlaceListMessage notification = new NewPlaceListMessage();
            notification.setSource(new SourceModel(source));
            notificationService.sendNotification(member, notification);
        } else {
            log.warn("Source '" + source.getName() + "' is already associated with member '" + member.getUsername() + "'");
        }
    }

}

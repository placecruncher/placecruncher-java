package com.placecruncher.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.dao.PlaceDao;
import com.placecruncher.server.domain.Place;
import com.placecruncher.server.domain.PlaceModel;

@Service
public class PlaceService {

	@Autowired
	private PlaceDao placeDao;

	@Transactional
	public Place createPlace(PlaceModel model) {
		Place place = new Place();
		place.setName(model.getName());
		place.setPhone(model.getPhone());
		place.setUrl(model.getUrl());
		place.setDescription(model.getLocation());
		return placeDao.load(placeDao.persist(place));
	}

	@Transactional
	public void deletePlace(int id) {
		Place place = placeDao.get(id);
		if (place != null) {
			placeDao.delete(place);
		}
	}
}

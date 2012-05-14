package com.placecruncher.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.dao.PlaceDao;
import com.placecruncher.server.dao.SourceDao;
import com.placecruncher.server.domain.Place;
import com.placecruncher.server.domain.PlaceModel;
import com.placecruncher.server.domain.Source;
import com.placecruncher.server.domain.Source.StatusEnum;

@Service
public class SourceService {
	@Autowired
	private SourceDao sourceDao;

	@Autowired
	private PlaceDao placeDao;

	@Autowired
	private PlaceService placeService;

	@Transactional
	public Source createSource(String name, String url) {
		Source source = new Source();
		source.setName(name);
		source.setUrl(url);
		source.setStatus(StatusEnum.OPEN);
		return sourceDao.load(sourceDao.persist(source));
	}

	@Transactional
	public Place createOrUpdatePlace(Source source, Place place) {
		Place newOrUpdatedPlace  = placeDao.findByExample(place);
		if (newOrUpdatedPlace == null) {
			// TODO need to add validation here or upstream
			place.getSources().clear();
			place.getSources().add(source);
			newOrUpdatedPlace = placeDao.load(placeDao.persist(place));
		} else {
			newOrUpdatedPlace.getSources().add(source);
		}
		return newOrUpdatedPlace;
	}
	@Transactional
	public List<Place> addPlaces(Source source, List<Place> places) {
		List<Place> retval = new ArrayList<Place>();
		for (Place place : places) {
			retval.add(createOrUpdatePlace(source, place));
		}
		return retval;
	}

	@Transactional
	public Place createOrAddPlace(Source source, PlaceModel model) {
		Place place;
		if (model.getId() != null) {
			place = placeDao.load(model.getId());
		} else {
			place = placeService.createPlace(model);
		}
		source.getPlaces().add(place);
		return place;
	}
}

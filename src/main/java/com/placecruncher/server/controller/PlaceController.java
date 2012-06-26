package com.placecruncher.server.controller;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placecruncher.server.application.Constants;
import com.placecruncher.server.dao.PlaceDao;
import com.placecruncher.server.domain.Place;
import com.placecruncher.server.service.PlaceService;

@Controller
@RequestMapping("/site")
public class PlaceController {
	private static final Logger LOG = Logger.getLogger(PlaceController.class);

	@Autowired
	private PlaceService placeService;

    @Autowired
    private PlaceDao placeDao;

    @RequestMapping(value = "places", method=RequestMethod.GET, produces=Constants.JSON_CONTENT)
    public @ResponseBody Collection<PlaceModel> getPlaces() {
    	return PlaceModel.transform(placeDao.findAll());
    }

    @RequestMapping(value = "places", method=RequestMethod.POST, consumes=Constants.JSON_CONTENT)
    public @ResponseBody PlaceModel createPlace(@RequestBody PlaceModel model) {
    	return new PlaceModel(placeService.createPlace(model));
    }

    @RequestMapping(value="places/{id}", method=RequestMethod.GET, produces=Constants.JSON_CONTENT)
    public @ResponseBody PlaceModel getPlace(@PathVariable("id") int id) {
    	return new PlaceModel(placeDao.load(id));
    }

    @RequestMapping(value="places/{id}", method=RequestMethod.PUT, consumes=Constants.JSON_CONTENT)
    public @ResponseBody PlaceModel updatePlace(@PathVariable("id") int id, @RequestBody PlaceModel model) {
    	Place place = placeDao.load(id);
    	return new PlaceModel(placeService.updatePlace(place, model));
    }

    @RequestMapping(value="places/{id}", method=RequestMethod.DELETE)
    public void updatePlace(@PathVariable("id") int id) {
    	Place place = placeDao.get(id);
    	if (place != null) {
    		placeService.deletePlace(place);
    	}
    }
}

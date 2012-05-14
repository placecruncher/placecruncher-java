package com.placecruncher.server.controller;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.placecruncher.server.application.Constants;
import com.placecruncher.server.dao.PlaceDao;
import com.placecruncher.server.dao.SourceDao;
import com.placecruncher.server.domain.PlaceModel;
import com.placecruncher.server.domain.Source;
import com.placecruncher.server.domain.SourceModel;
import com.placecruncher.server.service.SourceService;

@Controller
@RequestMapping("/site")
public class SourceController {
	private static final Logger LOG = Logger.getLogger(SourceController.class);

	@Autowired
	private SourceService sourceService;

    @Autowired
    private SourceDao sourceDao;

    @Autowired
    private PlaceDao placeDao;

    @RequestMapping(value = "sources/list.html", method=RequestMethod.GET, produces=Constants.HTML_CONTENT)
    public ModelAndView listSources(@RequestParam(value="status", required=false) Source.StatusEnum status) {
    	if (status == null) {
    		status = Source.StatusEnum.OPEN;
    	}
        ModelAndView mav = new ModelAndView("sources/listSources");
        mav.addObject("sources", sourceDao.findByStatus(status));
        return mav;
    }

    @RequestMapping(value="sources/{id}/view.html", method=RequestMethod.GET, produces=Constants.HTML_CONTENT)
    public ModelAndView viewSource(@PathVariable("id") int id) {
        Source source = sourceDao.load(id);
        ModelAndView mav = new ModelAndView("sources/viewSource");
        mav.addObject("source", source);
        return mav;
    }

    @RequestMapping(value="sources/{id}/edit.html", method=RequestMethod.GET, produces=Constants.HTML_CONTENT)
    public ModelAndView editSource(@PathVariable("id") int id) {
        Source source = sourceDao.load(id);
        ModelAndView mav = new ModelAndView("sources/editSource");
        mav.addObject("source", source);
        return mav;
    }

    @RequestMapping(value="sources/{id}", method=RequestMethod.GET, produces=Constants.JSON_CONTENT)
    public @ResponseBody SourceModel getSource(@PathVariable("id") int id) {
        Source source = sourceDao.load(id);
        return new SourceModel(source);
    }

    @RequestMapping(value="sources/{id}/places", method=RequestMethod.GET, produces=Constants.JSON_CONTENT)
    public @ResponseBody Collection<PlaceModel> getPlaces(@PathVariable("id") int id) {
        Source source = sourceDao.load(id);
    	return PlaceModel.transform(placeDao.findBySource(source));
    }

    @RequestMapping(value="sources/{id}/places", method=RequestMethod.POST, consumes=Constants.JSON_CONTENT, produces=Constants.JSON_CONTENT)
    public @ResponseBody PlaceModel addPlace(@PathVariable("id") int id, @RequestBody PlaceModel model) {
        Source source = sourceDao.load(id);
    	return new PlaceModel(sourceService.createOrAddPlace(source, model));
    }


}

package com.placecruncher.server.controller;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.placecruncher.server.dao.PlaceDao;
import com.placecruncher.server.dao.SourceDao;
import com.placecruncher.server.domain.PlaceModel;
import com.placecruncher.server.domain.Source;
import com.placecruncher.server.domain.SourceModel;

@Controller
@RequestMapping("/site/sources")
public class SourceController {
	private static final Logger LOG = Logger.getLogger(SourceController.class);

    @Autowired
    private SourceDao sourceDao;

    @Autowired
    private PlaceDao placeDao;

    private ModelAndView getSourceFormView(Source source) {
    	return getSourceFormView(new SourceModel(source));
    }

    private ModelAndView getSourceFormView(SourceModel source) {
        ModelAndView mav = new ModelAndView("source/sourceForm");
        mav.addObject("source", source);
        return mav;
    }

    @RequestMapping(value = "")
    public ModelAndView listSources(@RequestParam("status") Source.StatusEnum status) {
        ModelAndView mav = new ModelAndView("sources");
        mav.addObject("sources", sourceDao.findByStatus(status));
        return mav;
    }

    @RequestMapping(value="{id}.json", method=RequestMethod.GET)
    @ResponseBody
    public SourceModel getSource(@PathVariable("id") int id) {
        Source source = sourceDao.get(id);
        if (source == null) {
        	// TODO handle bad param
        	return null;
        }
        return new SourceModel(source);
    }

    @RequestMapping(value="{id}.html", method=RequestMethod.GET)
    public ModelAndView viewSource(@PathVariable("id") int id) {
        Source source = sourceDao.get(id);
        if (source == null) {
        	// TODO handle bad param
        	return null;
        }

        ModelAndView mav = new ModelAndView("viewSource");
        mav.addObject("source", source);
        return mav;
    }

    @RequestMapping(value="{id}/edit.html", method=RequestMethod.GET)
    public ModelAndView editSource(@PathVariable("id") int id) {
        Source source = sourceDao.get(id);
        if (source == null) {
        	// TODO handle bad param
        	return null;
        }
        return getSourceFormView(source);
    }

    @RequestMapping(value="{id}/edit.html", method=RequestMethod.POST)
    public ModelAndView saveSource(@PathVariable("id") int id, SourceModel source) {
    	return getSourceFormView(source);
    }

    @RequestMapping(value="{id}/places/create.html", method=RequestMethod.GET)
    public ModelAndView createPlace(@PathVariable("id") int id, @RequestParam("index") int index) {
    	ModelAndView mav = new ModelAndView("source/placeForm");
    	mav.addObject("place", new PlaceModel());
    	mav.addObject("index", index);
    	return mav;
    }

}

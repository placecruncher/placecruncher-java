package com.placecruncher.server.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.placecruncher.server.domain.Place;
import com.placecruncher.server.domain.Source;
import com.placecruncher.server.domain.SourceModel;
import com.placecruncher.server.service.SourceService;

@Controller
@RequestMapping("/site")
public class SourceController {
    private final Logger log = Logger.getLogger(this.getClass());

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

    @RequestMapping(value="sources/{id}", method=RequestMethod.POST)
    public void submitSource(@PathVariable("id") int id, HttpServletResponse response) {
        Source source = sourceDao.load(id);
        sourceService.submitSource(source);
        response.setStatus(HttpStatus.NO_CONTENT.value());
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

    @RequestMapping(value="sources/{sourceId}/places/{placeId}", method=RequestMethod.DELETE)
    public void deletePlace(@PathVariable("sourceId") int sourceId, @PathVariable("placeId") int placeId) {
        Source source = sourceDao.load(sourceId);
        Place place = placeDao.load(placeId);

        for (Place p : placeDao.findBySource(source)) {
            log.info("Source " + source + " references place " + p);
            for (Source s : p.getSources()) {
                log.info("Place " + p + " references source " + s);
                log.info(p + ".getSources().contains(" + s + ") is " + p.getSources().contains(s));
                log.info(source + " equals " + s + " is " + source.equals(s));
            }
        }

        log.info("Removing place " + place + " from source " + source);
        sourceService.removePlace(source, place);
    }


}

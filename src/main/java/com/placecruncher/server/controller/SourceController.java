package com.placecruncher.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.placecruncher.server.dao.SourceDao;
import com.placecruncher.server.domain.Source;

@Controller
@RequestMapping("/sources")
public class SourceController {

    @Autowired
    private SourceDao sourceDao;

    @RequestMapping(value = "")
    public ModelAndView listSources(@RequestParam("status") Source.StatusEnum status) {
        ModelAndView mav = new ModelAndView("sources");
        mav.addObject("sources", sourceDao.findByStatus(status));
        return mav;
    }

    @RequestMapping(value="{id}.html", method=RequestMethod.GET, params="action=edit")
    public ModelAndView editSource(@PathVariable("id") int id) {
        Source source = sourceDao.get(id);
        if (source == null) {
        	// TODO handle bad param
        	return null;
        }
        
        ModelAndView mav = new ModelAndView("editSource");
        mav.addObject("source", source);
        return mav;
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

}

package com.placecruncher.server.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.placecruncher.server.application.Constants;
import com.placecruncher.server.dao.ApiKeyDao;
import com.placecruncher.server.dao.SourceDao;
import com.placecruncher.server.domain.ApiKey;
import com.placecruncher.server.domain.Source;

@Controller
@RequestMapping("/site")
public class SourceViewController extends AbstractViewController {
    private final Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private SourceDao sourceDao;

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
        mav.addObject("apiKey", getApiKey());
        return mav;
    }

}

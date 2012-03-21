package com.placecruncher.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.placecruncher.server.dao.SourceDao;
import com.placecruncher.server.domain.Source;

@Controller
@RequestMapping("/site/sources")
public class SourceController {

    @Autowired
    private SourceDao sourceDao;

    @RequestMapping(value = "")
    public ModelAndView listSources(@RequestParam("status") Source.StatusEnum status) {
        ModelAndView mav = new ModelAndView("sources");
        mav.addObject("sources", sourceDao.findByStatus(status));
        return mav;
    }

}

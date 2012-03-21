package com.placecruncher.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.placecruncher.server.domain.Email;


@Controller
@RequestMapping("/api/v1/emails")
public class EmailController {
    
	private static final Logger LOGGER = Logger.getLogger(EmailController.class);

    
    @RequestMapping(value = "")
    public ModelAndView receiveEmail(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        try {
            String timestamp = httpServletRequest.getParameter("timestamp");
            String token = httpServletRequest.getParameter("token");
            String signature = httpServletRequest.getParameter("signature");
        
            LOGGER.info("token:" + token + " timestamp: " + timestamp + " signature: " + signature);
        
            if (timestamp == null || token == null || signature == null) {
                return null;
            }
        
            Email email = new Email();
        
            email.verify("key-6zwuz0tx6qryy2lgo6ww0qlr1uxazke9", token, timestamp, signature);
        } catch (Exception e) {
        	LOGGER.error(e, e);
        }
        return null;
    }

}

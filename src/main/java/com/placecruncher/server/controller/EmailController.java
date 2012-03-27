package com.placecruncher.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.placecruncher.server.domain.Email;
import com.placecruncher.server.service.EmailService;

// Email Controller
@Controller
@RequestMapping("/api/v1/emails")
public class EmailController {

    private static final Logger LOGGER = Logger.getLogger(EmailController.class);
    
    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "")
    public ModelAndView receiveEmail(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        
        Email email = new Email();
        emailService.receviceEmail();
        /*
        try {
            String timestamp = httpServletRequest.getParameter("timestamp");
            String token = httpServletRequest.getParameter("token");
            String signature = httpServletRequest.getParameter("signature");
        
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("token:" + token + " timestamp: " + timestamp + " signature: " + signature);
            }
            
            if (timestamp == null || token == null || signature == null) {
                throw new IllegalArgumentException("token:" + token + " timestamp: " + timestamp + " signature: " + signature);
            }
        
            Email email = new Email();
        
            boolean verifyResult = email.verify(token, timestamp, signature);
        
            if (!verifyResult) {
                throw new IllegalArgumentException("token:" + token + " timestamp: " + timestamp + " signature: " + signature);
            }
        } catch (Exception e) {
        	LOGGER.error(e, e);
        }*/
        return null;
    }
}

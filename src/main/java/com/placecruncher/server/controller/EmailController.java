package com.placecruncher.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.placecruncher.server.domain.Email;


@Controller
@RequestMapping("/emails")
public class EmailController {
    
    private final Log log = LogFactory.getLog(this.getClass());
    
    @RequestMapping(value = "")
    public ModelAndView receiveEmail(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        try {
            String timestamp = httpServletRequest.getParameter("timestamp");
            String token = httpServletRequest.getParameter("token");
            String signature = httpServletRequest.getParameter("signature");
        
            log.info("token:" + token + " timestamp: " + timestamp + " signature: " + signature);
        
            if (timestamp == null || token == null || signature == null) {
                return null;
            }
        
            Email email = new Email();
        
            email.verify("key-6zwuz0tx6qryy2lgo6ww0qlr1uxazke9", token, timestamp, signature);
        } catch (Exception e) {
            log.error(e, e);
        }
        return null;
    }

}

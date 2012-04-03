package com.placecruncher.server.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placecruncher.server.application.InvokerContext;
import com.placecruncher.server.dao.PrincipalDao;
import com.placecruncher.server.domain.Principal;
import com.placecruncher.server.service.MemberService;

@Controller
@RequestMapping("/api/private/v1/members")
public class MemberController {

    private static final Logger LOGGER = Logger.getLogger(MemberController.class);

    @Autowired
    private MemberService memberService;
    
    @Autowired
    PrincipalDao principalDao;
    
    @RequestMapping(method = RequestMethod.POST, value = "self/register")
    @ResponseBody
    public ResponsePayload registerUser(@RequestBody RegisterPayload registerPayload) {

        Meta meta = new Meta();
        ResponsePayload responsePayload = new ResponsePayload(meta);
        SessionTokenWrapper sessionTokenWrapper = new SessionTokenWrapper();
        String token = "";
        
        if (registerPayload.validate()) {
            token = memberService.registerUser(registerPayload.getUserName(), registerPayload.getPassword());
        }
        
        sessionTokenWrapper.setToken(token);      
        responsePayload.setResponse(sessionTokenWrapper);       
        return responsePayload;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "self/token")
    @ResponseBody
    public ResponsePayload token(@RequestBody RegisterPayload registerPayload) {

        Meta meta = new Meta();
        ResponsePayload responsePayload = new ResponsePayload(meta);
        SessionTokenWrapper sessionTokenWrapper = new SessionTokenWrapper();
        String token = "";
        
        Principal principal = null;
        if (registerPayload.validate()) {
            principal = this.principalDao.findByUserNameAndPassword(registerPayload.getUserName(), registerPayload.getPassword());
        }
        
        if (principal != null) {
            token = principal.getToken();
        }
        
        sessionTokenWrapper.setToken(token);      
        responsePayload.setResponse(sessionTokenWrapper);       
        return responsePayload;
    }
}

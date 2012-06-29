package com.placecruncher.server.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placecruncher.server.application.InvokerContext;
import com.placecruncher.server.dao.MemberDao;
import com.placecruncher.server.domain.Device;
import com.placecruncher.server.domain.DeviceType;
import com.placecruncher.server.domain.Member;
import com.placecruncher.server.service.MemberService;

@Controller
@RequestMapping("/api/private/v1/members")
public class MemberController {

    private static final Logger LOGGER = Logger.getLogger(MemberController.class);

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private MemberDao memberDao;
    
    @Autowired
    private InvokerContext invokerContext;
    
    @RequestMapping(method = RequestMethod.POST, value = "self/register")
    @ResponseBody
    public ResponsePayload registerUser(@RequestBody RegisterPayload registerPayload) {

        Meta meta = new Meta();
        ResponsePayload responsePayload = new ResponsePayload(meta);
        SessionTokenWrapper sessionTokenWrapper = new SessionTokenWrapper();
        String token = "";
        
        Device device = null;
        if (registerPayload.validate()) {
            if (registerPayload.getDevice() != null) {
                device = new Device();
                device.setDeviceType(DeviceType.getType(registerPayload.getDevice().getDeviceType()));
                device.setToken(registerPayload.getDevice().getToken());
                
            }
            token = memberService.registerUser(registerPayload.getUserName(), registerPayload.getPassword(), registerPayload.getEmail(), device);
        } else {
            throw new IllegalArgumentException();
        }
        
        sessionTokenWrapper.setToken(token);      
        responsePayload.setResponse(sessionTokenWrapper);       
        return responsePayload;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "self/token")
    @ResponseBody
    public ResponsePayload token(@RequestBody AuthenticationPayload authenticationPayload) {

        Meta meta = new Meta();
        ResponsePayload responsePayload = new ResponsePayload(meta);
        SessionTokenWrapper sessionTokenWrapper = new SessionTokenWrapper();
        String token = "";
        
        Member member = null;
        if (authenticationPayload.validate()) {
            member = this.memberDao.findByUserNameAndPassword(authenticationPayload.getUserName(), authenticationPayload.getPassword());
        }
        
        if (member != null) {
            token = member.getToken();
        }
        
        sessionTokenWrapper.setToken(token);      
        responsePayload.setResponse(sessionTokenWrapper);       
        return responsePayload;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "self")
    @ResponseBody
    public ResponsePayload self() {

        Meta meta = new Meta();
        ResponsePayload responsePayload = new ResponsePayload(meta);
        MemberWrapper memberWrapper = new MemberWrapper();
      
        Member member = invokerContext.getMember();
        
        if (member !=null) {
            memberWrapper.setMember(member);
        }
        
        responsePayload.setResponse(memberWrapper);       
        return responsePayload;
    }
}

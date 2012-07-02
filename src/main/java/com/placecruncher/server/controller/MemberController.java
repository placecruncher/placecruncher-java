package com.placecruncher.server.controller;

import javax.servlet.http.HttpServletResponse;

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
        registerPayload.validate();
        
        if (registerPayload.getDevice() != null) {
            device = new Device();
            device.setDeviceType(DeviceType.getType(registerPayload.getDevice().getDeviceType()));
            device.setToken(registerPayload.getDevice().getToken());    
        }
        token = memberService.registerUser(registerPayload.getUserName(), registerPayload.getPassword(), registerPayload.getEmail(), device);
        
        sessionTokenWrapper.setToken(token);      
        responsePayload.setResponse(sessionTokenWrapper);       
        return responsePayload;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "self/device")
    @ResponseBody
    public ResponsePayload registerDevice(@RequestBody DevicePayload devicePayload) {

        Meta meta = new Meta();
        ResponsePayload responsePayload = new ResponsePayload(meta);
        
        Device device = null;
        devicePayload.validate();
        
        Member member = invokerContext.getMember();
        if (member != null) {
            device = new Device();
            device.setDeviceType(DeviceType.getType(devicePayload.getDeviceType()));
            device.setToken(devicePayload.getToken());
            memberService.registerDevice(member, device);
        } else {
            meta.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        }
        
        
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
        } else {
            meta.setCode(HttpServletResponse.SC_UNAUTHORIZED);
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
      
        Member member = invokerContext.getMember();
        
        if (member !=null) {
            MemberWrapper memberWrapper = new MemberWrapper();
            memberWrapper.setMember(member);
            responsePayload.setResponse(memberWrapper); 
        } else {
            meta.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        }
              
        return responsePayload;
    }
}

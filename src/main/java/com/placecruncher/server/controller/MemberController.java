package com.placecruncher.server.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.placecruncher.server.application.Constants;
import com.placecruncher.server.application.InvokerContext;
import com.placecruncher.server.dao.MemberDao;
import com.placecruncher.server.dao.PlaceDao;
import com.placecruncher.server.dao.SourceDao;
import com.placecruncher.server.domain.Device;
import com.placecruncher.server.domain.DeviceType;
import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Source;
import com.placecruncher.server.domain.SourceModel;
import com.placecruncher.server.domain.SourcePlaceList;
import com.placecruncher.server.exception.ResourceNotFoundException;
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
    private PlaceDao placeDao;

    @Autowired
    private SourceDao sourceDao;

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

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "self")
    @ResponseBody
    public ResponsePayload self() {
        Member member = Member.currentMember();
        return new ResponsePayload(new MemberWrapper(member));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "self/places")
    @ResponseBody
    public ResponseWrapper<Collection<PlaceModel>> myPlaces() {
        Member self = Member.currentMember();
        Collection<PlaceModel> places = PlaceModel.transform(placeDao.findByMember(self));
        return new ResponseWrapper<Collection<PlaceModel>>(places);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="self/sources", method=RequestMethod.GET, produces=Constants.JSON_CONTENT)
    public @ResponseBody ResponseWrapper<List<SourceModel>> getSources(
            @RequestParam(value="url", required=false) String url) {
        Member self = Member.currentMember();

        Source source = null;
        if (url != null) {
            source = sourceDao.findByUrl(url);
        }

        List<Source> sources = new ArrayList<Source>();
        for( SourcePlaceList list : placeDao.findSourcePlaceList(self, source)) {
            sources.add(list.getSource());
        }
        return new ResponseWrapper<List<SourceModel>>(SourceModel.transform(sources));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="self/sources/{id}", method=RequestMethod.GET, produces=Constants.JSON_CONTENT)
    public @ResponseBody ResponseWrapper<SourceModel> getSource(@PathVariable("id") int id) {
        Member self = Member.currentMember();
        Source source = sourceDao.fetch(id);
        if (placeDao.findSourcePlaceList(self, source).isEmpty()) {
            throw new ResourceNotFoundException("Source " + id + " not associated with member " + self.getUsername());

        }
        return new ResponseWrapper<SourceModel>(new SourceModel(source));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="self/sources", method=RequestMethod.POST, produces=Constants.JSON_CONTENT)
    public @ResponseBody ResponseWrapper<SourceModel>  addSource(@RequestParam(value="url", required=true) String url) {
        Member self = Member.currentMember();
        Source source = sourceDao.findByUrl(url);
        if (source == null) {
            // DSDXXX this is a new source, need to handle this
            throw new RuntimeException("addSource not implemented for new sources");
        } else {
            memberService.addSource(self, source);
        }
        return new ResponseWrapper<SourceModel>(new SourceModel(source));
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @RequestMapping(value="self/sources/{id}", method=RequestMethod.DELETE, produces=Constants.JSON_CONTENT)
    public void deleteSource(@PathVariable("id") int id) {
        Member self = Member.currentMember();
        Source source = sourceDao.fetch(id);
        memberService.removeSource(self, source);
    }





}

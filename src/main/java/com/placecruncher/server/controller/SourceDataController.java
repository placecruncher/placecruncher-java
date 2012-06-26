package com.placecruncher.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.placecruncher.server.application.SanFranciscoSourceData;

@Controller
@RequestMapping("/api/private/v1/sourcedata")
public class SourceDataController {
    
    @Autowired
    public SanFranciscoSourceData sanFranciscoSourceData;
    
    @RequestMapping(method = RequestMethod.GET, value = "populate")
    @ResponseBody
    public ResponsePayload self() {

        Meta meta = new Meta();
        ResponsePayload responsePayload = new ResponsePayload(meta);
            
        sanFranciscoSourceData.populate();
        
        return responsePayload;
    }

}

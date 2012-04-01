package com.placecruncher.server.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/private/v1/test")
public class TestPrivateController {

    private static final Logger LOGGER = Logger.getLogger(TestPrivateController.class);

    @RequestMapping(method = RequestMethod.GET, value = "")
    @ResponseBody public ResponsePayload token() {
        Meta meta = new Meta();
        ResponsePayload responsePayload = new ResponsePayload(meta);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("token");
        }
        return responsePayload;
    }
}

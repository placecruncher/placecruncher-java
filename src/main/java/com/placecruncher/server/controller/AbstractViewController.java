package com.placecruncher.server.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.placecruncher.server.dao.ApiKeyDao;
import com.placecruncher.server.domain.ApiKey;

@Controller
public class AbstractViewController {
    private final Logger log = Logger.getLogger(this.getClass());

    @Value("${webclient.key}")
    private String webClientKey;

    @Autowired
    private ApiKeyDao apiKeyDao;

    private ApiKey apiKey;

    protected ApiKey getApiKey() {
        // TODO find a better way to initialize the key or maybe just hardcode it from the properties.
        if (apiKey == null ) {
            this.apiKey = apiKeyDao.findByApiKey(webClientKey);
            log.info("Loaded Web Client Key " + webClientKey + " " + apiKey);
        }
        return apiKey;
    }



}

package com.placecruncher.server.controller;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable(dependencyCheck = true)
public class ResponseContainer {
    @Autowired
    private ObjectMapper objectMapper;

    private Meta meta;
    private Map<String, Object> response;

    public Meta getMeta() {
        return meta;
    }
    public void setMeta(Meta meta) {
        this.meta = meta;
    }
    public void setResponse(Map<String, Object> response) {
        this.response = response;
    }

    public <T> T getResponse(Class<T> clazz) {
        return objectMapper.convertValue(response, clazz);
    }

}


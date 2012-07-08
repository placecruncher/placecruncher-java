package com.placecruncher.server.controller;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.placecruncher.server.test.ApiClientRequestContext;

public class ApiTestCase extends IntegrationTestCase {
    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected ApiClientRequestContext requestContext;

    @SuppressWarnings("unchecked")
    private <T> T handleResponse(ResponseContainer container, Class<T> responseType) {
        if (ResponseContainer.class.isAssignableFrom(responseType)) {
            return (T)container;
        } else if (Meta.class.isAssignableFrom(responseType)) {
            return (T)container.getMeta();
        } else {
            Assert.assertEquals(HttpStatus.SC_OK, container.getMeta().getCode());
            return container.getResponse(responseType);
        }
    }

    protected <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) {
        ResponseContainer container = restTemplate.postForObject(getBaseUrl() + url, request, ResponseContainer.class, uriVariables);
        return handleResponse(container, responseType);
    }

    protected <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) {
        ResponseContainer container = restTemplate.getForObject(getBaseUrl() + url, ResponseContainer.class, uriVariables);
        return handleResponse(container, responseType);
    }

    protected void delete(String url, Object... uriVariables) {
        restTemplate.delete(getBaseUrl() + url, uriVariables);
    }

    protected void put(String url, Object request, Object... uriVariables) {
        restTemplate.put(getBaseUrl() + url, request, uriVariables);
    }

}

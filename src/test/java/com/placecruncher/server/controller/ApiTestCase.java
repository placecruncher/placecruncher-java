package com.placecruncher.server.controller;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import com.placecruncher.server.application.data.SecurityTestData;
import com.placecruncher.server.test.ApiClientRequestContext;

public class ApiTestCase extends IntegrationTestCase {
    protected static final String PRIVATE_API = "/api/private/v1";

    private String key = SecurityTestData.TEST_KEY;
    private String secret = SecurityTestData.TEST_SECRET;

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected ApiClientRequestContext requestContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setupRequestContext() {
        requestContext.setKey(key);
        requestContext.setSecret(secret);
        requestContext.setClient("IPHONE:3,1:pnid:1:3");
    }
    @After
    public void teardownReqeustContext() {
        requestContext.clear();
    }

    @SuppressWarnings("unchecked")
    private <T> T handleResponse(ResponseContainer container, TypeReference<T> responseType) {
      if (responseType.getType() instanceof Class) {
        Class<T> clazz = (Class<T>)responseType.getType();
        if (ResponseContainer.class.isAssignableFrom(clazz)) {
            return (T)container;
        } else if (Meta.class.isAssignableFrom(clazz)) {
            return (T)container.getMeta();
        } else {
            Assert.assertEquals(HttpStatus.SC_OK, container.getMeta().getCode());
            return objectMapper.convertValue(container.getResponse(), clazz);
        }
      } else {
        Assert.assertEquals(HttpStatus.SC_OK, container.getMeta().getCode());
        return objectMapper.convertValue(container.getResponse(), responseType);
      }

    }

    @SuppressWarnings("unchecked")
    private <T> T handleResponse(ResponseContainer container, Class<T> responseType) {
        if (ResponseContainer.class.isAssignableFrom(responseType)) {
            return (T)container;
        } else if (Meta.class.isAssignableFrom(responseType)) {
            return (T)container.getMeta();
        } else {
            Assert.assertEquals(HttpStatus.SC_OK, container.getMeta().getCode());
            return objectMapper.convertValue(container.getResponse(), responseType);
        }
    }

    protected <T> HttpEntity<T> postForEntity(String url, Object request, Class<T> responseType, Object... uriVariables) {
        return restTemplate.postForEntity(getBaseUrl() + url, request, responseType, uriVariables);
    }

    protected <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) {
        ResponseContainer container = restTemplate.postForObject(getBaseUrl() + url, request, ResponseContainer.class, uriVariables);
        return handleResponse(container, responseType);
    }

    protected <T> T getForObject(String url, TypeReference<T> responseType, Object... uriVariables) {
        ResponseContainer container = restTemplate.getForObject(getBaseUrl() + url, ResponseContainer.class, uriVariables);
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

    protected String login(String username, String password) {
        AuthenticationPayload request = new AuthenticationPayload(username, password);
        String token = postForObject(PRIVATE_API + "/members/self/token", request, SessionTokenWrapper.class).getToken();
        // DSDXXX Need to decide how to handle error codes for failed login attempts and how to set the request context
        requestContext.setToken(token);
        return token;
    }

    protected void logout() {
        requestContext.clearToken();
    }

    protected String loginAsAdmin() {
        return login(SecurityTestData.ADMIN_USERNAME, SecurityTestData.ADMIN_PASSWORD);
    }

    protected String loginAsMember() {
        return login(SecurityTestData.MEMBER_USERNAME, SecurityTestData.MEMBER_PASSWORD);
    }



}

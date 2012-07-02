package com.placecruncher.server.controller;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:appContext-integration-test.xml" })
public class SourceControllerIT {
    private final Logger log = Logger.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl;

    @Before
    public void login() {
        restTemplate.postForLocation(baseUrl + "j_spring_security_check?j_username=admin&j_password=secret", null);
    }

    @Value("#{ systemProperties['base.url']?:'http://localhost:8080/placecruncher/' }")
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        return headers;
    }

    @Test
    public void getPlaces() {
        List<PlaceModel> places = Arrays.asList(restTemplate.getForObject("http://localhost:8080/placecruncher/site/sources/0/places", PlaceModel[].class));
        for (PlaceModel place : places) {
            log.info("Found place " + place);
        }
    }

    @Test
    public void homePage() {
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "site/sources/list.html", HttpMethod.GET, request, null);
        log.debug(response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void apiCall() {
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "api/private/v1/members/self", HttpMethod.GET, request, null);
        log.debug(response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}

package com.placecruncher.server.controller;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SourceControllerIT extends ApiTestCase {
    private final Logger log = Logger.getLogger(getClass());

    @Test
    public void dummy() {
        log.debug("dummy test case");
    }
//    @Test
//    public void getPlaces() {
//        List<PlaceModel> places = Arrays.asList(restTemplate.getForObject("http://localhost:8080/placecruncher/api/private/v1/sources/0/places", PlaceModel[].class));
//        for (PlaceModel place : places) {
//            log.info("Found place " + place);
//        }
//    }
//
//    @Test
//    public void apiCall() {
//        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
//        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "api/private/v1/members/self", HttpMethod.GET, request, null);
//        log.debug(response.getBody());
//        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }

}

package com.placecruncher.server.controller;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
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
    private final RestTemplate restTemplate = new RestTemplate();

    private String baseUrl;

    @Value("#{ systemProperties['base.url']?:'http://localhost:8080/placecruncher/' }")
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        return headers;
    }

    @Test
    public void homePage() {
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "site/sources/list.html", HttpMethod.GET, request, null);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}

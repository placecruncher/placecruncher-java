package com.placecruncher.server.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:appContext-integration-test.xml" })
public abstract class IntegrationTestCase {
    protected String baseUrl;

    @Value("#{ systemProperties['base.url']?:'http://localhost:8080/placecruncher' }")
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }


}

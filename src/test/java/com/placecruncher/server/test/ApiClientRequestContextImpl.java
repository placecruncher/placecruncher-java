package com.placecruncher.server.test;

import com.placecruncher.server.application.data.SecurityTestData;

/**
 * Thread local used to store the client context to be passed in headers.
 */
public class ApiClientRequestContextImpl implements ApiClientRequestContext {
    private String key = SecurityTestData.TEST_KEY;
    private String secret = SecurityTestData.TEST_SECRET;
    private String token;
    private String client;

    public void clear() {
        this.key = "";
        this.secret = "";
        this.token = "";
        this.client = "";
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void clearToken() {
        this.token = "";
    }

    public String getClient() {
        return client;
    }
    public void setClient(String client) {
        this.client = client;
    }

}

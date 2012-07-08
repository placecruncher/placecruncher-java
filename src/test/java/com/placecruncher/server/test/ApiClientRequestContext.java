package com.placecruncher.server.test;

/**
 * Thread local used to store the client context to be passed in headers.
 */
public interface ApiClientRequestContext {
    String getKey();
    void setKey(String key);

    String getSecret();
    void setSecret(String secret);

    String getToken();
    void setToken(String token);
    void clearToken();

    String getClient();
    void setClient(String client);

    void clear();
}

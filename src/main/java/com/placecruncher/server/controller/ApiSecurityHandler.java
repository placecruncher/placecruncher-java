package com.placecruncher.server.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.placecruncher.server.application.Constants;
import com.placecruncher.server.dao.ApiKeyDao;
import com.placecruncher.server.domain.ApiKey;
import com.placecruncher.server.exception.PlacecruncherRuntimeException;

public class ApiSecurityHandler extends HandlerInterceptorAdapter {
    protected static final Logger LOGGER = Logger.getLogger(ApiSecurityHandler.class);

    private int timestampLeniencySeconds = 60 * 60;

    @Autowired
    private ApiKeyDao apiKeyDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        boolean result = false;

        try {

            String key = request.getHeader(Constants.X_API_KEY);
            String timeStamp = request.getHeader(Constants.X_API_TIMESTAMP);
            String signature = request.getHeader(Constants.X_API_SIGNATURE);

            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(signature) && StringUtils.isNotEmpty(timeStamp)) {

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("key: " + key + " signature: " + signature + " timeStamp: " + timeStamp);
                }

                ApiKey apiKey = apiKeyDao.findByApiKey(key);

                if (apiKey != null) {
                    result = apiKey.validate(key, signature, timeStamp, timestampLeniencySeconds);
                } else {
                    LOGGER.debug("key " + key + " NOT FOUND");
                }
            }

            if (!result) {
                sendErrorResponse(response, "{ \"meta\": {  \"code\": 401, \"errorCode\":0, \"errorMessage\":\"Invalid Api Check\" }, \"response\": { } }");
            }
        } catch (Exception e) {
            throw new PlacecruncherRuntimeException(e);
        }
        return result;
    }

    protected void sendErrorResponse(ServletResponse response, String jsonMessage) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(jsonMessage);
    }
}

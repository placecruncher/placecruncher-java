package com.placecruncher.server.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.placecruncher.server.application.InvokerContext;
import com.placecruncher.server.dao.PrincipalDao;
import com.placecruncher.server.domain.Principal;

public class PrincipalHanlderInterceptor extends HandlerInterceptorAdapter {
    protected static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ApiSecurityHandler.class);

    @Autowired
    private PrincipalDao principalDao;
    
    @Autowired
    private InvokerContext invokerContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        boolean result = true;

        try {

            String token = request.getHeader("Authentication");

            if (StringUtils.isNotEmpty(token)) {

                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("token: " + token);
                }

                Principal principal = principalDao.findByToken(token);
                invokerContext.setPrincipal(principal);
            }
        } catch (Exception e) {
            LOGGER.error(e, e);
        }
        return result;
    }

    protected void sendErrorResponse(ServletResponse response, String jsonMessage) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(jsonMessage);
    }
}

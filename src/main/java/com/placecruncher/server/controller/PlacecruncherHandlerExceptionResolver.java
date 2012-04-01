package com.placecruncher.server.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class PlacecruncherHandlerExceptionResolver implements HandlerExceptionResolver {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(PlacecruncherHandlerExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Object handler, Exception exception) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Resolve Exception exception:" + exception);
        }
        LOGGER.error(exception, exception);
        try {
            servletResponse.setContentType("application/json");
            PrintWriter out = servletResponse.getWriter();

            String exceptionMessage = "";
            String exceptionCode = "";

            exceptionMessage = exception.toString();
            exceptionCode = Integer.toString(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            String resultString = "{ \"meta\": {  \"code\": 500, \"errorCode\":" + exceptionCode + ", \"errorMessage\":\"" + exceptionMessage + "\" } }";

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("resultString:" + resultString);
            }

            servletResponse.setStatus(HttpServletResponse.SC_OK);
            out.println(resultString);
            out.flush();
            // CHECKSTYLE:OFF IllegalCatch
        } catch (Exception e) { // NOPMD - Exception is not appreciated by PMD,
                                // but I had to do this to trap all exit paths.
            LOGGER.error(e, e);
        }
        // CHECKSTYLE:ON IllegalCatch
        return null;
    }
}

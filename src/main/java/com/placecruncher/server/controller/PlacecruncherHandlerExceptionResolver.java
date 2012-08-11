package com.placecruncher.server.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.placecruncher.server.exception.ResourceNotFoundException;

public class PlacecruncherHandlerExceptionResolver implements HandlerExceptionResolver {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(PlacecruncherHandlerExceptionResolver.class);

    private void writeJsonResponse(HttpServletRequest servletRequest,
            HttpServletResponse servletResponse,
            int statusCode,
            int errorCode,
            String message) {
        // DSDXXX change this to use MAV instead of writing to the response directly
        try {
            servletResponse.setContentType("application/json");
            PrintWriter out = servletResponse.getWriter();

            String resultString = "{ \"meta\": {  \"code\": " + statusCode + ", \"errorCode\":" + errorCode + ", \"errorMessage\":\"" + message + "\" } }";

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("resultString:" + resultString);
            }

            servletResponse.setStatus(HttpServletResponse.SC_OK);
            out.println(resultString);
            out.flush();
        } catch (Exception e) {
            LOGGER.error(e, e);
        }
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {

        // Spring Security exception need to pass through.  This is dumb, but as far as I can
        // tell this is a known issue with custom resolvers that implement blanket exception
        // reporting instead of only handling specific exceptions from the web layer.
        // See: https://jira.springsource.org/browse/SPR-5193

        if (AccessDeniedException.class.isAssignableFrom(exception.getClass())) {
            return null;
        }

        LOGGER.error(exception, exception);

        if (exception instanceof ObjectRetrievalFailureException ||
            exception instanceof ResourceNotFoundException) {
            writeJsonResponse(request, response,
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.value(),
                    exception.getMessage());
        } else {
            writeJsonResponse(request, response,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + exception.getMessage());
        }
        return null;
    }
}

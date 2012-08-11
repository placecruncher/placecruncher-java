package com.placecruncher.server.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * This class provides the appropriate response when a user is denied access
 * to the REST API through a Spring Security constraint.
 *
 */
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException, ServletException {

        // DSDXXX We should be able to use a JacksonView here and return JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String exceptionMessage = "";
        String exceptionCode = "";

        exceptionMessage = exception.toString();
        exceptionCode = Integer.toString(HttpServletResponse.SC_FORBIDDEN);

        String resultString = "{ \"meta\": {  \"code\": 500, \"errorCode\":" + exceptionCode + ", \"errorMessage\":\"" + exceptionMessage + "\" } }";

        response.setStatus(HttpServletResponse.SC_OK);
        out.println(resultString);
        out.flush();

    }

}

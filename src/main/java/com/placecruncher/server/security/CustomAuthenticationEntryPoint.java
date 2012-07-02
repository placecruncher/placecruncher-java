package com.placecruncher.server.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println("{ \"meta\": {  \"code\": 401, \"errorCode\":0, \"errorMessage\":\"Invalid User Auth\" }, \"response\": { } }");
    }
}
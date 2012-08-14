package com.placecruncher.server.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.util.WebUtils;

import com.placecruncher.server.domain.MemberDetails;

public class CookieRememberMeService implements RememberMeServices, LogoutHandler {
    private static final String COOKIE = "X-app-authorize";
    private final Logger log = Logger.getLogger(this.getClass());

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie((HttpServletRequest) request, COOKIE);
        if (cookie != null) {
            log.info("Auto Login not implemented " + cookie);
        }
        return null;
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {
        log.info("Login Failure");
        Cookie cookie = new Cookie(COOKIE, "");
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("Login Success");
        MemberDetails memberDetails = (MemberDetails)authentication.getPrincipal();
        Cookie cookie = new Cookie(COOKIE, memberDetails.getToken());
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie cookie = new Cookie(COOKIE, null);
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}

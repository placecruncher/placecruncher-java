package com.placecruncher.server.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.WebUtils;

import com.placecruncher.server.dao.MemberDao;
import com.placecruncher.server.domain.Member;

public class CookieAuthenticationFilter extends GenericFilterBean {
    private final Logger log = Logger.getLogger(this.getClass());

    private static final String AUTH_COOKIE = "X-app-authorize";

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Cookie cookie = WebUtils.getCookie((HttpServletRequest) request, AUTH_COOKIE);
        if (cookie != null) {
            String accessToken = cookie.getValue();

            if (log.isDebugEnabled()) {
                log.debug("Checking access token " + accessToken + " for request " + ((HttpServletRequest)request).getRequestURL());
            }

            // Validate the access token and lookup the associated user
            Member member = memberDao.findByToken(accessToken);

            if (member != null) {
                log.info("Authenticated " + member.getUsername() + " via access token " + accessToken);
                Authentication authentication = new PreAuthenticatedAuthenticationToken(member, accessToken);
                setSecurityContext(authentication);
            } else {
                log.error("No member associated with cookie value " + accessToken);
                cookie.setValue("");
                cookie.setMaxAge(0);
                ((HttpServletResponse)response).addCookie(cookie);
                throw new PreAuthenticatedCredentialsNotFoundException("Non-existent authentication token");
            }
        }
        chain.doFilter(request, response);
    }

    private void setSecurityContext(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authentication, this.getClass()));
        }
    }
}

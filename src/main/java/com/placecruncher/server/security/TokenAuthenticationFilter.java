package com.placecruncher.server.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.web.util.WebUtils;

import com.placecruncher.server.dao.MemberDao;
import com.placecruncher.server.domain.Member;

public class TokenAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {
    private final Logger log = Logger.getLogger(this.getClass());

    private static final String AUTH_COOKIE = "X-app-authorize";

    private static final String AUTH_HEADER = "Authentication";

    @Autowired
    private MemberDao memberDao;

    private String getAccessToken(HttpServletRequest request) {
        String accessToken = null;

        Cookie cookie = WebUtils.getCookie(request, AUTH_COOKIE);
        if (cookie != null) {
            accessToken = cookie.getValue();
            log.debug("Cookie '" + AUTH_COOKIE + "' contains access token '" + accessToken + "'");
        }

        String header = (request).getHeader(AUTH_HEADER);
        if (header != null) {
            accessToken = header;
            log.debug("Header '" + AUTH_HEADER + "' contains access token '" + accessToken + "'");
        }

        return accessToken;
    }

    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return getAccessToken(request);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String accessToken = getAccessToken(request);
        if (accessToken != null) {

            if (log.isDebugEnabled()) {
                log.debug("Checking access token " + accessToken + " for request " + ((HttpServletRequest)request).getRequestURL());
            }

            // Validate the access token and lookup the associated user
            Member member = memberDao.findByToken(accessToken);

            if (member != null) {
                log.info("Authenticated " + member.getUsername() + " via access token " + accessToken);
                return member.getUsername();
            } else {
                log.error("No member associated with token value " + accessToken);
                throw new PreAuthenticatedCredentialsNotFoundException("Non-existent authentication token '" + accessToken + "'");
            }
        }
        return null;
    }

}

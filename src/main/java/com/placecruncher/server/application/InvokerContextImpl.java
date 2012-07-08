package com.placecruncher.server.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.placecruncher.server.domain.ApiKey;
import com.placecruncher.server.domain.Member;

/**
 * Dummy InvokerContext that basically shows the user 1 is always logged in on
 * this thread at the moment
 *
 * @author djones
 *
 */
public class InvokerContextImpl implements InvokerContext {
    private static final Logger LOGGER = Logger.getLogger(InvokerContextImpl.class);

    protected ApiKey apiKey;
    protected String os;
    protected String osVersion;
    protected String pushNotificationId;
    protected String uuid;
    protected String phoneModel;
    protected Member member;

    public void initInvokerContext(HttpServletRequest request, HttpServletResponse response) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("initInvokerContext");
        }
        this.clear();

        String appClientString = request.getHeader(Constants.X_APP_CLIENT);

        if (StringUtils.isNotEmpty(appClientString)) {
            parseAppClientString(appClientString.trim());
        }
    }

    // <OS>:<OS Version>:<Push Notification ID>:<UUID>:<Phone Model>
    private void parseAppClientString(String appClientString) {
        String[] appClientInfo = appClientString.split(":");
        if (appClientInfo.length > 0) {
            os = appClientInfo[0].trim();
        }
        if (appClientInfo.length > 1) {
            osVersion = appClientInfo[1].trim();
        }
        if (appClientInfo.length > 2) {
            pushNotificationId = appClientInfo[2].trim();
        }
        if (appClientInfo.length > 3) {
            uuid = appClientInfo[3].trim();
        }
        if (appClientInfo.length > 4) {
            phoneModel = appClientInfo[4].trim();
        }
    }

    public void clear() {
        apiKey = null; // NOPMD This assignment clears the context for the next
                       // user.
        os = null; // NOPMD This assignment clears the context for the next
                   // user.
        osVersion = null; // NOPMD This assignment clears the context for the
                          // next user.
        pushNotificationId = null; // NOPMD This assignment clears the context
                                   // for the next user.;
        uuid = null; // NOPMD This assignment clears the context for the next
                     // user.
        phoneModel = null; // NOPMD This assignment clears the context for the
                           // next user.
        member = null; // NOPMD This assignment clears the context for the
                          // next user.;
    }

    public String getOs() {
        return os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getPushNotificationId() {
        return pushNotificationId;
    }

    public String getUuid() {
        return uuid;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    @Override
    public Member getMember() {
        return member;
    }

    @Override
    public void setMember(Member member) {
        this.member = member;
    }


}

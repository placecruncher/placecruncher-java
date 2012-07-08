package com.placecruncher.server.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import com.placecruncher.server.application.Constants;

public class ApiClientRequestFactory extends HttpComponentsClientHttpRequestFactory {
    private final Logger log = Logger.getLogger(getClass());

    @Autowired
    private ApiClientRequestContext requestContext;

    private DateTimeFormatter timestampFormatter = ISODateTimeFormat.basicDateTimeNoMillis();


    @Override
    protected void postProcessHttpRequest(HttpUriRequest request) {
        // curl -v -X POST -H "Content-Type: application/json"
        // -d '{"userName":"admin","password":"secret"}'
        // -H"X-API-key:abc123" -H"X-API-Timestamp:$(date '+%Y%m%dT%H%M%S-0700')"
        // -H"X-API-Signature:$(echo -n $(date '+%Y%m%dT%H%M%S-0700.secret')|sha256sum)
        // " http://localhost:8080/placecruncher/api/private/v1/members/self/token


        DateTime now = new DateTime();
        String timestamp = timestampFormatter.print(now);
        request.addHeader(Constants.X_API_TIMESTAMP, timestamp);

        if (!request.containsHeader(Constants.X_API_KEY) && StringUtils.isNotBlank(requestContext.getKey())) {
            request.addHeader(Constants.X_API_KEY, requestContext.getKey());
        }

        if (!request.containsHeader(Constants.X_API_SIGNATURE) && StringUtils.isNotBlank(requestContext.getSecret())) {
            String signature = DigestUtils.sha256Hex(timestamp + "." + requestContext.getSecret());
            request.addHeader(Constants.X_API_SIGNATURE, signature);
        }

        if (!request.containsHeader(Constants.AUTHENTICATION) && StringUtils.isNotBlank(requestContext.getToken())) {
            request.addHeader(Constants.AUTHENTICATION, requestContext.getToken());
        }

        if (log.isDebugEnabled()) {
            log.debug("Creating HTTP request " + request.getMethod() + " " + request.getURI() + ", " +
                    dumpHeaders(request, Constants.X_API_KEY) + ", " +
                    dumpHeaders(request, Constants.X_API_TIMESTAMP) + ", " +
                    dumpHeaders(request, Constants.X_API_SIGNATURE) + ", " +
                    dumpHeaders(request, Constants.AUTHENTICATION));
        }
    }

    private String dumpHeaders(HttpUriRequest request, String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("=[");
        boolean first = true;
        for (Header header : request.getHeaders(name)) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(header.getValue());
        }
        sb.append(']');
        return sb.toString();
    }

}

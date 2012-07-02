package com.placecruncher.server.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import com.placecruncher.server.application.data.SecurityTestData;

public class ApiClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {
    private final Logger log = Logger.getLogger(getClass());


    private DateTimeFormatter timestampFormatter = ISODateTimeFormat.basicDateTimeNoMillis();

    private String key = SecurityTestData.TEST_KEY;
    private String secret = SecurityTestData.TEST_SECRET;

    public void setApiKey(String key) {
        this.key = key;
    }

    public void setApiSecret(String secret) {
        this.secret = secret;
    }

    @Override
    protected void postProcessHttpRequest(HttpUriRequest request) {
        // curl -v -X POST -H "Content-Type: application/json"
        // -d '{"userName":"admin","password":"secret"}'
        // -H"X-API-key:abc123" -H"X-API-Timestamp:$(date '+%Y%m%dT%H%M%S-0700')"
        // -H"X-API-Signature:$(echo -n $(date '+%Y%m%dT%H%M%S-0700.secret')|sha256sum)
        // " http://localhost:8080/placecruncher/api/private/v1/members/self/token

        DateTime now = new DateTime();
        String timestamp = timestampFormatter.print(now);
        String signature = DigestUtils.sha256Hex(timestamp + ".xx" + secret);

        request.addHeader("X-API-Timestamp", timestamp);
        request.addHeader("X-API-Key", key);
        request.addHeader("X-API-Signature", signature);

    }

}

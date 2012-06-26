package com.placecruncher.server.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.placecruncher.server.application.ApplicationService;
import com.placecruncher.server.domain.Device;
import com.placecruncher.server.domain.Notification;


@Service
public class UrbanAirshipPushNotificationService {
    /**
     * The Authrization HTTP header
     * (http://tools.ietf.org/html/draft-ietf-oauth-v2-12#section-2.2)
     */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final Logger log = Logger.getLogger(this.getClass());

    /**
     * The 'Basic' tag for basic authorization
     * (http://tools.ietf.org/html/draft-ietf-oauth-v2-12#section-2.2)
     */
    private static final String BASIC_AUTH = "Basic";

    private static final String BASE_URL = "https://go.urbanairship.com/api/";
    private static final String PUSH_URL = BASE_URL + "push/";

    @Value("${urbanairship.key}")
    private String apiKey;

    @Value("${urbanairship.master.secret}")
    private String apiSecret;

    @Value("${urbanairship.debug}")
    private boolean debug;

    @Autowired
    private ApplicationService applicationService;

    private final RestTemplate restTemplate = new RestTemplate();

    // {"generalHost" : "http://inqblast/.......", "partitionedHost" :
    // "http://inqblast........", "apiPath" : "/self/notify...........",
    // "timestamp": "<timestamp of when the message was sent>", "notificationId"
    // : "............" }
    public boolean pushNotificationToAndroid(Notification notification, Device device) {

        Map<String, Object> body = new HashMap<String, Object>();
        List<String> apidList = new ArrayList<String>();
        apidList.add(device.getToken());
        body.put("apids", apidList);

        Map<String, Object> android = new HashMap<String, Object>();
        Map<String, String> extra = new HashMap<String, String>();

        body.put("android", android);
        android.put("alert", notification.getId().toString());

        extra.put("generalHost", applicationService.getBaseUrl());
        extra.put("partitionedHost", applicationService.getPartitionedHost());
        extra.put("apiPath", "/api/v1/notifications/");

        DateTime currentTime = DateTime.now();

        DateTimeFormatter parser = ISODateTimeFormat.basicDateTimeNoMillis();
        String updateTime = parser.print(currentTime);

        extra.put("timestamp", updateTime);
        extra.put("notificationId", notification.getId().toString());

        android.put("extra", extra);

        log.info("extra: " + extra.toString());
        log.info("android: " + android.toString());

        JSONObject jsonBody = new JSONObject(body);
        return restPush(jsonBody.toString());

    }

    public boolean pushAlertToAndroid(String alert, Device device) {
        boolean result = false;
        try {
            JSONObject body = new JSONObject();
            JSONArray tokens = new JSONArray();
            tokens.put(device.getToken());
            body.put("apids", tokens);

            JSONObject andriod = new JSONObject();
            body.put("android", andriod);

            if (StringUtils.isNotBlank(alert)) {
                andriod.put("alert", alert);
            }

            String payload = body.toString();

            result = restPush(payload);
        } catch (JSONException e) {
            log.error(e, e);
        }
        return result;
    }
    private void setRequestAuthorization(HttpHeaders requestHeaders) {
        String aut = apiKey + ":" + apiSecret;
        String auth = new String(Base64.encode(aut.getBytes()), Charset.forName("UTF-8"));
        requestHeaders.set(AUTHORIZATION_HEADER, BASIC_AUTH + " " + auth);
    }

    private HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        setRequestAuthorization(headers);
        return headers;
    }

    private boolean restPush(String payload) {
        boolean result = false;
        if (log.isInfoEnabled()) {
            log.info("Sending push notification " + payload);
        }

        try {
            HttpHeaders headers = getDefaultHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<?> request = new HttpEntity<String>(payload, headers);

            if (!debug) {
                restTemplate.exchange(PUSH_URL, HttpMethod.POST, request, null);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("debug turned on so no push notification " + payload);
                }
            }

            result = true;

        } catch (RestClientException e) {
            log.error(e, e);
        }
        return result;
    }
}


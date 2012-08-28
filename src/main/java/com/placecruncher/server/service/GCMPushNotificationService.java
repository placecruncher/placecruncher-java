// Copyright 2011 INQ Mobile. All rights reserved.
package com.placecruncher.server.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.io.IOUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;


@Service
public class GCMPushNotificationService {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(GCMPushNotificationService.class);

    private static final DateTimeFormatter PARSER = ISODateTimeFormat.basicDateTimeNoMillis();

    private static final String UTF8 = "UTF-8";

    @Value("${gcm.projectId}")
    private String projectId;

    @Value("${gcm.apiSecret}")
    private String apiSecret;

    @Value("${gcm.endpointUrl}")
    private String gcmUrl;

    @Value("${gcm.connectionTimeout}")
    private int connectionTimeout;

    @Value("${gcm.readTimeout}")
    private int readTimeout;

    public boolean sendMessage(String message, String token) {

        //Map<String, String> messageMap = new HashMap<String, String>();
        //messageMap.put("message", message);
        //JSONObject body = new JSONObject(messageMap);
        //return sendMessage(token, body, "generic");
        return sendTestMessage(token, message, "generic");
    }

    /**
     * set up the connection we use with standard settings.
     * @param connectUrl
     * @param dataLength
     * @return
     * @throws IOException
     */
    private HttpsURLConnection setupConnection(String connectUrl, int dataLength) throws IOException {
        URL url = new URL(connectUrl);

        HttpsURLConnection.setDefaultHostnameVerifier(new CustomizedHostnameVerifier());
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Content-Length", Integer.toString(dataLength));
        conn.setRequestProperty("Authorization", "key=" + apiSecret);

        conn.setConnectTimeout(connectionTimeout);
        conn.setReadTimeout(readTimeout);
        return conn;
    }


    /**
     * send a Google Cloud message to device specified by given token, with data of jsonBody, with collapseKey given
     * @param token - associated token generated on handset and sent up.  Required parameter
     * @param jsonBody - json body to use in message.  Optional.  4k data max
     * @param collapseKey - optional.  For messages that can be collapsed into a single notify, use this.  Only 4 keys allowed per device.
     * @return
     */
    public boolean sendMessage(String token, JSONObject jsonBody, String collapseKey) {

        boolean result = false;
        OutputStream out = null;
        HttpsURLConnection conn = null;
        InputStream responseStream = null;
        try {
            JSONObject request = new JSONObject();
            request.put("registration_ids", new JSONArray().put(token));
            if (jsonBody != null) {
                request.put("data", jsonBody);
            }
            if (collapseKey != null) {
                request.put("collapse_key", collapseKey);
            }
            //request.put("delay_while_idle", true);
            //request.put("time_to_live", 0);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Sending post data: " + request.toString() + "\n project id: " + projectId);
            }
            byte[] postData = request.toString().getBytes(UTF8);

            if (gcmUrl != null) {
                conn = setupConnection(gcmUrl, postData.length);
                out = conn.getOutputStream();
                out.write(postData);

                int responseCode = 0;
                // wrap the input stream access in try/catch, so we can grap the error stream instead if it fails.
                try {
                    responseStream = conn.getInputStream();
                } catch (IOException ioe) {
                    // some error codes (401) will trigger io exception
                    LOGGER.warn("got io exception getting input: " + ioe.getMessage());
                    responseStream = conn.getErrorStream();
                }
                responseCode = conn.getResponseCode();
                String responseMessage = conn.getResponseMessage();
                String responseData = IOUtils.toString(responseStream, UTF8);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // May still have useful data in response to handle different stuff
                    // Specifically, the result can be OK + failure

                    // I added in some testing to see what happens on a bad reg id.
                    // eg: 2012-07-12 11:16:03,168 [http-bio-8080-exec-1] INFO  com.inqmobile.inqcloud.service.GCMPushNotificationService - Sent message. code/string: 200 / OK data
                    // {"multicast_id":4645818202832724878,"success":0,"failure":1,"canonical_ids":0,"results":[{"error":"InvalidRegistration"}]}
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Sent message. code/string: " + responseCode + " / " + responseMessage + " data\n" + responseData);
                    }
                    JSONObject responseJson = new JSONObject(responseData);
                    // we can handle this simply, because we only are making one registration id at a time.
                    // thus checking the failure count == 0 or missing suffices.

                    /*      Here are JSON results for 6 recipients (IDs 4, 8, 15, 16, 23, and 42 respectively) with 3 messages successfully processed, 1 canonical registration ID returned, and 3 errors:
                            { "multicast_id": 216,
                              "success": 3,
                              "failure": 3,
                              "canonical_ids": 1,
                              "results": [
                                { "message_id": "1:0408" },
                                { "error": "Unavailable" },  // TODO: Should be resent.  Not clear from docs exactly what that means
                                { "error": "InvalidRegistration" },  // TODO: odd, happens if the id is just corrupt.
                                { "message_id": "1:1516" },
                                { "message_id": "1:2342", "registration_id": "32" }, // TODO: handle cannonical id response. http://developer.android.com/guide/google/gcm/adv.html#canonical
                                { "error": "NotRegistered"}  // TODO: Handle NotRegistered response, when a handset is no longer using this app.
                              ]
}                     */
                    if (responseJson.has("failure")) {
                        int failureCount = responseJson.getInt("failure");
                        if (failureCount == 0) {
                            result = true;
                        } else {
                            LOGGER.warn("reponse json: " + responseJson + " failure count " + failureCount);
                        }
                    } else {
                        result = true;
                    }
                } else {
                    // 400 - json format incorrect.
                    // 401 - There was an error authenticating the sender account
                    // 500 - There was an internal error in the GCM server while trying to process the request.
                    // 503 - Indicates that the server is temporarily unavailable (i.e., because of timeouts, etc ) TODO: add retry logic, has rules
                    LOGGER.error("Failed to send message. code/string: " + responseCode + " / " + responseMessage + " data\n" + responseData);
                    // consider logging some other problem?
                }

            }
            // CHECKSTYLE:OFF IllegalCatch
        } catch (Exception e) {
            // CHECKSTYLE:ON IllegalCatch
            result = false;
            // consider logging an error in db?
            LOGGER.error("Gcm mesage send error", e);
        } finally {
            safeClose(out);
            safeClose(conn);
            safeClose(responseStream);
        }
        return result;
    }

    /**
     * ugh, want a function to handle
     * @param ioObject
     * @return
     */
    private boolean safeClose(Object ioObject) {
        boolean result;
        try {
            if (ioObject instanceof OutputStream) {
                ((OutputStream) ioObject).close();
            } else if (ioObject instanceof InputStream) {
                ((InputStream) ioObject).close();
            } else if (ioObject instanceof HttpsURLConnection) {
                ((HttpsURLConnection) ioObject).disconnect();
            }

            result = true;

            // CHECKSTYLE:OFF IllegalCatch
        } catch (Exception e) {
            // CHECKSTYLE:ON IllegalCatch
            LOGGER.error("Error closing object: " + ioObject, e);
            result = false;
        }
        return result;
    }

    private static class CustomizedHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private boolean sendTestMessage(String token, String body, String key) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("sendTestMessage token: " + token + " body: " + body + " key: " + key);
            LOGGER.info("sendTestMessage apiSecret: " + apiSecret);
        }
        Sender sender = new Sender(apiSecret);
        Message message = new Message.Builder().addData("test", "test").collapseKey("key").build();

        try {
            Result result = sender.send(message, token, 5);
            LOGGER.info("errorCodeName: " +  result.getErrorCodeName());
        } catch (IOException e) {
            LOGGER.error(e, e);
        }

        return true;
    }
}

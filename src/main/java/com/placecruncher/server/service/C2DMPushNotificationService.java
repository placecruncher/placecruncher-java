// Copyright 2011 INQ Mobile. All rights reserved.
package com.placecruncher.server.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.placecruncher.server.exception.PlacecruncherRuntimeException;

import java.io.OutputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

@Service
public class C2DMPushNotificationService {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(C2DMPushNotificationService.class);

    private static final String PARAM_REGISTRATION_ID = "registration_id";

    private static final String PARAM_COLLAPSE_KEY = "collapse_key";

    private static final String UTF8 = "UTF-8";

    @Value("${C2DMPushNotificationService.authenticationToken}")
    private String authenticationToken;

    @Value("${C2DMPushNotificationService.googleC2dmUrl}")
    private String googleC2dmUrl;
    
    @Value("${C2DMPushNotificationService.connectionTimeout}")
    private int connectionTimeout;
    
    @Value("${C2DMPushNotificationService.readTimeout}")
    private int readTimeout;
    

    public void sendMessage(String message, String token) {
        OutputStream out = null;
        try {
            StringBuilder postDataBuilder = new StringBuilder();
            postDataBuilder.append(PARAM_REGISTRATION_ID).append("=")
                    .append(token);
            postDataBuilder.append("&").append(PARAM_COLLAPSE_KEY).append("=")
                    .append("0");
            postDataBuilder.append("&").append("data.payload").append("=")
                    .append(URLEncoder.encode(message, UTF8));

            byte[] postData = postDataBuilder.toString().getBytes(UTF8);

            // https://android.clients.google.com/c2dm/send
            URL url = new URL(googleC2dmUrl);
            HttpsURLConnection
                    .setDefaultHostnameVerifier(new CustomizedHostnameVerifier());
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("Content-Length",
                    Integer.toString(postData.length));
            conn.setRequestProperty("Authorization", "GoogleLogin auth="
                    + authenticationToken);
            
            conn.setConnectTimeout(connectionTimeout);
            conn.setReadTimeout(readTimeout);

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("message: " + message + " connectionTimeout : " + connectionTimeout + " readTimeout: " + readTimeout + " googleC2dmUrl: " + googleC2dmUrl);
                LOGGER.info("token: " + token);
                LOGGER.info("authenticationToken: " + authenticationToken);
            }
            
            out = conn.getOutputStream();
            out.write(postData);

            int responseCode = conn.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                String responseMessage = conn.getResponseMessage();
                LOGGER.error("token: " + token + " responseCode: " + responseCode + " responseMessage: " + responseMessage);  
                throw new PlacecruncherRuntimeException(responseMessage);
            }
        } catch (Exception e) { // NOCHECKSTYLE // NOPMD
            LOGGER.error(e, e);
            throw new PlacecruncherRuntimeException("", e);
        } finally {
            try {
                if (out!=null) { out.close(); }
            } catch (Exception e) { // NOCHECKSTYLE // NOPMD
                LOGGER.error(e, e);
            }
        }
    }

    private static class CustomizedHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

}

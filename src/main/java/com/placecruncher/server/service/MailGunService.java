// Copyright 2011 INQ Mobile. All rights reserved.
package com.placecruncher.server.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.io.IOUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailGunService {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(MailGunService.class);

    private static final String UTF8 = "UTF-8";

    @Value("${mailgun.apiKey}")
    private String apiKey;

    @Value("${mailgun.url}")
    private String url;

    @Value("${mailgun.connectionTimeout}")
    private int connectionTimeout;

    @Value("${mailgun.readTimeout}")
    private int readTimeout;
    
    public boolean createMailBox(String mailBox, String password) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("apiKey: " + apiKey + " url: " + url);
        }
        Map<String, String> data = new HashMap<String, String>();
        data.put("mailbox", mailBox);
        data.put("password", password);
        return postData(data);
    }

    public boolean postData(Map<String, String> data) {
        Set<String> keys = data.keySet();
        Iterator<String> keyIter = keys.iterator();
        String content = "";
        for(int i=0; keyIter.hasNext(); i++) {
            Object key = keyIter.next();
            if(i!=0) {
                content += "&";
            }
            try {
                content += key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(e, e);
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("content: " + content);
        }
        
        return sendMessage(content);
        
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
        
        String userPassword = "api" + ":" + apiKey;
        
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("userPassword: " + userPassword);
        }
        
        String encoding = new sun.misc.BASE64Encoder().encode (userPassword.getBytes());
        conn.setRequestProperty ("Authorization", "Basic " + encoding);
        //conn.setRequestProperty("Authorization", "api=" + apiKey);
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
    public boolean sendMessage(String request) {

        boolean result = false;
        OutputStream out = null;
        HttpsURLConnection conn = null;
        InputStream responseStream = null;
        try {
            
            byte[] postData = request.toString().getBytes(UTF8);

            if (url != null) {
                conn = setupConnection(url, postData.length);
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
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Sent message. code/string: " + responseCode + " / " + responseMessage + " data\n" + responseData);
                        result = true;
                    }
                } else {
                    LOGGER.error("Failed to send message. code/string: " + responseCode + " / " + responseMessage + " data\n" + responseData);
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
}

package com.placecruncher.server.controller;

import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.application.data.SecurityTestData;
import com.placecruncher.server.test.ApiClientRequestContext;


public class MemberControllerIT extends ApiTestCase {
    private final Logger log = Logger.getLogger(getClass());

    @Autowired
    private ApiClientRequestContext requestContext;


    private String key = SecurityTestData.TEST_KEY;
    private String secret = SecurityTestData.TEST_SECRET;
    private String username = SecurityTestData.TEST_USERNAME;
    private String password = SecurityTestData.TEST_PASSWORD;

    @Before
    public void setupRequestContext() {
        requestContext.setKey(key);
        requestContext.setSecret(secret);
        requestContext.setClient("IPHONE:3,1:pnid:1:3");
    }
    @After
    public void teardownReqeustContext() {
        requestContext.clear();
    }

    private String login() {
        AuthenticationPayload request = new AuthenticationPayload();
        request.setUserName(username);
        request.setPassword(password);

        String token = postForObject("/api/private/v1/members/self/token", request, SessionTokenWrapper.class).getToken();
        requestContext.setToken(token);
        return token;
    }

    @Test
    public void registerUser() {

        DevicePayload device = new DevicePayload();
        device.setToken("dvsdvasdv");
        device.setDeviceType("iphone");

        RegisterPayload request = new RegisterPayload();
        request.setUserName("ethan@placecruncher.com");
        request.setPassword("fsdvsdvdsfv");
        request.setEmail("ethan@placecruncher.com");
        request.setDevice(device);

        String token = postForObject("/api/private/v1/members/self/register", request, SessionTokenWrapper.class).getToken();
        Assert.assertNotNull(token);
    }

    @Test
    public void registerDevice() {
        login();

        DevicePayload device = new DevicePayload();
        device.setToken("1300564c6f8b8227c57c6e5c6c911fed1ca59a4b4fa5955fa6502ef8553e2163");
        device.setDeviceType("iphone");

        Assert.assertEquals(HttpServletResponse.SC_OK, postForObject("/api/private/v1/members/self/device", device, Meta.class).getCode());
    }

//    private static void registerDevice() {
//        HttpClient httpclient = new DefaultHttpClient();
//        try {
//            DateTime currentTime = DateTime.now();
//
//            DateTimeFormatter parser = ISODateTimeFormat.basicDateTimeNoMillis();
//            String updateTime = parser.print(currentTime);
//
//            String json = "{ \"token\":\"1300564c6f8b8227c57c6e5c6c911fed1ca59a4b4fa5955fa6502ef8553e2163\", \"deviceType\":\"iphone\"} ";
//
//            System.out.println(json);
//
//            StringEntity entity = new StringEntity(json, "application/json", null);
//
//            HttpPost httppost = new HttpPost("http://www.placecruncher.com/api/private/v1/members/self/device");
//
//            httppost.setEntity(entity);
//
//            createSignature();
//
//            System.out.println("key: " + key + " timestamp: " + timestamp + " signature: " + signature);
//
//            httppost.setHeader("X-API-Key", key);
//            httppost.setHeader("X-API-Timestamp", timestamp);
//            httppost.setHeader("X-API-Signature", signature);
//
//            // <OS>:<OS Version>:<Push Notification ID>:<UUID>:<Phone Model>
//
//            httppost.setHeader("X-App-Client", "IPHONE:3,1:pnid:1:3");
//
//            httppost.setHeader("Authentication", "890b29e9-71fe-46e1-8747-dca4a8cffe9f");
//            ResponseHandler<String> responseHandler = new BasicResponseHandler();
//            String responseBody = httpclient.execute(httppost, responseHandler);
//
//            System.out.println("----------------------------------------");
//            System.out.println(responseBody);
//            System.out.println("----------------------------------------");
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//
//        } finally {
//            // When HttpClient instance is no longer needed,
//            // shut down the connection manager to ensure
//            // immediate deallocation of all system resources
//            httpclient.getConnectionManager().shutdown();
//        }
//    }
//

}

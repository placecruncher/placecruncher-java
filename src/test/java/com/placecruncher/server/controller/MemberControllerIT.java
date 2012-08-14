package com.placecruncher.server.controller;

import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.junit.Test;

import com.placecruncher.server.application.data.SecurityTestData;
import com.placecruncher.server.domain.Member;


public class MemberControllerIT extends ApiTestCase {
//    private final Logger log = Logger.getLogger(getClass());

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

        String token = postForObject(PRIVATE_API + "/members/self/register", request, SessionTokenWrapper.class).getToken();
        Assert.assertNotNull(token);
    }

    @Test
    public void registerDevice() {
        loginAsAdmin();

        DevicePayload device = new DevicePayload();
        device.setToken("1300564c6f8b8227c57c6e5c6c911fed1ca59a4b4fa5955fa6502ef8553e2163");
        device.setDeviceType("iphone");

        Assert.assertEquals(HttpServletResponse.SC_OK, postForObject(PRIVATE_API + "/members/self/device", device, Meta.class).getCode());
    }

    @Test
    public void self() {
        String username = SecurityTestData.ADMIN_USERNAME;
        String password = SecurityTestData.ADMIN_PASSWORD;

        login(username, password);

        MemberWrapper wrapper = getForObject(PRIVATE_API + "/members/self", MemberWrapper.class);
        Member member = wrapper.getMember();
        Assert.assertEquals(username, member.getUsername());


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

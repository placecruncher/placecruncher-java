package com.placecruncher.server.controller;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class EmailControllerIT extends ApiTestCase {
//    private final Logger log = Logger.getLogger(getClass());

    @Value("${mailgun.api.key}")
    private String mailGunKey;

    private String generateSignature(String token, String timestamp) throws Exception {
        byte[] keyBytes = mailGunKey.getBytes();

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(keySpec);

        String encode = timestamp + token;

        byte[] hmacBytes = mac.doFinal(encode.getBytes());

        String signature = new String(Hex.encodeHex(hmacBytes));

        return signature;
    }

    @Test
    public void receiveEmail() throws Exception {

        DateTime now = new DateTime();
        String timestamp = Long.toString(now.getMillis()/1000l);
        String token = "token";
        String signature = generateSignature(token, timestamp);

        MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
        request.add("timestamp", timestamp);
        request.add("token", token);
        request.add("signature", signature);

        request.add("sender", "email@addthis.com");

        request.add("from", "cruncher@placecruncher.com");

        request.add("subject", "Inside Scoop SF The 15 restaurants dropped from the Top 100 list");

        request.add("body-plain",
            "http://insidescoopsf.sfgate.com/blog/2012/04/02/the-15-restaurants-dropped-from-the-top-100-list/#.UAzFpUtIflw.email\n" +
            "---\n" +
            "This message was sent by cruncher@placecruncher.com via http://addthis.com.  Please note that AddThis does not verify email addresses.\n" +
            "\n" +
            "Make sharing easier with the AddThis Toolbar:  http://www.addthis.com/go/toolbar-em\n" +
            "\n" +
            "To stop receiving any emails from AddThis please visit: http://www.addthis.com/privacy/email-opt-out?e=.bJnz3HTZ9Vhz0TNaNxn2GfPcdNn1WHPKtBl1GjacdMq0nba in your web browser.");

        request.add("stripped-text",
            "http://insidescoopsf.sfgate.com/blog/2012/04/02/the-15-restaurants-dropped-from-the-top-100-list/#.UAzFpUtIflw.email");

        request.add("stripped-signature",
            "---\n" +
            "This message was sent by cruncher@placecruncher.com via http://addthis.com.  Please note that AddThis does not verify email addresses.\n" +
            "\n" +
            "Make sharing easier with the AddThis Toolbar:  http://www.addthis.com/go/toolbar-em\n" +
            "\n" +
            "To stop receiving any emails from AddThis please visit: http://www.addthis.com/privacy/email-opt-out?e=.bJnz3HTZ9Vhz0TNaNxn2GfPcdNn1WHPKtBl1GjacdMq0nba in your web browser.");

        request.add("stripped-html",
            "http://insidescoopsf.sfgate.com/blog/2012/04/02/the-15-restaurants-dropped-from-the-top-100-list/#.UAzFpUtIflw.email</p>");

        request.add("attachment-count", "0");

        postForEntity("/api/v1/emails", request, Object.class);

    }

}

package com.placecruncher.server.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.placecruncher.server.domain.Device;

@Service
public class ApplePushNotificationService {
    private static final Logger LOGGER = Logger.getLogger(ApplePushNotificationService.class);
    
    @Value("${applePushNotificationService.p12FileLocation}")
    private String p12FileLocation;
    
    @Value("${applePushNotificationService.p12Password}")
    private String p12Password;
    
    
    public void sendMessage(String message, Device device) {
        try {
        String simplePayload = APNS.newPayload().alertBody(message).badge(12).sound("default").build();

            String client ="1300564c6f8b8227c57c6e5c6c911fed1ca59a4b4fa5955fa6502ef8553e2163";
            
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("p12FileLocation: " + p12FileLocation + " p12Password: " + p12Password);
            }

            ApnsService service = APNS.newService()
                .withCert(p12FileLocation, p12Password)
                .withSandboxDestination()
                .build();

            service.push(client, simplePayload);
        } catch (Exception e) {
            LOGGER.error(e, e);
        }
    }

}

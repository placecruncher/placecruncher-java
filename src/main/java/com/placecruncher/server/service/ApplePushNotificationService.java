package com.placecruncher.server.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.placecruncher.server.exception.PlacecruncherRuntimeException;

@Service
public class ApplePushNotificationService {
    private static final Logger LOGGER = Logger.getLogger(ApplePushNotificationService.class);
    
    @Value("${applePushNotificationService.p12FileLocation}")
    private String p12FileLocation;
    
    @Value("${applePushNotificationService.p12Password}")
    private String p12Password;
    
    
    public void sendMessage(String message, String token) {
        try {
            String simplePayload = APNS.newPayload().alertBody(message).badge(1).sound("default").build();

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("p12FileLocation: " + p12FileLocation + " p12Password: " + p12Password);
            }

            ApnsService service = APNS.newService()
                .withCert(p12FileLocation, p12Password)
                .withSandboxDestination()
                .build();

            service.push(token, simplePayload);
        } catch (Exception e) {
            throw new PlacecruncherRuntimeException("", e);
        }
    }

}

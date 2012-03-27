package com.placecruncher.server.domain;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable(dependencyCheck = true)
public class Email {
    private String recipient;
    private String sender;
    private String from;
    private String subject;
    private String bodyPlain;
    private String strippedText;
    private String strippedSignature;
    private String bodyHtml;
    private String strippedHtml;
    private long attachementCount;
    private String timestamp;
    
    @Value("${urbanairship.key}")
    private String mailGunKey;
    
    private static final Logger LOGGER = Logger.getLogger(Email.class);
    
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBodyPlain() {
        return bodyPlain;
    }

    public void setBodyPlain(String bodyPlain) {
        this.bodyPlain = bodyPlain;
    }

    public String getStrippedText() {
        return strippedText;
    }

    public void setStrippedText(String strippedText) {
        this.strippedText = strippedText;
    }

    public String getStrippedSignature() {
        return strippedSignature;
    }

    public void setStrippedSignature(String strippedSignature) {
        this.strippedSignature = strippedSignature;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public String getStrippedHtml() {
        return strippedHtml;
    }

    public void setStrippedHtml(String strippedHtml) {
        this.strippedHtml = strippedHtml;
    }

    public long getAttachementCount() {
        return attachementCount;
    }

    public void setAttachementCount(long attachementCount) {
        this.attachementCount = attachementCount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Email [recipient=" + recipient + ", sender=" + sender
                + ", from=" + from + ", subject=" + subject + ", bodyPlain="
                + bodyPlain + ", strippedText=" + strippedText
                + ", strippedSignature=" + strippedSignature + ", bodyHtml="
                + bodyHtml + ", strippedHtml=" + strippedHtml
                + ", attachementCount=" + attachementCount + ", timestamp="
                + timestamp + "]";
    }
    
    public boolean verify(String token, String timestamp, String signature) throws Exception {

        if (LOGGER.isInfoEnabled()) {
    		LOGGER.info("verify:apiKey: " + mailGunKey + " token:" + token + " timestamp: " + timestamp + " signature: " + signature);
    	}
    	
        String key = mailGunKey;
        byte[] keyBytes = key.getBytes();

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(keySpec);

        String encode = timestamp + token;
        
        byte[] hmacBytes = mac.doFinal(encode.getBytes());

        String newSignature = new String(Hex.encodeHex(hmacBytes));
        
        if (LOGGER.isInfoEnabled()) {
        	LOGGER.info("newSignature: " + newSignature + " old signature: " + signature);
        }
        
        boolean result = StringUtils.equals(newSignature, signature);
        return result;
    }

}

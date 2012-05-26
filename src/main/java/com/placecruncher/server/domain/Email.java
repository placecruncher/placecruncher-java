package com.placecruncher.server.domain;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Configurable;

import com.placecruncher.server.dao.EmailDao;

// Standard change

@Entity
@Configurable(dependencyCheck = true)
public class Email extends SuperEntity {

    private Integer id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable=false)
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }
   // private String recipient;
    
    private String sender;
    /*
    private String from;
    private String subject;
    private String bodyPlain;
    private String strippedText;
    private String strippedSignature;
    private String bodyHtml;
    private String strippedHtml;
    private long attachementCount;
    private String timestamp;
    */
    
    @Value("${mailgun.api.key}")
    private String mailGunKey;
    
    @Autowired
    private EmailDao emailDao;
    
    private static final Logger LOGGER = Logger.getLogger(Email.class);
    
    @Column(nullable=false)
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
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
    
    public void store() {
        emailDao.saveOrUpdate(this);
    }

}

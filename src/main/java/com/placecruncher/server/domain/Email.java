package com.placecruncher.server.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import com.placecruncher.server.dao.EmailDao;

// Standard change

@Entity
@Table(name="EMAIL")
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

    private String sender;

    private String from;
    private String subject;
    private String bodyPlain;
    private String strippedText;
    private String strippedSignature;
    private String bodyHtml;
    private String strippedHtml;
    private long attachementCount;
    private String recipient;
    
    private int timestamp;

    @Transient
    private Pattern pattern = Pattern.compile(
            "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" +
            "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" +
            "|mil|biz|info|mobi|name|aero|jobs|museum" +
            "|travel|[a-z]{2}))(:[\\d]{1,5})?" +
            "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" +
            "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" +
            "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" +
            "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");

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

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
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
    
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void store() {
        emailDao.saveOrUpdate(this);
    }

    public Set<String> extractUrls() {
        Set<String> results = new HashSet<String>();
        if (StringUtils.isNotEmpty(this.bodyPlain)) {
            results = extractUrls(this.bodyPlain);
        } else if (StringUtils.isNotEmpty(this.strippedText)) {
            results = extractUrls(this.strippedText);
        }

        for (String result : results) {
            if  (LOGGER.isInfoEnabled()) {
                LOGGER.info("Url found in email: " + result);
            }
        }

        return results;
    }

    private Set<String> extractUrls(String input) {
        Set<String> result = new HashSet<String>();

        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String url = matcher.group();
            if (url.contains("?")) {
                String parts[] = url.split("\\?");
                url = parts[0];
            }

            if (!checkForImage(url)) {
                result.add(url);
            }
        }

        return result;
    }

    private boolean checkForImage(String url) {
        if (StringUtils.isEmpty(url)) {
            return false;
        }

        if (StringUtils.endsWithIgnoreCase(url, ".jpeg") || StringUtils.endsWithIgnoreCase(url, ".jpg") ||
            StringUtils.endsWithIgnoreCase(url, ".gif") || StringUtils.endsWithIgnoreCase(url, ".giff") ||
            StringUtils.endsWithIgnoreCase(url, ".png")) {
            return true;
        }

        return false;
    }


    @Override
    public String toString() {
        return "Email [id=" + id + ", sender=" + sender + ", from=" + from + ", subject=" + subject + ", bodyPlain=" + bodyPlain + ", strippedText=" + strippedText + ", strippedSignature=" + strippedSignature + ", bodyHtml=" + bodyHtml
                + ", strippedHtml=" + strippedHtml + ", attachementCount=" + attachementCount + ", recipient=" + recipient + "]";
    }


}

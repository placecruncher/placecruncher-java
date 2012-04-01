package com.placecruncher.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Configurable;


@Entity
@Table(name = "API_KEY")
@Configurable(dependencyCheck = true)
public class ApiKey extends AbstractEntity {
    private static final Logger LOGGER = Logger.getLogger(ApiKey.class);

    private Integer id;
    private String key;    
    private String secret;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    public Integer getId() {
        return id;
    }
    
    protected void setId(Integer id) {
        this.id = id;
    }
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean validate(String requestKey, String signature, String timeStamp, int timestampLeniencySeconds) {
        boolean result = false;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("key: " + requestKey + " signature: " + signature
                    + " timeStamp: " + timeStamp
                    + " timestampLeniencySeconds: " + timestampLeniencySeconds);
        }
        if (StringUtils.isNotEmpty(requestKey) && StringUtils.isNotEmpty(signature)
                && StringUtils.isNotEmpty(timeStamp)) {
            try {
                String digest = timeStamp + "." + this.secret;

                String newSignature = DigestUtils.sha256Hex(digest);

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("key: " + requestKey + " signature: " + signature
                            + " timeStamp: " + timeStamp
                            + " timestampLeniencySeconds: "
                            + timestampLeniencySeconds + " digest: " + digest
                            + " newSignature: " + newSignature);
                }

                if (StringUtils.equals(signature, newSignature)) {
                    DateTime passedInDateTime = null;

                    DateTimeFormatter parser = ISODateTimeFormat
                            .basicDateTimeNoMillis();
                    passedInDateTime = parser.parseDateTime(timeStamp);

                    DateTime currentTime = DateTime.now();

                    Seconds gap = Seconds.secondsBetween(passedInDateTime,
                            currentTime);

                    int diff = Math.abs(gap.getSeconds());

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("key: " + requestKey + " signature: " + signature
                                + " timeStamp: " + timeStamp
                                + " timestampLeniencySeconds: "
                                + timestampLeniencySeconds + " digest: "
                                + digest + " newSignature: " + newSignature
                                + " diff: " + diff);
                    }

                    if (diff <= timestampLeniencySeconds) {
                        result = true;
                    }
                }
            //CHECKSTYLE:OFF IllegalCatch
            } catch (Exception e) {
                LOGGER.error(e, e);
            }
            //CHECKSTYLE:ON IllegalCatch
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("key: " + requestKey + " signature: " + signature
                    + " timeStamp: " + timeStamp
                    + " timestampLeniencySeconds: " + timestampLeniencySeconds
                    + " result: " + result);
        }
        return result;
    }
    
    @Override
    public String toString() {
        return "ApiKey [id=" + id + ", key=" + key + ", secret=" + secret + "]";
    }
}

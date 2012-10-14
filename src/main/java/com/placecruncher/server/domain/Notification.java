package com.placecruncher.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.placecruncher.server.application.Constants;
import com.placecruncher.server.exception.PlacecruncherRuntimeException;

@Entity
@Table(name="NOTIFICATION")
@Configurable(dependencyCheck = true)
public class Notification extends SuperEntity {

    private Integer id;

    private Member member;

    private String json;

    private NotificationMessage message;

    @Autowired
    private ObjectMapper objectMapper;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable=false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "memberId", nullable = false)
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Column(name="message", nullable = false, length = Constants.MESSAGE_MAXLEN)
    protected String getJson() {
        return json;
    }
    protected void setJson(String json) {
        this.json = json;
    }


    @Transient
    public NotificationMessage getMessage() {
        if (message != null) {
            return message;
        }

        if (json != null) {
            try {
                message = objectMapper.readValue(json, new TypeReference<NotificationMessage>(){});
            } catch (Exception e) {
                throw new PlacecruncherRuntimeException("Unable to deserialize NotificationMessage, " + e.getMessage(), e);
            }
        }
        return message;
    }

    public void setMessage(NotificationMessage message) {
        this.message = message;
        if (message != null) {
            try {
                this.json = objectMapper.writeValueAsString(message);
            } catch (Exception e) {
                throw new PlacecruncherRuntimeException("Unable to serialize NotificationMessage, " + e.getMessage(), e);
            }
        } else {
            this.json = null;
        }
    }


}

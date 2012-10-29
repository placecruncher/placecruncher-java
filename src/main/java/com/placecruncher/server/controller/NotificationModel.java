package com.placecruncher.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.Transformer;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.placecruncher.server.domain.NotificationMessage;
import com.placecruncher.server.domain.Notification;
import com.placecruncher.server.util.TransformUtils;

public class NotificationModel extends EntityModel {
    public static final TypeReference<List<NotificationModel>> LIST_TYPE = new TypeReference<List<NotificationModel>>(){};

    private Integer id;
    private NotificationMessage message;
    private Date created;

    public static List<NotificationModel> transform(List<Notification> notifications) {
        return TransformUtils.transform(notifications, new Transformer() {
            public Object transform(Object input) {
                return new NotificationModel((Notification) input);
            }
        }, new ArrayList<NotificationModel>());
    }

    public NotificationModel() {
    }

    public NotificationModel(Notification notification) {
        BeanUtils.copyProperties(notification, this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public NotificationMessage getMessage() {
        return message;
    }

    public void setMessage(NotificationMessage message) {
        this.message = message;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }



}

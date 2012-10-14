package com.placecruncher.server.dao;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.controller.NotificationModel;
import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Notification;
import com.placecruncher.server.domain.NotificationMessage;
import com.placecruncher.server.domain.SimpleNotificationMessage;
import com.placecruncher.server.service.NewPlaceListMessage;

public class NotificationDaoTest extends DaoTestCase {
    private final Logger log = Logger.getLogger(getClass());
    
    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private NotificationFactory notificationFactory;

    @Autowired
    private MemberFactory memberFactory;

    @Test
    public void create() {
        notificationFactory.create();
        flush();
    }

    @Test
    public void findByMember() {
        Member m1 = memberFactory.create();
        Member m2 = memberFactory.create();

        Notification n1 = notificationFactory.create("member", m1);
        Notification n2 = notificationFactory.create("member", m1);
        Notification n3 = notificationFactory.create("member", m2);

        Assert.assertThat(notificationDao.findByMember(m1), contains(n1,n2));
        Assert.assertThat(notificationDao.findByMember(m2), contains(n3));
    }


    @Test
    public void serializeTestMessage() {
        String message = "This is a test message";
        NotificationMessage m1 = new SimpleNotificationMessage(message);
        Notification n1 = notificationFactory.create("message", m1);
        flush();
        notificationDao.evict(n1);

        NotificationMessage deserialized = notificationDao.load(n1.getId()).getMessage();
        Assert.assertThat(deserialized, is(instanceOf(SimpleNotificationMessage.class)));
        Assert.assertThat(((SimpleNotificationMessage)deserialized).getMessage(), is(equalTo(message)));
    }

    @Test
    public void serializeNewPlaceListMessage() {
        String message = "This is a test message";
        NotificationMessage m1 = new NewPlaceListMessage();
        Notification n1 = notificationFactory.create("message", m1);
        flush();
        notificationDao.evict(n1);

        NotificationMessage deserialized = notificationDao.load(n1.getId()).getMessage();
        Assert.assertThat(deserialized, is(instanceOf(NewPlaceListMessage.class)));
    }
    
    @Test
    public void createNotificationModel() {
        String message = "This is a test message";
        SimpleNotificationMessage m1 = new SimpleNotificationMessage(message);
        NotificationModel model = new NotificationModel(notificationFactory.build("message", m1));
    }

}

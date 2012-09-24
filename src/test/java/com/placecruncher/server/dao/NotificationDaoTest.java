package com.placecruncher.server.dao;

import static org.hamcrest.Matchers.contains;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Notification;

public class NotificationDaoTest extends DaoTestCase {
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

}

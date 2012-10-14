package com.placecruncher.server.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.dao.NotificationDao;
import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Notification;
import com.placecruncher.server.domain.NotificationMessage;

@Service
public class NotificationService {
    private final Logger log = Logger.getLogger(getClass());

    @Autowired
    private NotificationDao notificationDao;

    @Transactional
    public void sendNotification(Member member, NotificationMessage message) {
        log.info("Sending message '" + message + "' to member '" + member.getUsername() + "'");
        Notification notification = new Notification();
        notification.setMember(member);
        notification.setMessage(message);
        notificationDao.persist(notification);

        member.notifyDevices(notification);
    }

}

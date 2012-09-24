package com.placecruncher.server.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placecruncher.server.domain.AbstractEntityFactory;
import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Notification;

@Component
public class NotificationFactory extends AbstractEntityFactory<Notification> {

    @Autowired
    public MemberFactory memberFactory;

    @Autowired
    public NotificationFactory(NotificationDao dao) {
        super(dao);
    }

    @Override
    public Notification instance(int id, Map<String, Object> properties) {
        Notification instance = new Notification();
        instance.setMessage("Test notification " + id);

        Member member = (Member)properties.get("member");
        if (member == null) {
            member = memberFactory.create();
        }
        instance.setMember(member);

        return instance;
    }
}

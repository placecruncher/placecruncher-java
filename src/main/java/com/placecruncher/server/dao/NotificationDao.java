package com.placecruncher.server.dao;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Notification;

@Repository
public class NotificationDao extends AbstractDao<Integer, Notification> {
    public NotificationDao() {
        super(Notification.class);
    }

    @SuppressWarnings("unchecked")
    public List<Notification> findByMember(Member member) {
        return createCriteria()
                .add(Restrictions.eq("member", member))
                .addOrder(Order.asc("created"))
                .list();
    }

}

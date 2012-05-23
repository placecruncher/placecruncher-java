package com.placecruncher.server.domain;

import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.event.PreInsertEvent;
import org.hibernate.event.PreInsertEventListener;
import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreUpdateEventListener;

import com.placecruncher.server.util.TimeUtils;


public class EntityListener implements PreUpdateEventListener, PreInsertEventListener
{
    private static final long serialVersionUID = 1L;

    /** {@inheritDoc} */
    public boolean onPreInsert(PreInsertEvent event)
    {
        if (AbstractEntity.class.isAssignableFrom(event.getEntity().getClass()))
        {
            Object[] state = event.getState();
            Date now = TimeUtils.getCurrentTime();
            state[ArrayUtils.indexOf(event.getPersister().getPropertyNames(), Entity.CREATED_PROPERTY)] = now;
        }
        if (SuperEntity.class.isAssignableFrom(event.getEntity().getClass()))
        {
            Object[] state = event.getState();
            Date now = TimeUtils.getCurrentTime();
            state[ArrayUtils.indexOf(event.getPersister().getPropertyNames(), Entity.CREATED_PROPERTY)] = now;
        }
        return false;
    }

    /** {@inheritDoc} */
    public boolean onPreUpdate(PreUpdateEvent event)
    {
        if (AbstractEntity.class.isAssignableFrom(event.getEntity().getClass()))
        {
            Object[] state = event.getState();
            Date now = TimeUtils.getCurrentTime();
            state[ArrayUtils.indexOf(event.getPersister().getPropertyNames(), Entity.UPDATED_PROPERTY)] = now;
        }
        if (SuperEntity.class.isAssignableFrom(event.getEntity().getClass()))
        {
            Object[] state = event.getState();
            Date now = TimeUtils.getCurrentTime();
            state[ArrayUtils.indexOf(event.getPersister().getPropertyNames(), Entity.UPDATED_PROPERTY)] = now;
        }
        return false;
    }


}

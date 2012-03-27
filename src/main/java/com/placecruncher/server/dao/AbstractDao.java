package com.placecruncher.server.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.domain.Entity;

public class AbstractDao<I extends Serializable, T extends Entity<I>> {
    private SessionFactory sessionFactory;
    private Class<T> clazz;

    /**
     * Instantiate the DAO.
     *
     * @param clazz The entity class type.
     */
    public AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Create a hibernate query.
     *
     * @param query The query string.
     * @return The query.
     */
    protected Query createQuery(String query) {
        return getSessionFactory().getCurrentSession().createQuery(query);
    }

    /**
     * Create a criteria for this entity.
     *
     * @return The criteria.
     */
    protected Criteria createCriteria() {
        return getCurrentSession().createCriteria(clazz);
    }

    /**
     * Gets the current hibernate session.
     *
     * @return The current hibernate session
     */
    protected final Session getCurrentSession() {
        return getSessionFactory().getCurrentSession();
    }

    /** {@inheritDoc} */
    public final void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    /** {@inheritDoc} */
    public final T get(I identifier) {
        @SuppressWarnings("unchecked")
        T entity = (T) getCurrentSession().get(clazz, identifier);
        return entity;
    }

    /** {@inheritDoc} */
    public final T get(String code) {
        @SuppressWarnings("unchecked")
        T entity = (T) createCriteria().add(Restrictions.eq("code", code)).uniqueResult();
        return entity;
    }

    /** {@inheritDoc} */
    public final T load(I identifier) {
        @SuppressWarnings("unchecked")
        T entity = (T) getCurrentSession().load(clazz, identifier);
        return entity;
    }

    /** {@inheritDoc} */
    public final T load(String code) {
        @SuppressWarnings("unchecked")
        T entity = (T) createCriteria().add(Restrictions.eq("code", code)).uniqueResult();
        if (entity == null) {
            throw new ObjectNotFoundException(code, clazz.getName());
        }
        return entity;
    }

    /** {@inheritDoc} */
    public final T merge(T detachedEntity) {
        @SuppressWarnings("unchecked")
        T entity = (T) getCurrentSession().merge(detachedEntity);
        return entity;
    }

    /** {@inheritDoc} */
    public final void refresh(T entity) {
        getCurrentSession().merge(entity);
    }

    /** {@inheritDoc} */
    public final I persist(T transientEntity) {
        @SuppressWarnings("unchecked")
        I id = (I) getCurrentSession().save(transientEntity);
        return id;
    }

    /** {@inheritDoc} */
    public final void update(T detachedEntity) {
        getCurrentSession().update(detachedEntity);
    }
    
    /** {@inheritDoc} */
    public final void saveOrUpdate(T detachedEntity) {
        getCurrentSession().saveOrUpdate(detachedEntity);
    }

    /** {@inheritDoc} */
    public Collection<T> findAll() {
        @SuppressWarnings("unchecked")
        List<T> entities = (List<T>) createCriteria().list();
        return entities;
    }

    /** {@inheritDoc} */
    public List<T> findAll(String orderBy, boolean asc) {
        @SuppressWarnings("unchecked")
        List<T> entities = (List<T>) createCriteria().addOrder(asc ? Order.asc(orderBy) : Order.desc(orderBy)).list();
        return entities;
    }

    /** {@inheritDoc} */
    public List<T> findAll(int start, int count, String orderBy, boolean asc) {
        @SuppressWarnings("unchecked")
        List<T> entities = (List<T>) createCriteria().setFirstResult(start).setMaxResults(count).addOrder(asc ? Order.asc(orderBy) : Order.desc(orderBy)).list();
        return entities;
    }

}

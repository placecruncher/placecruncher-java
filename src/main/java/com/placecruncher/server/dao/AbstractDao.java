package com.placecruncher.server.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ObjectRetrievalFailureException;

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

    /**
     * Delete the given entity.
     * @param entity the entity to delete.
     */
    public final void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    /**
     * Get the entity with the given identifier returning null if not found.
     * @param identifier The identifier.
     * @return The entity or null if not found.
     */
    public final T get(I identifier) {
        @SuppressWarnings("unchecked")
        T entity = (T) getCurrentSession().get(clazz, identifier);
        return entity;
    }

    /**
     * Fetch the entity with the given identifier throwing an exception immediately
     * if the entity cannot be found.
     * @param identifier The identifier.
     * @return The entity
     * @throws ObjectRetrievalFailureException if the entity does not exist.
     */
    public final T fetch(I identifier) {
        @SuppressWarnings("unchecked")
        T entity = (T) getCurrentSession().get(clazz, identifier);
        if (entity == null) {
            throw new ObjectRetrievalFailureException(clazz, identifier);
        }
        return entity;
    }

    /**
     * Load the entity with the given identifier assuming that it exists, an
     * exception will be thrown if the entity does not exist, however this
     * exception may not be thrown until an attempt is made to access the
     * object.
     * @param identifier the identifier.
     * @return The entity or a proxy to the entity.
     * @throws ObjectRetrievalFailureException Might be thrown if a proxy cannot be
     * returned immediately, otherwise the proxy might throw this exception
     * when accessed.
     */
    public final T load(I identifier) {
        @SuppressWarnings("unchecked")
        T entity = (T) getCurrentSession().load(clazz, identifier);
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
    public List<T> findAll() {
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

    public void evict(T entity) {
        getCurrentSession().evict(entity);
    }
}

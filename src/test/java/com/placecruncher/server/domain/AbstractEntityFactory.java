package com.placecruncher.server.domain;

import java.util.Collections;
import java.util.Map;

import com.placecruncher.server.dao.AbstractDao;

public abstract class AbstractEntityFactory<E extends Entity<Integer>> extends AbstractObjectFactory<E> {
    private AbstractDao<Integer, E> dao;

    public AbstractEntityFactory(AbstractDao<Integer, E> dao) {
        this.dao = dao;
    }

    private E save(E entity) {
        return dao.load(dao.persist(entity));
    }

    public final E create() {
        Map<String, Object> properties = Collections.emptyMap();
        return create(properties);
    }

    public final E create(Map<String, Object> properties) {
        return save(build(properties));
    }

    /**
     * Build and persist an entity overriding default properties with those provided.
     * @param name The first property name.
     * @param value The the first property value.
     * @param properties An arbitrary list of String, Object, String, Object.
     * @return A customized entity created by the factory.
     */
    public final E create(String name, Object value, Object... properties) {
        return save(build(name, value, properties));
    }


}

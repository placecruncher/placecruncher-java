package com.placecruncher.server.domain;

import java.util.Collections;
import java.util.Map;

import com.placecruncher.server.dao.AbstractDao;

public abstract class AbstractEntityFactory<E extends Entity<Integer>> extends AbstractObjectFactory<E> {
    
    public abstract AbstractDao<Integer, E> getDao();
    
    public final E create() {
        Map<String, Object> properties = Collections.emptyMap();
        return create(properties);
    }
    
    public final E create(Map<String, Object> properties) {
        E entity = build(properties);
        return getDao().load(getDao().persist(entity));
    }
    
}

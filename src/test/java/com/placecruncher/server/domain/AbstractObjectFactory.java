package com.placecruncher.server.domain;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.BeanWrapperImpl;

public abstract class AbstractObjectFactory<T> implements ObjectFactory<T> {

    public T build() {
        Map<String, Object> properties = Collections.emptyMap();
        return build(properties);
    }
    
    protected void populate(T object, Map<String, Object> properties) {
        BeanWrapperImpl wrapper = new BeanWrapperImpl(object);
        wrapper.setPropertyValues(properties);
	}
}

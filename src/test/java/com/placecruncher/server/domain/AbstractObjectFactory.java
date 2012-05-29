package com.placecruncher.server.domain;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public abstract class AbstractObjectFactory<T> implements ObjectFactory<T> {
    
    private int uniqueKeyCounter = 0;

    /**
     * Create a unique key for objects in this factory using the simple class name.
     * @return A unique key.
     */
    public final String uniqueKey() { 
        return uniqueKey(getClass().getSimpleName());
    }

    /**
     * Create a unique key for objects in this factory using the given prefix.
     * @param prefix The prefix of the key.
     * @return A unique key.
     */
    public String uniqueKey(String prefix) { 
        return prefix + (uniqueKeyCounter++);
    }
    
    /**
     * Convenience method builds an object with an empty set of custom property values.
     * @return A default object created by the factory.
     */
    public T build() {
        Map<String, Object> properties = Collections.emptyMap();
        return build(properties);
    }
    
    /**
     * Override this method to build a default instance of the object type.
     * @param key A unique key for the object.
     * @return A default object.
     */
    public abstract T buildDefaultObject(String key);
    
    public T build(Map<String, Object> properties) {
        String key = uniqueKey();
        T object = buildDefaultObject(key);
        if (properties != null) {
            populate(object, properties);
        }
        return object;
    }
    
    
    private void populate(T object, Map<String, Object> properties) {
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        wrapper.setPropertyValues(properties);
	}
}

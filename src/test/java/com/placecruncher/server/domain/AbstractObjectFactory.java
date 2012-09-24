package com.placecruncher.server.domain;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public abstract class AbstractObjectFactory<T> implements ObjectFactory<T> {

    private int uniqueKeyCounter = 0;

    /**
     * Override this method to build a default instance of the object type.
     * @param id A unique value within the factory that can be used to generate default property values.
     * @param properties A map of property name/value pairs which override default values.
     * @return A default object.
     */
    protected abstract T instance(int id, Map<String, Object> properties);

    /**
     * Get the prefix name associated with the factory.
     * @return A prefix name.
     */
    protected final String prefix() {
      return getClass().getSimpleName();
    }

    /**
     * Convenience method builds an object with default set of values.
     * @return A default object created by the factory.
     */
    public T build() {
      Map<String, Object> properties = Collections.emptyMap();
      return build(properties);
    }

    /**
     * Build an object overriding any default properties with those provided.
     * @param name The first property name.
     * @param value The the first property value.
     * @param properties An arbitrary list of String, Object, String, Object.
     * @return A customized object created by the factory.
     */
    public T build(String name, Object value, Object... properties) {
      return build(PropertyBuilder.build(name, value, properties));
    }

    /**
     * Build an object overriding any default properties with those provided.
     * @param properties Map of property name/value pairs.
     * @return A customized object created by the factory.
     */
    public T build(Map<String, Object> properties) {
      T object = instance(uniqueKeyCounter++, properties);
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

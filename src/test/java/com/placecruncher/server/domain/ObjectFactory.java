package com.placecruncher.server.domain;

import java.util.Map;


public interface ObjectFactory<T> {
    /**
     * Builds a object allowing the user to customize the creation of the object by 
     * a map of property values. 
     * @param properties Map of bean property values.
     * @return The new object.
     */
	T build(Map<String, Object> properties);	
}

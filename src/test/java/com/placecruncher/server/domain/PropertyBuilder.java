package com.placecruncher.server.domain;

import java.util.HashMap;
import java.util.Map;

public class PropertyBuilder {
    private Map<String, Object> properties = new HashMap<String, Object>();
    
    public PropertyBuilder() {
    }
    
    public PropertyBuilder put(String key, Object value) {
        properties.put(key,  value);
        return this;
    }
    
    public Map<String, Object> build() {
        return properties;
    }
}

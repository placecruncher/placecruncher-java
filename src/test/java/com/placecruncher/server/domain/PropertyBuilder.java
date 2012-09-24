package com.placecruncher.server.domain;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

public class PropertyBuilder {
    private Map<String, Object> properties = new HashMap<String, Object>();

    private PropertyBuilder() {}

    public static PropertyBuilder instance() {
      return new PropertyBuilder();
    }

    /**
     * Adds a property name/value pair to the map.
     * @param name The property name.
     * @param value The property value.
     * @return The builder.
     */
    public PropertyBuilder put(String name, Object value) {
      properties.put(name,  value);
      return this;
    }

    /**
     * Returns the map of property name/value pairs.
     * @return Map of property name/value pairs.
     */
    public Map<String, Object> build() {
      return properties;
    }

    /**
     * Convenience method to build a map of property name/value pairs.
     * @param name The first property name.
     * @param value The the first property value.
     * @param properties An arbitrary list of String, Object, String, Object.
     * @return Map of property name/value pairs.
     */
    public static Map<String, Object> build(String name, Object value, Object... properties) {
      Assert.assertTrue("Properties must be a list of name/value pairs however the list is an odd number in length!", properties.length%2 == 0);
      PropertyBuilder builder = instance().put(name, value);
      for (int i = 0; i < properties.length; i += 2) {
        builder.put(properties[i].toString(), properties[i+1]);
      }
      return builder.build();
    }

}

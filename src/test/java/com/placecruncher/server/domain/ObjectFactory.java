package com.placecruncher.server.domain;

import java.util.Map;


public interface ObjectFactory<T> {
	T build(Map<String, Object> properties);	
}

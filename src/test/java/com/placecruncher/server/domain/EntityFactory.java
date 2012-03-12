package com.placecruncher.server.domain;


public interface EntityFactory<E extends Entity<?>> {
    E create(Object... values);

    E build(Object... values);
}

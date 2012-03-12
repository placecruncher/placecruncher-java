package com.placecruncher.server.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * A persistent entity is an object with its own lifecycle, it has its own
 * database identity and is referenced by other entities. The opposite of an
 * entity is a value type which does not have its own identity and is dependent
 * on an entity for its existence.
 * 
 * For example, a Person is an Entity, and an Address is a value type. If two
 * people live in the same apartment, they are both obviously entities, but
 * their home addresses are most likely not. Their addresses may have identical
 * values, but it is unlikely that the Person object references an Address
 * object that has its own lifecycle.
 * 
 * @param <I>
 *            The entity identifier type, usually Integer and occasionally Long.
 */
public interface Entity<I extends Serializable> {
    /** The name of the creation timestamp property, used by the EntityListener. */
    String CREATED_PROPERTY = "created";

    /** The name of the updated timestamp property, used by the EntityListener. */
    String UPDATED_PROPERTY = "updated";

    /**
     * Get the identifier of the entity.
     * 
     * @return The identifier of the entity.
     */
    I getId();

    /**
     * Get the timestamp the entity was created.
     * 
     * @return The timestamp the entity was created.
     */
    Date getCreated();

    /**
     * Get the timestamp the entity was last updated.
     * 
     * @return The timestamp the entity was last updated.
     */
    Date getUpdated();

    /**
     * Get the entity version use for optimistic database locking.
     * 
     * @return The entity version use for optimistic locking.
     */
    int getVersion();
}

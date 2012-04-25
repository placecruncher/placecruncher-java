package com.placecruncher.server.application;

import java.util.Collection;

import org.springframework.core.Ordered;

/**
 * SeedData can be used to create data using business methods instead of directly
 * inserting data into the database.  Classes that implement this interface will be
 * invoked during startup by the Startup class.
 */
public interface SeedData extends Ordered {
    /**
     * The name of the data set
     * @return The name of the data set.
     */
    String getName();

    /**
     * The configurations that should include this seed data, an empty list
     * indicates that the seed data always applies.
     * @return The configurations that should include this seed data.
     */
    Collection<String> getConfigurations();

    /**
     * Populate the system with seed data defined by this bean.
     */
    void populate();

    /**
     * Implementations should return true if they are capable of being
     * run each time the system is started.  This can allow seed data
     * to be reset or updated.
     * @return true if this seed data should be (re)loaded each time the
     * system is started.
     */
    boolean isRepeatable();
}

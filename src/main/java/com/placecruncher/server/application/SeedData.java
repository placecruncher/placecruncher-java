package com.placecruncher.server.application;

import java.util.Collection;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

/**
 * Classes that implement this interface will be invoked by the
 * SeedDataProcessor and will be able to seed the database based on the current
 * configuration.
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
}

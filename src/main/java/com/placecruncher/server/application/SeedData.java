package com.placecruncher.server.application;

import java.util.Collection;

import org.springframework.core.Ordered;

/**
 * SeedData can be used to create data using business methods instead of directly
 * inserting data into the database.  Classes that implement this interface will be
 * invoked during startup by the Startup class once the entire Spring context has 
 * been initialized.
 * 
 * <p>The order in which SeedData is initialized is determined by the {@link Ordered}
 * interface.  Constants can be pre-defined {@see AbstractSeedData} that will help developers determine a value
 * for their ordering which is most likely to work.  For example, runtime configuration
 * data maybe need to be initialized before security data before application data and 
 * finally user data.</p>
 * 
 * <p>The isRepeatable method determines if SeedData should be processed once or if 
 * the SeedData should be processed each time the system is started.  If the SeedData
 * is repeatable, it is up to the developer to implement the populate method in a manner
 * that is repeatable.</p>
 * 
 * <p>SeedData can also be grouped into specific configurations.  For example, a 'test'
 * configuration could contain well-known data used in functional testing, a 'demo' 
 * configuration could contain data that is used in conjunction with a stock demo
 * script, and 'feature-foo' could contain SeedData useful in developing feature foo.</p>
 * 
 * <p>Runtime properties define which configurations are processed by the system at startup.
 * There is a set of default SeedData configurations defined by the default.seed.data.configurations
 * runtime property in file placecruncher.properties.  Custom SeedData configurations can be
 * specified using the custom.seed.data.configurations runtime property in either an 
 * environment-specific runtime property file or your local.properties</p>
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

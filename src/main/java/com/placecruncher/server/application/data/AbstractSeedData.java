package com.placecruncher.server.application.data;

import java.util.Collection;
import java.util.Collections;

import org.springframework.core.Ordered;

import com.placecruncher.server.application.SeedData;

public abstract class AbstractSeedData implements SeedData {
	// The DEFAULT configuration
	private static final String DEFAULT_CONFIGURATION = "default";
	
    // These constants let you pick when SeedData should be populated within a given category.
    private static final int SEED_BEFORE = -1;
    private static final int SEED_AFTER = 1;
    
    // Categories of SeedData and the order they are populated.
    protected static final int SEED_FIRST       = Ordered.HIGHEST_PRECEDENCE;
    protected static final int SEED_CORE        = 10;  // Runtime configuration and constants tables
    protected static final int SEED_SECURITY    = 20;  // Users, principals, security roles.
    protected static final int SEED_SYSTEM_DATA = 30;  // Data that belongs to the system (jobs, pre-defined tags, etc)
    protected static final int SEED_USER_DATA   = 40;  // Data that belongs to users
    protected static final int SEED_LAST        = Ordered.LOWEST_PRECEDENCE;  
    
    	
    protected final int after(int order) {
    	return order + SEED_AFTER;
    }
	
    protected final int before(int order) {
    	return order + SEED_BEFORE;
    }
    
	public Collection<String> getConfigurations() {
		// By default, run in the default configuration
		return Collections.singletonList(DEFAULT_CONFIGURATION);
	}

}

package com.placecruncher.server.application.data;


public class CoreData extends AbstractSeedData {

	@Override
	public boolean isRepeatable() {
		return true;
	}

	@Override
	public int getOrder() {
		return SEED_CORE;
	}

	@Override
	public String getName() {
		return "Core Seed Data";
	}

	@Override
	public void populate() {
		
		// Create static runtime configuration data here.
		
	}

}

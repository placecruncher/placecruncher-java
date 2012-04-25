package com.placecruncher.server.application;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Service;

import com.placecruncher.server.service.SeedDataService;

@Service
public class Startup implements SmartLifecycle
{
    private final Log log = LogFactory.getLog(this.getClass());
    private boolean running;

    @Autowired
    private SeedDataService seedDataService;

    @Value("${default.seed.data.configurations}")
    private Collection<String> defaultConfigurations;

    @Value("${custom.seed.data.configurations:}")
    private Collection<String> customConfigurations;

    /** {@inheritDoc} */
    public boolean isAutoStartup()
    {
        return true;
    }

    /** {@inheritDoc} */
    public boolean isRunning()
    {
        return running;
    }

    /** {@inheritDoc} */
    public void start()
    {
        if (log.isInfoEnabled()) log.info("Starting up application...");

        if (log.isInfoEnabled()) log.info("Loading seed data...");
        @SuppressWarnings("unchecked")
		Collection<String> collections = CollectionUtils.union(defaultConfigurations, customConfigurations);
        seedDataService.loadSeedData(collections);

        if (log.isInfoEnabled()) log.info("Application startup complete.");
        running = true;
    }

    private void shutdown()
    {
        // TODO add shutdown logic here.
        running = false;
    }

    /** {@inheritDoc} */
    public void stop(Runnable callback)
    {
        shutdown();
        callback.run();
    }

    /** {@inheritDoc} */
    public void stop()
    {
        shutdown();
    }


    /** {@inheritDoc} */
    public int getPhase()
    {
        return Integer.MAX_VALUE;
    }

}

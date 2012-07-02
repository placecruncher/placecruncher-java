package com.placecruncher.server.application;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Service;

import com.placecruncher.server.service.SeedDataService;

@Service
public class Startup implements SmartLifecycle
{
    private final Logger log = Logger.getLogger(this.getClass());

    private boolean running;
    private Collection<String> defaultConfigurations;
    private Collection<String> customConfigurations;

    @Autowired
    private SeedDataService seedDataService;

    @Value("${default.seed.data.configurations}")
    public void setDefaultConfigurations(String defaultConfigurations) {
        this.defaultConfigurations = Arrays.asList(defaultConfigurations.split(", "));
    }

    @Value("${custom.seed.data.configurations:}")
    public void setCustomConfigurations(String customConfigurations) {
        this.customConfigurations = Arrays.asList(customConfigurations.split(", "));
    }

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
        Collection<String> configurations = CollectionUtils.union(defaultConfigurations, customConfigurations);
        if (log.isDebugEnabled()) {
            for (String configuration : configurations) {
                log.debug("Configuration ENABLED: " + configuration);
            }
        }
        seedDataService.loadSeedData(configurations);

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

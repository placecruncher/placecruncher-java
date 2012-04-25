package com.placecruncher.server.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.application.SeedData;
import com.placecruncher.server.dao.SeedDataLogDao;
import com.placecruncher.server.domain.SeedDataLog;

@Service
public class SeedDataService {
    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private SeedDataLogDao seedDataDao;

    @Autowired
    private List<SeedData> seedDataSet = Collections.emptyList();

    @Transactional
    public void loadSeedData(Collection<String> configurations) {
        if (log.isInfoEnabled()) {
            log.info("Processing seed data for configurations: " + StringUtils.join(configurations, ','));
        }
        for (SeedData seedData : seedDataSet) {
            if (seedData.getConfigurations().isEmpty() || CollectionUtils.containsAny(seedData.getConfigurations(), configurations)) {
            	SeedDataLog seedDataLog = seedDataDao.getByName(seedData.getName());
            	if ( seedDataLog == null || seedData.isRepeatable()) {
                    if (log.isInfoEnabled()) {
                        log.info("Populating seed data " + seedData.getName());
                    }
                    seedData.populate();

            		if (seedDataLog == null) {
            			seedDataLog = new SeedDataLog();
            			seedDataLog.setName(seedData.getName());
            			seedDataDao.persist(seedDataLog);
            		}
            	} else {
            		if (log.isInfoEnabled()) {
                        log.info("Seed data " + seedData.getName() + " has already been loaded");
                    }
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Seed data " + seedData.getName() + " is not enabled for this configuration");
                }
            }
        }
    }

}

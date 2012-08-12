package com.placecruncher.server.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.application.SeedData;
import com.placecruncher.server.dao.SeedDataLogDao;
import com.placecruncher.server.domain.SeedDataLog;

@Service
public class SeedDataService {
    private final Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private SeedDataLogDao seedDataDao;

    @Autowired
    private List<SeedData> seedDataSet = Collections.emptyList();

    @Transactional
    public void loadSeedData(Collection<String> configurations) {

        // Sort the list
        Collections.sort(seedDataSet, new OrderComparator());

        if (log.isInfoEnabled()) {
            StringBuilder sb = new StringBuilder("Processing seed data for configurations: [");
            for (String config : configurations) {
                sb.append('"').append(config).append("\" ");
            }
            sb.append(']');
            log.info(sb.toString());
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
                    log.debug("Skipping seed data " + seedData.getName() + ", only enabled for the following configurations [" + StringUtils.join(seedData.getConfigurations(),", ") + "]");
                }
            }
        }
    }

}

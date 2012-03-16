package com.placecruncher.server.application;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.dao.SourceDao;
import com.placecruncher.server.domain.Source;

/**
 * Load seed data into the system.
 */
public class SeedData implements ApplicationListener<ContextRefreshedEvent>{
    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private SourceDao sourceDao;


    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Initializing Seed Data...");
        initialize();
        log.info("Seed data initialized.");
    }

    private Source createSource() {
        Source source = new Source();
        source.setName("Test");
        source.setStatus(Source.StatusEnum.OPEN);
        return sourceDao.load(sourceDao.persist(source));
    }
    @Transactional
    public void initialize() {
        createSource();


    }
}

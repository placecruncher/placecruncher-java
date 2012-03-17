package com.placecruncher.server.application;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placecruncher.server.dao.SourceDao;
import com.placecruncher.server.domain.Source;

@Service
public class DemoSeedData implements SeedData {

    @Autowired
    private SourceDao sourceDao;

    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    public String getName() {
        return "Demo Data";
    }

    public Collection<String> getConfigurations() {
        return Arrays.asList(new String[] {"demo"});
    }

    private Source createSource(String name) {
        Source source = new Source();
        source.setName(name);
        return sourceDao.load(sourceDao.persist(source));
    }
    public void populate() {
        // TODO Auto-generated method stub

    }

}

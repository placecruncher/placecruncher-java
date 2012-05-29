package com.placecruncher.server.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placecruncher.server.domain.AbstractEntityFactory;
import com.placecruncher.server.domain.Source;

@Component
public class SourceFactory extends AbstractEntityFactory<Source> {

    @Autowired
    private SourceDao sourceDao;

    public SourceDao getDao() {
        return sourceDao;
    }
    
    public Source buildDefaultObject(String key) {
        Source source = new Source();
        source.setName("Source " + key);
        source.setUrl("http://places.google.com/" + key);
        source.setDescription("Description of Test Source #" + key);
        source.setTitle("Test Source " + key);
        return source;
    }
}

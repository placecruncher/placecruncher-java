package com.placecruncher.server.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placecruncher.server.domain.AbstractEntityFactory;
import com.placecruncher.server.domain.Source;

@Component
public class SourceFactory extends AbstractEntityFactory<Source> {
    private static int COUNTER = 0;

    @Autowired
    private SourceDao sourceDao;

    public SourceDao getDao() {
        return sourceDao;
    }
    
    public Source build(Map<String, Object> properties) {
    	int counter = COUNTER++;

        Source source = new Source();
        populate(source, properties);
        source.setName("Source " + counter);
        source.setUrl("http://places.google.com/" + counter);
        source.setDescription("Description of Test Source #" + counter);
        source.setTitle("Test Source " + counter);
        return source;
    }
}

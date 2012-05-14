package com.placecruncher.server.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placecruncher.server.domain.EntityFactory;
import com.placecruncher.server.domain.Source;

@Component
public class SourceFactory implements EntityFactory<Source> {
    private static int COUNTER = 0;

    @Autowired
    private SourceDao sourceDao;

    public Source create(Object... values) {
        Source source = build(values);
        return sourceDao.load(sourceDao.persist(source));
    }

    public Source build(Object... values) {
    	int counter = COUNTER++;

        Source source = new Source();
        for (Object value : values) {
            if (value instanceof Source.StatusEnum) {
                source.setStatus((Source.StatusEnum)value);
            }
        }
        source.setName("Source " + counter);
        source.setUrl("http://places.google.com/" + counter);
        source.setDescription("Description of Test Source #" + counter);
        source.setTitle("Test Source " + counter);
        return source;
    }
}

package com.placecruncher.server.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placecruncher.server.domain.EntityFactory;
import com.placecruncher.server.domain.Source;

@Component
public class SourceFactory implements EntityFactory<Source> {
    @Autowired
    private SourceDao sourceDao;

    public Source create(Object... values) {
        Source source = build(values);
        return sourceDao.load(sourceDao.persist(source));
    }

    public Source build(Object... values) {
        Source source = new Source();
        for (Object value : values) {
            if (value instanceof Source.StatusEnum) {
                source.setStatus((Source.StatusEnum)value);
            }
        }
        source.setName("Some name");
        return source;
    }
}

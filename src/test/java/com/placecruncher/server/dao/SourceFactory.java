package com.placecruncher.server.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placecruncher.server.domain.AbstractEntityFactory;
import com.placecruncher.server.domain.Source;

@Component
public class SourceFactory extends AbstractEntityFactory<Source> {

    @Autowired
    public SourceFactory(SourceDao dao) {
        super(dao);
    }

    public Source instance(int id, Map<String, Object> properties) {
        Source source = new Source();
        source.setName("Source " + id);
        source.setUrl("https://www.google.com/search?btnI=I%27m+Feeling+Lucky&q=" + id);
        source.setDescription("Description of Test Source #" + id);
        source.setTitle("Test Source " + id);
        return source;
    }
}

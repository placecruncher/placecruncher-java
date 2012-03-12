package com.placecruncher.server.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.Place;
import com.placecruncher.server.domain.Source;


@Repository
public class PlaceDao extends AbstractDao<Integer, Place> {

    public PlaceDao() {
        super(Place.class);
    }

    @SuppressWarnings("unchecked")
    public List<Place> findBySource(Source source) {
        Query query = createQuery("from Place p where :source member of p.sources");
        query.setEntity("source", source);
        return query.list();

    }
}

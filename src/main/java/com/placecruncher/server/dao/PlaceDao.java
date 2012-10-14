package com.placecruncher.server.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Place;
import com.placecruncher.server.domain.PlaceList;
import com.placecruncher.server.domain.PlaceRef;
import com.placecruncher.server.domain.Source;
import com.placecruncher.server.domain.SourcePlaceList;


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

    public Place findByExample(Place place) {
        // This will need to be made much smarter.
        Query query = createQuery("from Place p where p.name = :name");
        query.setString("name", place.getName());
        return (Place)query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<Place> findByMember(Member member) {
        Query query = createQuery("select distinct pr.place from PlaceRef pr where pr.member = :member");
        query.setEntity("member", member);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Place> findByList(PlaceList placeList) {
        Query query = createQuery("select distinct pr.place from PlaceRef pr where pr.placeList = :placeList");
        query.setEntity("placeList", placeList);
        return query.list();
    }

    public PlaceList createPlaceList(Source source, Member member) {
        SourcePlaceList placeList = new SourcePlaceList();
        placeList.setMember(member);
        placeList.setSource(source);
        getCurrentSession().persist(placeList);
        for (Place place : findBySource(source)) {
            PlaceRef ref = new PlaceRef();
            ref.setMember(member);
            ref.setPlace(place);
            ref.setPlaceList(placeList);
            getCurrentSession().persist(ref);
        }
        return placeList;
    }

    public void removePlaceList(Member member, Source source) {
        // Delete all place references for the member that reference the source
        Query query = createQuery("delete from PlaceRef pr where pr.placeList in (from SourcePlaceList as pl where pl.member = :member and pl.source = :source)");
        query.setParameter("member", member);
        query.setParameter("source", source);
        query.executeUpdate();

        // Delete all place lists for the member that reference the source
        query = createQuery("delete from SourcePlaceList pl where pl.member = :member and pl.source = :source");
        query.setParameter("member", member);
        query.setParameter("source", source);
        query.executeUpdate();
    }

    public Source findSourceByMember(Member member, Source source) {
        Query query = createQuery("select distinct pl.source from SourcePlaceList pl where pl.member = :member and pl.source = :source");
        query.setParameter("member", member);
        query.setParameter("source", source);
        return (Source)query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<Source> findSourcesByMember(Member member) {
        Query query = createQuery("select distinct pl.source from SourcePlaceList pl where pl.member = :member");
        query.setParameter("member", member);
        return (List<Source>)query.list();
    }

}

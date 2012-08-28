package com.placecruncher.server.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
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
        for (SourcePlaceList placeList : findSourcePlaceList(member, source)) {
            Query query = createQuery("delete from PlaceRef pr where pr.member = :member and pr.placeList = :placeList");
            query.setParameter("member", member);
            query.setParameter("placeList", placeList);
            query.executeUpdate();

            getCurrentSession().delete(placeList);
        }
    }

    @SuppressWarnings("unchecked")
    // DSDXXX build a filter and pagination class instead of passing in filter parameters
    // DSDXXX Should place lists be unique based on source?  I don't think this should be a list or maybe it should
    public List<SourcePlaceList> findSourcePlaceList(Member member, Source source) {
      Criteria criteria = getCurrentSession().createCriteria(SourcePlaceList.class);
      criteria.add(Restrictions.eq("member", member));
      if (source != null) {
          criteria.add(Restrictions.eq("source", source));
      }
      return criteria.list();
    }



}

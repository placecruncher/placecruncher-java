package com.placecruncher.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.placecruncher.server.application.Constants;


@Entity
@Table(name="PLACE_REF")
public class PlaceRef extends SuperEntity {
    private Integer id;
    private Member member;
    private Place place;
    private PlaceList placeList;
    private Privacy privacy = Privacy.PRIVATE;
    private int rating;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable=false)
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "memberId", nullable = false)
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }


    @OneToOne
    @JoinColumn(name = "placeId", nullable = false)
    public Place getPlace() {
        return place;
    }
    public void setPlace(Place place) {
        this.place = place;
    }

    @Column(nullable=false, length=Constants.ENUM_MAXLEN)
    @Enumerated(EnumType.STRING)
    public Privacy getPrivacy() {
        return privacy;
    }
    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    @Column(nullable=false)
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    @OneToOne
    @JoinColumn(name = "placeListId", nullable = true)
    public PlaceList getPlaceList() {
        return placeList;
    }
    public void setPlaceList(PlaceList placeList) {
        this.placeList = placeList;
    }

}

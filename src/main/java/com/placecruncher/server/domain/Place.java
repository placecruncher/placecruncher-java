package com.placecruncher.server.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.placecruncher.server.application.Constants;


/**
 * A Place
 */
@Entity
@Table(name="PLACE")
public class Place extends SuperEntity {

    private Integer id;
    private String name;
    private String title;
    private String description;
    private String address;
    private String city;
    private String state;
    private String country;
    private String phone;
    private String url;
    // TODO zipcode

    private Set<Source> sources = new HashSet<Source>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable=false)
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable=false, length=Constants.NAME_MAXLEN)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length=Constants.TITLE_MAXLEN)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(length=Constants.DESCRIPTION_MAXLEN)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(length = Constants.ADDRESS_MAXLEN)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(length = Constants.CITY_MAXLEN)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(length = Constants.STATE_MAXLEN)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(length = Constants.COUNTRY_MAXLEN)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(length = Constants.PHONE_MAXLEN)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(length = Constants.URL_MAXLEN)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToMany
    @JoinTable(name = "PLACE_SOURCE",
        joinColumns = {@JoinColumn(name = "placeId")},
        inverseJoinColumns = {@JoinColumn(name = "sourceId")})
    public Set<Source> getSources() {
        return sources;
    }

    private void setSources(Set<Source> sources) {
        this.sources = sources;
    }

}

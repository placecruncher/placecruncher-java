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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.placecruncher.server.application.Constants;


/**
 * A Place
 */
@Entity
@Table(name=Place.DB_TABLE)
@SequenceGenerator(name = Place.DB_SEQ, sequenceName = Place.DB_SEQ)
public class Place extends AbstractEntity {

    public static final String DB_TABLE = "PLACE";
    public static final String DB_SEQ = DB_TABLE + "_SEQ";

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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = DB_SEQ)
    @Column(name="ID", nullable=false)
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    @Column(name="NAME", nullable=false, length=Constants.NAME_MAXLEN)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="TITLE", length=Constants.TITLE_MAXLEN)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name="DESCRIPTION", length=Constants.DESCRIPTION_MAXLEN)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="ADDRESS", length = Constants.ADDRESS_MAXLEN)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name="CITY", length = Constants.CITY_MAXLEN)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name="STATE", length = Constants.STATE_MAXLEN)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name="COUNTRY", length = Constants.COUNTRY_MAXLEN)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name="PHONE", length = Constants.PHONE_MAXLEN)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name="URL", length = Constants.URL_MAXLEN)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToMany
    @JoinTable(name = "PLACE_SOURCE",
        joinColumns = {@JoinColumn(name = "PLACE_ID")},
        inverseJoinColumns = {@JoinColumn(name = "SOURCE_ID")})
    public Set<Source> getSources() {
        return sources;
    }

    private void setSources(Set<Source> sources) {
        this.sources = sources;
    }

}

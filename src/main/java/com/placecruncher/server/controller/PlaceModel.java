package com.placecruncher.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Transformer;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.BeanUtils;

import com.placecruncher.server.domain.Place;
import com.placecruncher.server.util.TransformUtils;

public class PlaceModel {
    public static final TypeReference<List<PlaceModel>> LIST_TYPE = new TypeReference<List<PlaceModel>>(){};

    private Integer id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private String phone;
    private String url;

    public static List<PlaceModel> transform(List<Place> places) {
        return TransformUtils.transform(places, new Transformer() {
            public Object transform(Object input) {
                return new PlaceModel((Place) input);
            }
        }, new ArrayList<PlaceModel>());
    }

    public PlaceModel() {
    }

    public PlaceModel(Place place) {
        BeanUtils.copyProperties(place, this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}

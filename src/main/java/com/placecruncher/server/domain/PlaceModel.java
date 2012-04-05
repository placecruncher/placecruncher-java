package com.placecruncher.server.domain;

import java.util.Collection;

import org.apache.commons.collections.Transformer;
import org.springframework.beans.BeanUtils;

import com.placecruncher.server.util.TransformUtils;

public class PlaceModel {
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;
    private String phone;
    private String url;

	public static Collection<PlaceModel> transform(Collection<Place> places) {
		return TransformUtils.transform(places, new Transformer() {
			public Object transform(Object input) {
				return new PlaceModel((Place)input);
			}
		});
	}

	public PlaceModel() {}

	public PlaceModel(Place place) {
    	BeanUtils.copyProperties(place, this);
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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


}

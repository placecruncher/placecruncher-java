package com.placecruncher.server.domain;

import java.util.Collection;

import org.apache.commons.collections.Transformer;
import org.springframework.beans.BeanUtils;

import com.placecruncher.server.util.TransformUtils;

public class PlaceModel {
	private Integer id;
    private String name;
    private String address;
    private String location;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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

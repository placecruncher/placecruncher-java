package com.placecruncher.server.domain;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.Transformer;
import org.springframework.beans.BeanUtils;
import org.springframework.util.AutoPopulatingList;

import com.placecruncher.server.domain.Source.StatusEnum;
import com.placecruncher.server.util.TransformUtils;

public class SourceModel {
	private String url;
    private String name;
    private String title;
    private String description;
    private StatusEnum status = StatusEnum.OPEN;
    private List<PlaceModel> places = new AutoPopulatingList<PlaceModel>(PlaceModel.class);

	public static Collection<SourceModel> transform(Collection<Source> sources) {
		return TransformUtils.transform(sources, new Transformer() {
			public Object transform(Object input) {
				return new SourceModel((Source)input);
			}
		});
	}

	public SourceModel() {}

    public SourceModel(Source source) {
    	BeanUtils.copyProperties(source, this);
    	places.addAll(PlaceModel.transform(source.getPlaces()));
    }

    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public List<PlaceModel> getPlaces() {
		return places;
	}


}

package com.placecruncher.server.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Transformer;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.BeanUtils;

import com.placecruncher.server.controller.EntityModel;
import com.placecruncher.server.domain.Source.StatusEnum;
import com.placecruncher.server.util.TransformUtils;

public class SourceModel  extends EntityModel {
    public static final TypeReference<List<SourceModel>> LIST_TYPE = new TypeReference<List<SourceModel>>(){};

    private Integer id;
    private String url;
    private String name;
    private String title;
    private String description;
    private StatusEnum status = StatusEnum.OPEN;

    public static List<SourceModel> transform(List<Source> sources) {
        return TransformUtils.transform(sources, new Transformer() {
            public Object transform(Object input) {
                return new SourceModel((Source)input);
            }
        }, new ArrayList<SourceModel>());
    }

    public SourceModel() {}

    public SourceModel(Source source) {
        BeanUtils.copyProperties(source, this);
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

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return getUrl() == null ? 0 : getUrl().hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;

        String thatUrl = ((SourceModel) obj).getUrl();
        return this.getUrl() == null ? thatUrl == null : this.getUrl().equals(thatUrl);
    }


}

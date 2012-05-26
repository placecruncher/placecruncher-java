package com.placecruncher.server.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Configurable;

import com.placecruncher.server.application.Constants;

@Entity
@Table(name=Source.DB_TABLE)
@SequenceGenerator(name = Source.DB_SEQ, sequenceName = Source.DB_SEQ)
@Configurable(dependencyCheck = true)
public class Source extends AbstractEntity {
    public static final String DB_TABLE = "SOURCE";
    public static final String DB_SEQ = DB_TABLE + "_SEQ";

    private Integer id;
    private String url;
    private String name;
    private String title;
    private String description;
    private StatusEnum status = StatusEnum.OPEN;
    private Collection<Place> places = new ArrayList<Place>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = DB_SEQ)
    @Column(name="ID", nullable=false)
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    @Column(name="URL", nullable = false, unique = true, updatable = false, length = Constants.URL_MAXLEN)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Column(name="STATUS", nullable=false, length=Constants.ENUM_MAXLEN)
    @Enumerated(EnumType.STRING)
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @ManyToMany(mappedBy="sources")
    public Collection<Place> getPlaces() {
    	return places;
    }
    @SuppressWarnings("unused")  // Hide the setter
    private void setPlaces(Collection<Place> places) {
    	this.places = places;
    }
    

    public enum StatusEnum {
        OPEN,
        IN_PROGRESS,
        CLOSED
    }

}

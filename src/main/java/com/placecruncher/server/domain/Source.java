package com.placecruncher.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Configurable;

import com.placecruncher.server.application.Constants;

@Entity
@Table(name="SOURCE")
@Configurable(dependencyCheck = true)
public class Source extends SuperEntity {

    private Integer id;
    private String url;
    private String name;
    private String title;
    private String description;
    private StatusEnum status = StatusEnum.OPEN;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable=false)
    public Integer getId() {
        return id;
    }

    // DSDXXX I wonder if this always needs to be public for the bean property copy
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false, unique = true, updatable = false, length = Constants.URL_MAXLEN)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Column(nullable=false, length=Constants.ENUM_MAXLEN)
    @Enumerated(EnumType.STRING)
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public enum StatusEnum {
        OPEN,
        IN_PROGRESS,
        CLOSED
    }

}

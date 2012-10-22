package com.placecruncher.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Table(name="SOURCE_META")
@Configurable(dependencyCheck = true)
public class SourceMeta extends SuperEntity {
    private Integer id;
    
    private String name;
    private String value;
    
    private Source source;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable=false)
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sourceId")
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
    
}

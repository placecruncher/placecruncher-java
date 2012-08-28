package com.placecruncher.server.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

// DSDXXX get rid of the Hibernate inheritance stuff and make one big object
@Entity
@DiscriminatorValue("SOURCE")
public class SourcePlaceList extends PlaceList {
    private Source source;

    @OneToOne
    @JoinColumn(name = "sourceId", nullable = false)
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

}

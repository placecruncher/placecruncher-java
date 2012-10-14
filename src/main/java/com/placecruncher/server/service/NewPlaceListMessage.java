package com.placecruncher.server.service;

import com.placecruncher.server.domain.NotificationMessage;
import com.placecruncher.server.domain.SourceModel;

public class NewPlaceListMessage extends NotificationMessage {
    private SourceModel source;

    public SourceModel getSource() {
        return source;
    }

    public void setSource(SourceModel source) {
        this.source = source;
    }

    @Override
    public String getText() {
        return "New places have been added from '" + source.getName() + "'";
    }


}

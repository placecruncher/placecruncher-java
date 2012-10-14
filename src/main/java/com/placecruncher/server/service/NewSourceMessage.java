package com.placecruncher.server.service;

import java.util.ArrayList;
import java.util.List;

import com.placecruncher.server.domain.NotificationMessage;
import com.placecruncher.server.domain.SourceModel;

public class NewSourceMessage extends NotificationMessage {
    private String title;
    private List<SourceModel> sources = new ArrayList<SourceModel>();

    public NewSourceMessage(String title) {
        this.title = title;
    }

    @Override
    public String getText() {
        return "Processing places found in '" + title + "'";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SourceModel> getSources() {
        return sources;
    }
    protected void setSources(List<SourceModel> sources) {
        this.sources = sources;
    }
    public void addSource(SourceModel source) {
        sources.add(source);
    }


}

package com.placecruncher.server.controller;


public class ResponseContainer {

    private Meta meta;
    private Object response;

    public Meta getMeta() {
        return meta;
    }
    public void setMeta(Meta meta) {
        this.meta = meta;
    }
    public void setResponse(Object response) {
        this.response = response;
    }
    public Object getResponse() {
        return response;
    }
}


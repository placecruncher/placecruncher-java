// Copyright 2011 INQ Mobile. All rights reserved.
package com.placecruncher.server.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResponsePayload {
    private Meta meta;
    private Response response;

    public ResponsePayload() {
        this.meta = new Meta();
    }

    public ResponsePayload(Meta meta) {
        this.meta = meta;
    }

    public ResponsePayload(Response response) {
        this.meta = new Meta();
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponsePayload [meta=" + meta + ", response=" + response + "]";
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }





}


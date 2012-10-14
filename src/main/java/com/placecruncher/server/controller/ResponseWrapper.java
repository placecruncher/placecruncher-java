package com.placecruncher.server.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

// DSDXXX Try to get rid of this and have the ObjectMapper generate the Meta section, then we can return POJOS
@JsonInclude(Include.NON_NULL)
public class ResponseWrapper<T> {
    private Meta meta = new Meta();
    private T response;

    // Called by the JSON deserializer
    protected ResponseWrapper() {
    }

    public ResponseWrapper(T response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponsePayload [meta=" + meta + ", response=" + response + "]";
    }

    public Meta getMeta() {
        return meta;
    }

    public T getResponse() {
        return response;
    }

}

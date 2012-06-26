// Copyright 2011 INQ Mobile. All rights reserved.
package com.placecruncher.server.exception;


/**
 * Default runtime exception for cloud.
 */
public class PlacecruncherRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    
    public PlacecruncherRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }


    public PlacecruncherRuntimeException(String msg) {
        super(msg);
    }


    public PlacecruncherRuntimeException(Throwable cause) {
        super(cause);
    }
}

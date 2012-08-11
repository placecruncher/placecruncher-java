package com.placecruncher.server.exception;


/**
 * Thrown when a resource is not found.  Translated to a 400 by the exception resolver.
 */
public class ResourceNotFoundException extends PlacecruncherRuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

}

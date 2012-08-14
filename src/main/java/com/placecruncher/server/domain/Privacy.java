package com.placecruncher.server.domain;

public enum Privacy {
    PUBLIC,
    PRIVATE;

    public boolean isPublic() {
        return PUBLIC == this;
    }
    public boolean isPrivate() {
        return PRIVATE == this;
    }
}


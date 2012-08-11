package com.placecruncher.server.domain;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MemberRole {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private static final Map<String, MemberRole> LOOKUP = new HashMap<String, MemberRole>();

    static {
        for (MemberRole r : EnumSet.allOf(MemberRole.class)) {
            LOOKUP.put(r.getName(), r);
        }
    }

    public boolean isAdmin() {
        return this == ROLE_ADMIN;
    }

    private String name;

    private MemberRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MemberRole get(String authority) {
        return LOOKUP.get(authority);
    }
}

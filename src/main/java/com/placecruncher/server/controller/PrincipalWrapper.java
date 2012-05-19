package com.placecruncher.server.controller;

import com.placecruncher.server.domain.Principal;

public class PrincipalWrapper extends Response {
    private Principal principal;

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @Override
    public String toString() {
        return "PrincipalWrapper [principal=" + principal + "]";
    }
    
    
}

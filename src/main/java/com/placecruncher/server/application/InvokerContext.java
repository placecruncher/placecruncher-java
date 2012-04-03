package com.placecruncher.server.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.placecruncher.server.domain.Principal;

public interface InvokerContext {
    String BEAN_NAME = "invokerContext";

    void initInvokerContext(HttpServletRequest request,
            HttpServletResponse response);
    
    void clear();
    
    Principal getPrincipal();
    
    void setPrincipal(Principal principal);    
}

package com.placecruncher.server.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.placecruncher.server.domain.Member;

public interface InvokerContext {
    String BEAN_NAME = "invokerContext";

    void initInvokerContext(HttpServletRequest request,
            HttpServletResponse response);
    
    void clear();
    
    Member getMember();
    
    void setMember(Member member);    
}

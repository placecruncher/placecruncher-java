package com.placecruncher.server.dao;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placecruncher.server.domain.AbstractEntityFactory;
import com.placecruncher.server.domain.Principal;

@Component
public class PrincipalFactory extends AbstractEntityFactory<Principal> {
    private static int COUNTER = 0;

    @Autowired
    private PrincipalDao principalDao;
    
    @Autowired
    private MemberFactory memberFactory;

    public PrincipalDao getDao() {
        return principalDao;
    }
    
    public Principal build(Map<String, Object> properties) {
    	int counter = COUNTER++;

    	Principal principal = new Principal();
    	populate(principal, properties);
    	
    	if (StringUtils.isEmpty(principal.getUsername())) {
    	    principal.setUsername("user" + counter);
    	}
    	if (principal.getMember() == null) {
    	    // DSDXXX I'm not liking that I MUST initialize the member object here.
    	    principal.setMember(memberFactory.create());
    	}
    	return principal;
    }
}

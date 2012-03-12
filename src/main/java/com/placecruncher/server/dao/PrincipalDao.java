package com.placecruncher.server.dao;

import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.Principal;

@Repository
public class PrincipalDao extends AbstractDao<Integer, Principal> {

    public PrincipalDao() {
        super(Principal.class);
    }

}

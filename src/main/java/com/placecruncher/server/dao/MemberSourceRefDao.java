package com.placecruncher.server.dao;

import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.MemberSourceRef;

@Repository
public class MemberSourceRefDao extends AbstractDao<Integer, MemberSourceRef> {
    
    public MemberSourceRefDao() {
        super(MemberSourceRef.class);
    }

}

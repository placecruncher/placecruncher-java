package com.placecruncher.server.dao;

import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.MemberSourceRef;

// DSDXXX I think we can kill this class and just do DAO stuff directly from MemberDao

@Repository
public class MemberSourceRefDao extends AbstractDao<Integer, MemberSourceRef> {

    public MemberSourceRefDao() {
        super(MemberSourceRef.class);
    }


}

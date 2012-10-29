package com.placecruncher.server.dao;

import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.SourceMeta;

@Repository
public class SourceMetaDao extends AbstractDao<Integer, SourceMeta> {
    public SourceMetaDao() {
        super(SourceMeta.class);
    }
}

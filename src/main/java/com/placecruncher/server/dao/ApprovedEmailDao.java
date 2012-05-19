package com.placecruncher.server.dao;

import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.ApprovedEmail;

@Repository
public class ApprovedEmailDao extends AbstractDao<Integer, ApprovedEmail> {
    public ApprovedEmailDao() {
        super(ApprovedEmail.class);
    }

}

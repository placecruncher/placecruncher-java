package com.placecruncher.server.dao;

import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.Email;


@Repository
public class EmailDao extends AbstractDao<Integer, Email> {

    public EmailDao() {
        super(Email.class);
    }
}

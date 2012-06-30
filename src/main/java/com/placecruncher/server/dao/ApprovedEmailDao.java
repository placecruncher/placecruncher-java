package com.placecruncher.server.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.placecruncher.server.domain.ApprovedEmail;

@Repository
public class ApprovedEmailDao extends AbstractDao<Integer, ApprovedEmail> {
    public ApprovedEmailDao() {
        super(ApprovedEmail.class);
    }
    
    public ApprovedEmail findByEmail(String email) {
        // This will need to be made much smarter.
        Query query = createQuery("from ApprovedEmail ae where ae.email = :email");
        query.setString("email", email);
        return (ApprovedEmail) query.uniqueResult();
    }

}

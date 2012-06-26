package com.placecruncher.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.dao.ApprovedEmailDao;
import com.placecruncher.server.dao.MemberDao;
import com.placecruncher.server.domain.ApprovedEmail;
import com.placecruncher.server.domain.Email;
import com.placecruncher.server.domain.Member;

@Service
public class EmailService {
    
    @Autowired
    private MemberDao memberDao;
    
    @Autowired
    private ApprovedEmailDao approvedEmailDao;
    
    
    @Transactional
    public void receviceEmail() {
        Email email = new Email();
        email.setSender("aefsdfsdf");
        email.store();
        
    }
    
    @Transactional
    public Member findMember(String email) {
        Member member = memberDao.findByEmail(email);
        if (member == null) {
            ApprovedEmail approvedEmail = approvedEmailDao.findByEmail(email);
            if (approvedEmail != null) {
                member = approvedEmail.getMember();
            }
        }
        return member;
    }
}

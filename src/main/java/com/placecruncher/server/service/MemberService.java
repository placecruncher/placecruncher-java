package com.placecruncher.server.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.domain.ApprovedEmail;
import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Principal;

@Service
public class MemberService {
    
    @Transactional
    public String registerUser(String userName, String password, String email) {
        UUID token = UUID.randomUUID();
        Member member = new Member();
        member.setUsername(userName);
        member.setPassword(password);
        member.setToken(token.toString());
        member.setEmail(email);
        member.saveOrUpdate();
        
        ApprovedEmail approvedEmail = new ApprovedEmail();
        approvedEmail.setEmail(email);
        approvedEmail.setMember(member);
        approvedEmail.saveOrUpdate();
        
        return token.toString();
    }

}

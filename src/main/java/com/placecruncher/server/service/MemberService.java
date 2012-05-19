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
        Principal principal = new Principal();
        principal.setUsername(userName);
        principal.setPassword(password);
        principal.setToken(token.toString());
        Member member = new Member();
        member.setEmail(email);
        member.saveOrUpdate();
        
        principal.setMember(member);
        principal.saveOrUpdate();
        
        ApprovedEmail approvedEmail = new ApprovedEmail();
        approvedEmail.setEmail(email);
        approvedEmail.setPrincipal(principal);
        approvedEmail.saveOrUpdate();
        
        return token.toString();
    }

}

package com.placecruncher.server.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.domain.ApprovedEmail;
import com.placecruncher.server.domain.Device;
import com.placecruncher.server.domain.Member;

@Service
public class MemberService {
    
	
    @Transactional
    public String registerUser(String userName, String password, String email, Device device) {
        UUID token = UUID.randomUUID();
        Member member = new Member();
        member.setUsername(userName);
        member.setPassword(password);
        member.setToken(token.toString());
        member.setEmail(email);
        
        member.saveOrUpdate();
        
        if (device != null) {
            device.setMember(member);
            device.saveOrUpdate();
        }
        
        ApprovedEmail approvedEmail = new ApprovedEmail();
        approvedEmail.setEmail(email);
        approvedEmail.setMember(member);
        approvedEmail.saveOrUpdate();
        
        return token.toString();
    }
    
    @Transactional
    public void registerDevice(Member member, Device device) {
        member.registerDevice(device);
    }

}

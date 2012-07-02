package com.placecruncher.server.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.dao.MemberDao;
import com.placecruncher.server.domain.ApprovedEmail;
import com.placecruncher.server.domain.Device;
import com.placecruncher.server.domain.Member;

@Service
public class MemberService {
    private final Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private MemberDao memberDao;


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

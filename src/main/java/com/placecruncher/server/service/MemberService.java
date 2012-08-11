package com.placecruncher.server.service;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.dao.MemberDao;
import com.placecruncher.server.dao.SourceDao;
import com.placecruncher.server.domain.ApprovedEmail;
import com.placecruncher.server.domain.Device;
import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.MemberRole;
import com.placecruncher.server.domain.Source;

@Service
public class MemberService {
    private final Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private SourceDao sourceDao;

    @Transactional
    public String registerUser(String userName, String password, String email, Device device) {
        return registerUser(MemberRole.ROLE_USER, userName, password, email, device);
    }

    @Transactional
    public String registerUser(MemberRole role, String userName, String password, String email, Device device) {

        Member member = memberDao.findByUserName(userName);
        if (member == null) {
            if (log.isDebugEnabled()) {
                log.debug("Create new member: username=" + userName + ", email=" + email);
            }

            member = new Member();
            member.setUsername(userName);
            UUID token = UUID.randomUUID();
            member.setToken(token.toString());
            member.saveOrUpdate();

            if (device != null) {
                device.setMember(member);
                device.saveOrUpdate();
            }

            ApprovedEmail approvedEmail = new ApprovedEmail();
            approvedEmail.setEmail(email);
            approvedEmail.setMember(member);
            approvedEmail.saveOrUpdate();

        }
        member.setPassword(password);
        member.setEmail(email);
        member.setMemberRole(role);

        return member.getToken();
    }

    @Transactional
    public void registerDevice(Member member, Device device) {
        member.registerDevice(device);
    }


    @Transactional
    public boolean removeSourceReference(Member member, Source source) {
        return sourceDao.removeReference(source, member);
    }

    @Transactional
    public boolean addSourceReference(Member member, Source source) {
        if (sourceDao.hasReference(source, member)) {
            return false;
        }
        sourceDao.addReference(source, member);

        return true;
    }
}

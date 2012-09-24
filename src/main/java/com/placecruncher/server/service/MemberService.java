package com.placecruncher.server.service;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.dao.MemberDao;
import com.placecruncher.server.dao.PlaceDao;
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
    private PlaceDao placeDao;

    @Autowired
    private MailGunService mailGunService;

    @Transactional
    public Member registerUser(String userName, String password, String email, Device device) {
        return registerUser(MemberRole.ROLE_USER, userName, password, email, device);
    }

    @Transactional
    public Member registerUser(MemberRole role, String userName, String password, String email, Device device) {

        Member member = memberDao.findByUserName(userName);
        if (member == null) {
            if (log.isDebugEnabled()) {
                log.debug("Create new member: username=" + userName + ", email=" + email);
            }

            member = new Member();
            member.setUsername(userName);
            UUID token = UUID.randomUUID();
            member.setToken(token.toString());
            member.setPassword(password);
            member.setEmail(email);
            member.setMemberRole(role);
            mailGunService.createMailBox(userName, password);
            member.setPlacecruncherEmail(userName + "@placecruncher.mailgun.org");
            member.saveOrUpdate();

            if (device != null) {
                device.setMember(member);
                device.saveOrUpdate();
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Member found: username=" + userName + ", email=" + email);
            }
            return null;
        }
        return member;
    }

    @Transactional
    public void registerDevice(Member member, Device device) {
        member.registerDevice(device);
    }


    @Transactional
    public void removeSource(Member member, Source source) {
        placeDao.removePlaceList(member, source);
    }

    @Transactional
    public void createTestMailBox() {
        mailGunService.createMailBox("davidtest@placecruncher.mailgun.org", "testpassword");
    }
}

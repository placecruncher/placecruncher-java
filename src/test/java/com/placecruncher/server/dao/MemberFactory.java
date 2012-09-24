package com.placecruncher.server.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placecruncher.server.domain.AbstractEntityFactory;
import com.placecruncher.server.domain.Member;

@Component
public class MemberFactory extends AbstractEntityFactory<Member> {

    @Autowired
    public MemberFactory(MemberDao dao) {
        super(dao);
    }

    @Override
    public Member instance(int id, Map<String, Object> properties) {
        Member member = new Member();
        String username = "User" + id;
        member.setUsername(username);
        member.setPlacecruncherEmail(username);
        return member;
    }
}

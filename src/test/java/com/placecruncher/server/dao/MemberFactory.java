package com.placecruncher.server.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placecruncher.server.domain.AbstractEntityFactory;
import com.placecruncher.server.domain.Member;

@Component
public class MemberFactory extends AbstractEntityFactory<Member> {
    @Autowired
    private MemberDao memberDao;
    
    public MemberDao getDao() { 
        return memberDao;
    }
    
    @Override
    public Member build(Map<String, Object> properties) {
        Member member = new Member();
        populate(member, properties);
        return member;
    }
}

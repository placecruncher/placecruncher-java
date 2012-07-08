package com.placecruncher.server.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.placecruncher.server.dao.MemberDao;
import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.MemberDetails;

@Service
public class MemberDetailsService implements UserDetailsService {
    private final Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private MemberDao memberDao;


    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
        if (log.isDebugEnabled()) {
            log.debug("loadByUsername(" + userName + ")");
        }
        Member member = memberDao.findByUserName(userName);
        if (member != null) {
            log.info("Found user " + userName + ", " + member);
            return new MemberDetails(member);
        } else {
            log.info("Unable to locate user " + userName);
            throw new UsernameNotFoundException("User does not exist: " + userName);
        }
    }


}

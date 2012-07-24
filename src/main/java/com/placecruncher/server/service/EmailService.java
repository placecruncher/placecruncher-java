package com.placecruncher.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.dao.ApprovedEmailDao;
import com.placecruncher.server.dao.SourceDao;
import com.placecruncher.server.domain.ApprovedEmail;
import com.placecruncher.server.domain.Email;
import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Source;

@Service
public class EmailService {
    private final Logger log = Logger.getLogger(getClass());

    @Autowired
    private ApprovedEmailDao approvedEmailDao;

    @Autowired
    private SourceDao sourceDao;

    @Transactional
    public boolean receviceEmail(Email email) {
        boolean accepted;

        // Locate the member associated with the email
        Member member = findMember(email);

        if (member != null) {
            log.info("Received email from " + member.getUsername());

            // Store email
            // email.store();

            // Extract URLs from email
            Set<String> urls = email.extractUrls();

            // Find or create sources for URLs
            List<Source> sources = new ArrayList<Source>();
            for (String url : urls) {
                // Locate existing sources for URLs
                Source source = sourceDao.findByUrl(url);
                if (source == null ) {
                    source = new Source();
                    source.setTitle(email.getSubject());
                    source.setUrl(url);
                    sourceDao.persist(source);
                }
                sources.add(source);
            }

            // Link member to sources

            // Notify member
            member.processEmail(email);

            accepted = true;
        } else {
            log.info("Unable to find member for email " + email);
            accepted = false;
        }
        return accepted;

    }

    public Member findMember(Email email) {
        Member member = null;

        if (StringUtils.isNotBlank(email.getSender())) {
            ApprovedEmail approvedEmail = approvedEmailDao.findByEmail(email.getSender());
            if (approvedEmail != null) {
                member = approvedEmail.getMember();
            }
        }

        if (StringUtils.isNotBlank(email.getFrom())) {
            ApprovedEmail approvedEmail = approvedEmailDao.findByEmail(email.getFrom());
            if (approvedEmail != null) {
                member = approvedEmail.getMember();
            }
        }
        return member;
    }
}

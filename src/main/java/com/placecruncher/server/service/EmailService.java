package com.placecruncher.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placecruncher.server.domain.Email;

@Service
public class EmailService {
    
    @Transactional
    public void receviceEmail() {
        Email email = new Email();
        email.setSender("aefsdfsdf");
        email.store();
        
    }
}

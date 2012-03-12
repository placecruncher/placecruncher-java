package com.placecruncher.server.dao;

import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/appContext-dao-test.xml")
public abstract class DaoTestCase extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private SessionFactory sessionFactory;

    public void flush() {
        sessionFactory.getCurrentSession().flush();
    }
}

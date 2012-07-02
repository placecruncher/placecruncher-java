package com.placecruncher.server.service;

import java.util.Arrays;
import java.util.Collections;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.placecruncher.server.dao.SeedDataLogDao;

@RunWith(MockitoJUnitRunner.class)
public class SeedDataServiceTest {
    @Mock
    private SeedDataLogDao seedDataLogDao;

    @InjectMocks
    private final SeedDataService seedDataService = new SeedDataService();

    @Test
    public void testIsActiveConfiguration() {
        Assert.assertTrue(CollectionUtils.containsAny(Collections.singletonList("test"), Arrays.asList("demo", "test")));
    }
}

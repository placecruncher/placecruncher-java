package com.placecruncher.server.controller;

import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import com.placecruncher.server.application.data.SourceTestData;
import com.placecruncher.server.domain.SourceModel;

/**
 * Test case for members and their sources.
 */
public class MemberSourcesIT extends ApiTestCase {
//    private final Logger log = Logger.getLogger(getClass());

    private SourceModel testSource;
    private List<PlaceModel> testPlaces;
    private boolean initialized;

    @Before
    public void setUp() {

        if (!initialized) {
            loginAsAdmin();
            // Get the well known test source
            testSource = getForObject(PRIVATE_API + "/sources?url={url}", SourceModel.LIST_TYPE, SourceTestData.SOURCE_URL).get(0);
            Assert.assertNotNull(testSource);
            // Get the places for the source
            testPlaces = getForObject(PRIVATE_API + "/sources/{id}/places", PlaceModel.LIST_TYPE, testSource.getId());
            Assert.assertFalse(testPlaces.isEmpty());
            logout();
            initialized = true;
        }
    }

    private SourceModel getSource(String url) {
        List<SourceModel> sources = getForObject(PRIVATE_API + "/members/self/sources?url={url}", SourceModel.LIST_TYPE, url);
        if (!sources.isEmpty()) {
            return sources.get(0);
        } else {
            return null;
        }
    }

    private SourceModel addSource(String url) {
        return postForObject(PRIVATE_API + "/members/self/sources?url={url}", null, SourceModel.class, url);
    }

    private void removeSource(int id) {
        delete(PRIVATE_API + "/members/self/sources/{id}", id);
    }

    private List<PlaceModel> getPlaces() {
        return getForObject(PRIVATE_API + "/members/self/places", PlaceModel.LIST_TYPE);
    }

    private List<NotificationModel> getNotifications() {
        return getForObject(PRIVATE_API + "/members/self/notifications", NotificationModel.LIST_TYPE);
    }

    @Test
    public void addExistingSourceToMember() {
        loginAsMember();

        String sourceUrl = testSource.getUrl();
        SourceModel source = getSource(sourceUrl);
        if (source != null) {
            removeSource(source.getId());
        }

        // Member does not have source
        Assert.assertNull(getSource(sourceUrl));

        // Member does not have test places
        Assert.assertFalse(CollectionUtils.containsAny(testPlaces, getPlaces()));

        // DSDXXX need to improve the notifications API
        Collection<NotificationModel> existingNotifications = getNotifications();

        // Add source to member
        addSource(sourceUrl);

        // Member has source
        Assert.assertNotNull(getSource(sourceUrl));

        // Member has places associated with source
        Assert.assertTrue(CollectionUtils.isSubCollection(testPlaces, getPlaces()));

        // Member received a notification
        @SuppressWarnings("unchecked")
        Collection<NotificationModel> notifications = CollectionUtils.removeAll(existingNotifications, getNotifications());
        Assert.assertFalse(notifications.isEmpty());

    }

//    @Test
//    public void addNewSourceToMember() {
//
//        // Member does not have new source
//
//        // Add new source to member
//
//        // Member has new source waiting to be crunched
//
//        // Member receives notification of new source
//
//        Assert.fail("not implemented");
//
//    }

    @Test
    public void removeSourceFromMember() {
        loginAsMember();

        String sourceUrl = testSource.getUrl();
        SourceModel source = getSource(sourceUrl);
        if (source == null) {
            source = addSource(sourceUrl);
        }

        Assert.assertNotNull(getSource(sourceUrl));
        removeSource(source.getId());
        Assert.assertNull(getSource(sourceUrl));
    }



}

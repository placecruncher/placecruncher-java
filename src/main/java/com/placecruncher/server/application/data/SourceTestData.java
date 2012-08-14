package com.placecruncher.server.application.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.placecruncher.server.dao.MemberDao;
import com.placecruncher.server.dao.SourceDao;
import com.placecruncher.server.domain.Member;
import com.placecruncher.server.domain.Place;
import com.placecruncher.server.domain.Source;
import com.placecruncher.server.service.MemberService;
import com.placecruncher.server.service.SourceService;

/**
 * Create well known sources for integration testing.
 */
public class SourceTestData extends AbstractSeedData {
  private final Logger log = Logger.getLogger(getClass());

  public static final String SOURCE_URL = "www.sfgate.com/food/top100/2012/";
  public static final String SOURCE_NAME = "San Francisco Top 100 Restaurants";

  @Autowired
  private SourceService sourceService;

  @Autowired
  private MemberService memberService;

  @Autowired
  private SourceDao sourceDao;

  @Autowired
  private MemberDao memberDao;


  public Collection<String> getConfigurations() {
    return Collections.singletonList(TEST_CONFIGURATION);
  }

  @Override
  public boolean isRepeatable() {
    return true;
  }

  @Override
  public int getOrder() {
    return SEED_USER_DATA;
  }

  @Override
  public String getName() {
    return "Source Test Data";
  }

  @Override
  public void populate() {

    // Create a source to use for testing
    Source source = sourceDao.findByUrl(SOURCE_URL);
    if (source == null) {
      log.debug("Creating source " + SOURCE_URL);
      source = sourceService.createSource(SOURCE_NAME, SOURCE_URL);
      List<Place> places = new ArrayList<Place>();
      Place place = new Place();
      place.setName("place 1");
      places.add(place);
      sourceService.addPlaces(source, places);
    }

    // Link the test member to the source
    Member member = memberDao.findByUserName(SecurityTestData.MEMBER_USERNAME);
    memberService.addSource(member,  source);

  }
}

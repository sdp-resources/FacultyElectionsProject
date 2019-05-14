package fsc.entity;

import fsc.entity.query.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryTest {

  private Query query;
  private Profile profile;

  @Before
  public void setUp() throws Exception {
    profile = new Profile("Todd", "SmithT", "Art", "contract");
  }

  @Test
  public void ProfileIsAlwaysFalse() {
    query = new AlwaysFalseQuery();
    assertFalse(query.isProfileValid(profile));
  }

  @Test
  public void ProfileIsAlwaysTrue() {
    query = new AlwaysTrueQuery();
    assertTrue(query.isProfileValid(profile));
  }

  @Test
  public void AndWithThreeTrues()
  {
    query = new AndQuery(new Query[] { new AlwaysTrueQuery(), new AlwaysTrueQuery(),
                                       new AlwaysTrueQuery() });

    assertTrue(query.isProfileValid(profile));
  }

  @Test
  public void AndWithTwoTrueOneFalse()
  {
    query = new AndQuery(new Query[] { new AlwaysTrueQuery(), new AlwaysTrueQuery(),
                                       new AlwaysFalseQuery() });

    assertFalse(query.isProfileValid(profile));
  }

  @Test
  public void OrWithThreeFalse()
  {
    query = new OrQuery(new Query[] { new AlwaysFalseQuery(), new AlwaysFalseQuery(),
                                       new AlwaysFalseQuery() });

    assertFalse(query.isProfileValid(profile));
  }

  @Test
  public void OrWithOneTrueTwoFalse()
  {
    query = new OrQuery(new Query[] { new AlwaysTrueQuery(), new AlwaysFalseQuery(),
                                      new AlwaysFalseQuery() });

    assertTrue(query.isProfileValid(profile));
  }
}
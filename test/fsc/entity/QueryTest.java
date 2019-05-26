package fsc.entity;

import fsc.entity.query.*;
import fsc.mock.AlwaysFalseQueryStub;
import fsc.mock.AlwaysTrueQueryStub;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryTest {

  private Query query;
  private Profile profile;

  @Before
  public void setUp() {
    profile = new Profile("Todd", "SmithT", "Art", "contract");
  }

  @Test
  public void ProfileIsAlwaysFalse() {
    query = new AlwaysFalseQueryStub();
    assertFalse(query.isProfileValid(profile));
  }

  @Test
  public void ProfileIsAlwaysTrue() {
    query = new AlwaysTrueQueryStub();
    assertTrue(query.isProfileValid(profile));
  }

  @Test
  public void AndWithThreeTrues() {
    query = new AndQuery(new Query[]{
          new AlwaysTrueQueryStub(), new AlwaysTrueQueryStub(), new AlwaysTrueQueryStub()
    });

    assertTrue(query.isProfileValid(profile));
  }

  @Test
  public void AndWithTwoTrueOneFalse() {
    query = new AndQuery(new Query[]{
          new AlwaysTrueQueryStub(), new AlwaysTrueQueryStub(), new AlwaysFalseQueryStub()
    });

    assertFalse(query.isProfileValid(profile));
  }

  @Test
  public void OrWithThreeFalse() {
    query = new OrQuery(new Query[]{
          new AlwaysFalseQueryStub(), new AlwaysFalseQueryStub(), new AlwaysFalseQueryStub()
    });

    assertFalse(query.isProfileValid(profile));
  }

  @Test
  public void OrWithOneTrueTwoFalse() {
    query = new OrQuery(new Query[]{
          new AlwaysTrueQueryStub(), new AlwaysFalseQueryStub(), new AlwaysFalseQueryStub()
    });

    assertTrue(query.isProfileValid(profile));
  }

  @Test
  public void SingleAttributeQueryFormatString() {
    Query query = new AttributeQuery("contract", "tenured");

    assertEquals("contract = tenured", query.getFormattedString());
  }

  @Test
  public void AndWithThreeItemsFormatsString() {
    query = new AndQuery(new Query[]{
          new AlwaysTrueQueryStub(), new AlwaysFalseQueryStub(),
          new AttributeQuery("contract", "tenured")
    });

    assertEquals("(true AND false AND contract = tenured)", query.getFormattedString());
  }

  @Test
  public void OrWithThreeItemsFormatsString() {
    query = new OrQuery(new Query[]{
          new AlwaysTrueQueryStub(), new AlwaysFalseQueryStub(),
          new AttributeQuery("contract", "tenured")
    });

    assertEquals("(true OR false OR contract = tenured)", query.getFormattedString());
  }

  @Test
  public void AndWithOrInsideFormatsString() {
    query = new AndQuery(new Query[]{
          new AlwaysTrueQueryStub(),
          new OrQuery(new Query[]{new AlwaysTrueQueryStub(), new AlwaysFalseQueryStub()})
    });

    assertEquals("(true AND (true OR false))", query.getFormattedString());
  }
}
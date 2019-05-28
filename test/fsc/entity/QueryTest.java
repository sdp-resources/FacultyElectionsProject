package fsc.entity;

import fsc.entity.query.*;
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
    query = new FalseQuery();
    assertFalse(query.isProfileValid(profile));
  }

  @Test
  public void ProfileIsAlwaysTrue() {
    query = new TrueQuery();
    assertTrue(query.isProfileValid(profile));
  }

  @Test
  public void AndWithThreeTrues() {
    query = new AndQuery(new Query[]{
          new TrueQuery(), new TrueQuery(), new TrueQuery()
    });

    assertTrue(query.isProfileValid(profile));
  }

  @Test
  public void AndWithTwoTrueOneFalse() {
    query = new AndQuery(new Query[]{
          new TrueQuery(), new TrueQuery(), new FalseQuery()
    });

    assertFalse(query.isProfileValid(profile));
  }

  @Test
  public void OrWithThreeFalse() {
    query = new OrQuery(new Query[]{
          new FalseQuery(), new FalseQuery(), new FalseQuery()
    });

    assertFalse(query.isProfileValid(profile));
  }

  @Test
  public void OrWithOneTrueTwoFalse() {
    query = new OrQuery(new Query[]{
          new TrueQuery(), new FalseQuery(), new FalseQuery()
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
          new TrueQuery(), new FalseQuery(), new AttributeQuery("contract", "tenured")
    });

    assertEquals("(true AND false AND contract = tenured)", query.getFormattedString());
  }

  @Test
  public void OrWithThreeItemsFormatsString() {
    query = new OrQuery(new Query[]{
          new TrueQuery(), new FalseQuery(), new AttributeQuery("contract", "tenured")
    });

    assertEquals("(true OR false OR contract = tenured)", query.getFormattedString());
  }

  @Test
  public void AndWithOrInsideFormatsString() {
    query = new AndQuery(new Query[]{
          new TrueQuery(), new OrQuery(new Query[]{new TrueQuery(), new FalseQuery()})
    });

    assertEquals("(true AND (true OR false))", query.getFormattedString());
  }
}
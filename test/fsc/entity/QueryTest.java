package fsc.entity;

import fsc.entity.query.Query;
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
    query = Query.never();
    assertFalse(query.isProfileValid(profile));
  }

  @Test
  public void ProfileIsAlwaysTrue() {
    query = Query.always();
    assertTrue(query.isProfileValid(profile));
  }

  @Test
  public void AndWithThreeTrues() {
    query = Query.all(Query.always(), Query.always(), Query.always());
    assertTrue(query.isProfileValid(profile));
  }

  @Test
  public void AndWithTwoTrueOneFalse() {
    query = Query.all(Query.always(), Query.always(), Query.never());
    assertFalse(query.isProfileValid(profile));
  }

  @Test
  public void OrWithThreeFalse() {
    query = Query.any(Query.never(), Query.never(), Query.never());
    assertFalse(query.isProfileValid(profile));
  }

  @Test
  public void OrWithOneTrueTwoFalse() {
    query = Query.any(Query.always(), Query.never(), Query.never());
    assertTrue(query.isProfileValid(profile));
  }

  @Test
  public void SingleAttributeQueryFormatString() {
    Query query = Query.has("contract", "tenured");
    assertEquals("contract equals tenured", query.getFormattedString());
  }

  @Test
  public void AndWithThreeItemsFormatsString() {
    query = Query.all(Query.always(), Query.never(), Query.has("contract", "tenured"));
    assertEquals("(all AND none AND contract equals tenured)", query.getFormattedString());
  }

  @Test
  public void OrWithThreeItemsFormatsString() {
    query = Query.any(Query.always(), Query.never(), Query.has("contract", "tenured"));

    assertEquals("(all OR none OR contract equals tenured)", query.getFormattedString());
  }

  @Test
  public void AndWithOrInsideFormatsString() {
    query = Query.all(Query.always(), Query.any(Query.always(), Query.never()));

    assertEquals("(all AND (all OR none))", query.getFormattedString());
  }
}
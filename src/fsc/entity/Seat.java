package fsc.entity;

import fsc.entity.query.Query;

public class Seat {
  private Profile profile;
  private Query defaultQuery;
  private final String name;

  public Seat(String name, Query defaultQuery) {

    this.name = name;
    this.defaultQuery = defaultQuery;
  }

  public String getName() {
    return this.name;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public Profile getProfile() {
    return profile;
  }

  public void setDefaultQuery(Query defaultQuery) {
    this.defaultQuery = defaultQuery;
  }

  public Query getDefaultQuery() {
    return defaultQuery;
  }
}

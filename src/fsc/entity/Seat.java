package fsc.entity;

import fsc.entity.query.Query;

public class Seat {
  private Profile profile;
  private Query defaultQuery;

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

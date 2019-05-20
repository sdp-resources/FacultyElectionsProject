package fsc.entity;

import fsc.entity.query.Query;

public class Seat {
  private Profile profile;
  private Query defaultQuery;
  private String seatName;

  public Seat(){
    this.seatName = seatName;
  }

  public String getSeatName(){
    return this.seatName;
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

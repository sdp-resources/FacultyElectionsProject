package fsc.entity;

import fsc.entity.query.Query;

import java.util.Objects;

public class Seat {
  private Profile profile;
  private Query defaultQuery;
  private final String name;

  Seat(String name, Query defaultQuery) {

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

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Seat seat = (Seat) o;
    return Objects.equals(profile, seat.profile) && Objects.equals(defaultQuery,
                                                                   seat.defaultQuery) && Objects
                                                                                               .equals(
                                                                                                     name,
                                                                                                     seat.name);
  }

  public int hashCode() {
    return Objects.hash(profile, defaultQuery, name);
  }
}

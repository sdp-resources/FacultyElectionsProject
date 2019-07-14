package fsc.entity;

import fsc.entity.query.Query;
import fsc.request.EditSeatRequest;

import java.util.Objects;

public class Seat {
  private Long id;
  private Profile profile;
  private Query defaultQuery;
  private Committee committee;
  private String name;

  public Seat() {}

  public Seat(String name, Query defaultQuery, Committee committee) {
    this.name = name;
    this.defaultQuery = defaultQuery;
    this.setCommittee(committee);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long seatId) {
    this.id = seatId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
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

  public Committee getCommittee() {
    return committee;
  }

  public void setCommittee(Committee committee) {
    committee.addSeat(this);
    this.committee = committee;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Seat seat = (Seat) o;
    return Objects.equals(id, seat.id);
  }

  public int hashCode() {
    return Objects.hash(id);
  }

  public void update(String field, Object o) {
    switch (field) {
      case EditSeatRequest.EDIT_SEAT_PROFILE:
        setProfile((Profile) o);
        break;
      case EditSeatRequest.EDIT_SEAT_NAME:
        setName((String) o);
        break;
      case EditSeatRequest.EDIT_SEAT_QUERY:
        setDefaultQuery((Query) o);
        break;
    }
  }
}

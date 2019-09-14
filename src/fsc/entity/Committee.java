package fsc.entity;

import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Committee {
  private String name;
  private String description;

  private Query voterQuery;

  private Set<Seat> seats = new HashSet<>();
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Committee() {}

  public Committee(String name, String description, Query voterQuery) {
    this.name = name;
    this.description = description;
    this.voterQuery = voterQuery;
  }

  public Set<Seat> getSeats() {
    return seats;
  }

  public Seat getSeat(String seatName) throws CommitteeGateway.UnknownSeatNameException {
    for (Seat seat : seats) {
      if (seat.getName().equals(seatName)) {
        return seat;
      }
    }
    throw new CommitteeGateway.UnknownSeatNameException();
  }

  public boolean hasSeat(String seatName) {
    for (Seat seat : seats) {
      if (seat.getName().equals(seatName)) {
        return true;
      }
    }
    return false;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public int getCommitteeSize() {
    return seats.size();
  }

  public void addSeat(Seat seat) {
    seats.add(seat);
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Committee committee = (Committee) o;
    return Objects.equals(name, committee.name) &&
                 Objects.equals(description, committee.description) &&
                 Objects.equals(voterQuery, committee.voterQuery) &&
                 seats.equals(committee.seats);
  }

  public int hashCode() {
    return Objects.hash(name, description, seats);
  }

  public void update(String field, Object value) {
    switch (field) {
      case "name":
        setName((String) value);
        break;
      case "description":
        setDescription((String) value);
        break;
      case "voterQuery":
        setVoterQuery((Query) value);
    }
    // TODO: account for query-changing case
  }

  public Query getVoterQuery() {
    return voterQuery;
  }

  public void setVoterQuery(Query voterQuery) {
    this.voterQuery = voterQuery;
  }

  public String toString() {
    return "Committee{" +
                 "name='" + name + '\'' +
                 ", description='" + description + '\'' +
                 ", voterQuery=" + voterQuery +
                 '}';
  }
}

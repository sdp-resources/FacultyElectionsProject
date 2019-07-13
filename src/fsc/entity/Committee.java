package fsc.entity;

import fsc.gateway.CommitteeGateway;

import java.util.*;

public class Committee {
  private String name;
  private String description;

  private Set<Seat> seats = new HashSet<>();

  public Committee() {}

  protected Committee(String name, String description) {
    this.name = name;
    this.description = description;
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
    }
  }

}

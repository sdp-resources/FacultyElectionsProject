package fsc.entity;

import java.util.ArrayList;
import java.util.Objects;

public class Committee {
  private String name;
  private String description;

  private final ArrayList<Seat> seats = new ArrayList<>();

  public Committee(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public ArrayList<Seat> getSeats() {
    return seats;
  }

  public Seat getSeat(String seatName) throws UnknownSeatNameException {
    for (Seat seat : seats) {
      if (seat.getName().equals(seatName)) {
        return seat;
      }
    }
    throw new UnknownSeatNameException();
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

  public void addMember(Seat seat) {
    seats.add(seat);
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Committee committee = (Committee) o;
    return Objects.equals(name, committee.name) && Objects.equals(description,
                                                                  committee.description) && seats.equals(
          committee.seats);
  }

  public int hashCode() {
    return Objects.hash(name, description, seats);
  }

  public class UnknownSeatNameException extends Exception {}
}

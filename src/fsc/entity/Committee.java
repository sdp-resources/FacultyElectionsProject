package fsc.entity;

import java.util.ArrayList;

public class Committee {
  private String name;
  private String description;

  private ArrayList<Seat> seats = new ArrayList<>();

  public Committee(String name, String description)
  {
    this.name = name;
    this.description = description;
  }

  public ArrayList<Seat> getSeats() {
    return seats;
  }

  public Seat getSeat(String seatName) throws UnknownSeatNameException {
    for (int i = 0; i < seats.size(); i++) {
      if (seats.get(i).getName().equals(seatName)) {
        return seats.get(i);
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

  public class UnknownSeatNameException extends Exception {}
}

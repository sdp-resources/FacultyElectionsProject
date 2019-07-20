package fsc.viewable;

import java.util.List;
import java.util.Objects;

public class ViewableCommittee {
  public String name;
  public String description;
  public List<ViewableSeat> seats;
  public String voterQuery;

  public ViewableCommittee(String name, String description, String voterQuery,
                           List<ViewableSeat> seats) {
    this.name = name;
    this.description = description;
    this.voterQuery = voterQuery;
    this.seats = seats;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewableCommittee that = (ViewableCommittee) o;
    return Objects.equals(name, that.name) &&
                 Objects.equals(description, that.description) &&
                 Objects.equals(seats, that.seats);
  }

  public int hashCode() {
    return Objects.hash(name, description, seats);
  }
}

package fsc.viewable;

import java.util.List;
import java.util.Objects;

public class ViewableCommittee {
  // TODO Fix model context so that we don't  need all these
  //  getters just for handlebars
  public Long id;
  public String name;
  public String description;
  public List<ViewableSeat> seats;
  public String voterQuery;

  public ViewableCommittee(Long id, String name, String description, String voterQuery,
                           List<ViewableSeat> seats) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.voterQuery = voterQuery;
    this.seats = seats;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public List<ViewableSeat> getSeats() {
    return seats;
  }

  public String getVoterQuery() {
    return voterQuery;
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

  public Long getId() {
    return id;
  }
}

package fsc.viewable;

import java.util.List;
import java.util.Objects;

public class ViewableElection {

  public final String electionID;
  public final String state;
  public final ViewableSeat seat;
  public final String query;
  public final List<ViewableCandidate> candidates;

  public ViewableElection(
        String electionID, String state, ViewableSeat seat,
        String query, List<ViewableCandidate> candidates
  ) {
    this.electionID = electionID;
    this.state = state;
    this.seat = seat;
    this.query = query;
    this.candidates = candidates;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewableElection that = (ViewableElection) o;
    return Objects.equals(electionID, that.electionID) &&
                 Objects.equals(state, that.state) &&
                 Objects.equals(seat, that.seat) &&
                 Objects.equals(query, that.query) &&
                 Objects.equals(candidates, that.candidates);
  }

  public int hashCode() {
    return Objects.hash(electionID, state, seat, query, candidates);
  }
}

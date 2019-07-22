package fsc.viewable;

import java.util.Collection;
import java.util.Objects;

public class ViewableElection {

  public final long electionID;
  public final String state;
  public final ViewableSeat seat;
  public final String query;
  public final Collection<ViewableCandidate> candidates;
  public final Collection<ViewableVoter> voters;
  public final Collection<ViewableVoteRecord> votes;

  public ViewableElection(
        long electionID, String state, ViewableSeat seat,
        String query, Collection<ViewableCandidate> candidates,
        Collection<ViewableVoter> voters,
        Collection<ViewableVoteRecord> votes
  ) {
    this.electionID = electionID;
    this.state = state;
    this.seat = seat;
    this.query = query;
    this.candidates = candidates;
    this.voters = voters;
    this.votes = votes;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewableElection that = (ViewableElection) o;
    return Objects.equals(electionID, that.electionID) &&
                 Objects.equals(state, that.state) &&
                 Objects.equals(seat, that.seat) &&
                 Objects.equals(query, that.query) &&
                 Objects.equals(candidates, that.candidates) &&
                 Objects.equals(voters, that.voters) &&
                 Objects.equals(votes, that.votes) ;
  }

  public int hashCode() {
    return Objects.hash(electionID, state, seat, query, candidates, voters, votes);
  }
}

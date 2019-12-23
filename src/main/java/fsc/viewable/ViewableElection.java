package fsc.viewable;

import fsc.entity.Candidate;
import fsc.entity.State;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class ViewableElection {

  public final long electionID;
  public final State state;
  public final ViewableSeat seat;
  public final String query;
  public final Collection<ViewableCandidate> candidates;
  public final Collection<ViewableVoter> voters;
  public final Collection<ViewableVoteRecord> votes;

  public ViewableElection(
        long electionID, State state, ViewableSeat seat,
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

  public Collection<ViewableCandidate> getUndecidedCandidates() {
    return candidates.stream().filter(c -> c.status.equals(Candidate.Status.NoAnswer))
                     .collect(Collectors.toList());
  }

  public Collection<ViewableVoter> getPendingVoters() {
    return voters.stream().filter(c -> !c.voted).collect(Collectors.toList());
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

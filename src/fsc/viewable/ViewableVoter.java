package fsc.viewable;

import java.util.Objects;

public class ViewableVoter {
  public final long voterId;
  public final boolean voted;
  public final ViewableProfile profile;
  public final ViewableElection election;

  public ViewableVoter(
        long voterId, boolean voted, ViewableProfile profile,
        ViewableElection election
  ) {
    this.voterId = voterId;
    this.profile = profile;
    this.election = election;
    this.voted = voted;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewableVoter that = (ViewableVoter) o;
    return voterId == that.voterId &&
                 voted == that.voted &&
                 Objects.equals(profile, that.profile) &&
                 Objects.equals(election, that.election);
  }

  public int hashCode() {
    return Objects.hash(voterId, voted, profile, election);
  }

  public String toString() {
    return "ViewableVoter{" +
                 "voterId=" + voterId +
                 ", voted=" + voted +
                 ", profile=" + profile.username +
                 ", election=" + election.electionID +
                 '}';
  }
}

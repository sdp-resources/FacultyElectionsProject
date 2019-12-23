package fsc.viewable;

import java.util.Objects;

public class ViewableVoter {
  public final long voterId;
  public final long electionId;
  public final boolean voted;
  public final ViewableProfile profile;

  public ViewableVoter(
        long voterId, long electionId, boolean voted, ViewableProfile profile
  ) {
    this.voterId = voterId;
    this.profile = profile;
    this.electionId = electionId;
    this.voted = voted;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewableVoter that = (ViewableVoter) o;
    return voterId == that.voterId &&
                 voted == that.voted &&
                 Objects.equals(profile, that.profile) &&
                 Objects.equals(electionId, that.electionId);
  }

  public int hashCode() {
    return Objects.hash(voterId, voted, profile, electionId);
  }

  public String toString() {
    return "ViewableVoter{" +
                 "voterId=" + voterId +
                 ", voted=" + voted +
                 ", profile=" + profile.username +
                 ", election=" + electionId +
                 '}';
  }
}

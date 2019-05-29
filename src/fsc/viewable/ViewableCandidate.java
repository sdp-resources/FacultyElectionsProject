package fsc.viewable;

import fsc.entity.Candidate;

import java.util.Objects;

public class ViewableCandidate {
  public final ViewableProfile profile;
  public final Candidate.Status status;

  public ViewableCandidate(ViewableProfile profile, Candidate.Status status) {
    this.profile = profile;
    this.status = status;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewableCandidate that = (ViewableCandidate) o;
    return Objects.equals(profile, that.profile) && status == that.status;
  }

  public int hashCode() {
    return Objects.hash(profile, status);
  }
}

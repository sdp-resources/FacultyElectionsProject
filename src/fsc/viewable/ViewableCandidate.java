package fsc.viewable;

import fsc.entity.Candidate;

public class ViewableCandidate {
  public final ViewableProfile profile;
  public final Candidate.Status status;

  public ViewableCandidate(ViewableProfile profile, Candidate.Status status) {
    this.profile = profile;
    this.status = status;
  }
}

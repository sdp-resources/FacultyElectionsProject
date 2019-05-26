package fsc.service;

import fsc.entity.Candidate;
import fsc.entity.Profile;
import fsc.viewable.ViewableCandidate;
import fsc.viewable.ViewableProfile;

public class ViewableEntityConverter {

  public ViewableProfile convert(Profile profile) {
    return new ViewableProfile(profile.getName(), profile.getUsername(), profile.getDivision(),
                               profile.getContract());
  }

  public ViewableCandidate convert(Candidate candidate) {
    return new ViewableCandidate(convert(candidate.getProfile()), candidate.getStatus());

  }
}

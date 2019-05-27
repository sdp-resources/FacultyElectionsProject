package fsc.service;

import fsc.entity.Candidate;
import fsc.entity.Profile;
import fsc.viewable.ViewableCandidate;
import fsc.viewable.ViewableProfile;

import java.util.List;
import java.util.stream.Collectors;

public class ViewableEntityConverter {

  public ViewableProfile convert(Profile profile) {
    return new ViewableProfile(profile.getName(), profile.getUsername(), profile.getDivision(),
                               profile.getContract());
  }

  public List<ViewableProfile> convert(List<Profile> profiles) {
    return profiles.stream().map(this::convert).collect(Collectors.toList());
  }

  public ViewableCandidate convert(Candidate candidate) {
    return new ViewableCandidate(convert(candidate.getProfile()), candidate.getStatus());

  }
}

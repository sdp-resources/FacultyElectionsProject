package fsc.response;

import fsc.viewable.ViewableProfile;

import java.util.List;

public class SuccessfullyViewedCandidatesResponse implements Response{
  public List<ViewableProfile> viewableProfiles;

  public SuccessfullyViewedCandidatesResponse(List<ViewableProfile> viewableProfiles) {
    this.viewableProfiles = viewableProfiles;
  }
}
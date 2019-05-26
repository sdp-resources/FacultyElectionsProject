package fsc.response;

import fsc.viewable.ViewableProfile;

import java.util.List;

public class ViewCandidatesResponse implements Response{
  public final List<ViewableProfile> viewableProfiles;

  public ViewCandidatesResponse(List<ViewableProfile> viewableProfiles) {
    this.viewableProfiles = viewableProfiles;
  }
}

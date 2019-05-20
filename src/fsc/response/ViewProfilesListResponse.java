package fsc.response;

import fsc.viewable.ViewableProfile;

import java.util.Collection;

public class ViewProfilesListResponse implements Response {
  public Collection<ViewableProfile> viewableProfiles;

  public ViewProfilesListResponse(Collection<ViewableProfile> viewableProfiles)
  {
    this.viewableProfiles = viewableProfiles;
  }
}

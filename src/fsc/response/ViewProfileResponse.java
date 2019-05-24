package fsc.response;

import fsc.viewable.ViewableProfile;

public class ViewProfileResponse implements Response {
  public final ViewableProfile viewableProfile;
  public ViewProfileResponse(ViewableProfile viewableProfile){
    this.viewableProfile = viewableProfile;
  }
}

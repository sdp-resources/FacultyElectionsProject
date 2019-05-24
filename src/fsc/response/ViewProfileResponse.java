package fsc.response;

import fsc.response.Response;
import fsc.viewable.ViewableProfile;

public class ViewProfileResponse implements Response {
  public ViewableProfile viewableProfile;
  public ViewProfileResponse(ViewableProfile viewableProfile){
    this.viewableProfile = viewableProfile;
  }
}

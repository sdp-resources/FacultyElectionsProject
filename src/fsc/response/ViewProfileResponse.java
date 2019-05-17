package fsc.response;

import fsc.response.Response;
import fsc.viewable.ViewableProfile;

import javax.swing.text.View;
import java.util.HashMap;
import java.util.Map;

public class ViewProfileResponse implements Response {
  public ViewableProfile viewableProfile;
  public ViewProfileResponse(ViewableProfile viewableProfile){
    this.viewableProfile = viewableProfile;
  }
}

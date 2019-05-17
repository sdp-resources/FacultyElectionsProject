package fsc.response;

import fsc.response.Response;
import fsc.viewable.ViewableProfile;

import javax.swing.text.View;
import java.util.HashMap;
import java.util.Map;

public class ProfileViewerResponse implements Response {
  public ViewableProfile viewableProfile;
  public ProfileViewerResponse(ViewableProfile viewableProfile){
    this.viewableProfile = viewableProfile;
  }
}

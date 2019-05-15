package fsc.interactor;

import java.util.HashMap;

public class ProfileViewerResponse {
  public HashMap<String, String> userInfo;
  public ProfileViewerResponse(HashMap<String, String> userInfo){
    this.userInfo = userInfo;
  }
}

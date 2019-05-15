package fsc.response;

import fsc.response.Response;

import java.util.HashMap;

public class ProfileViewerResponse implements Response {
  public HashMap<String, String> userInfo;
  public ProfileViewerResponse(HashMap<String, String> userInfo){
    this.userInfo = userInfo;
  }
}

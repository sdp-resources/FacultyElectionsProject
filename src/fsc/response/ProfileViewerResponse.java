package fsc.response;

import fsc.response.Response;

import java.util.HashMap;
import java.util.Map;

public class ProfileViewerResponse implements Response {
  public Map<String, String> userInfo;
  public ProfileViewerResponse(Map<String, String> userInfo){
    this.userInfo = userInfo;
  }
}

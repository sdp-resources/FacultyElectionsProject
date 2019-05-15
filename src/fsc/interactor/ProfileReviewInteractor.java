package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGatewayInterface;
import fsc.response.Response;

import java.util.HashMap;

public class ProfileReviewInteractor {
  public String userName;
  public HashMap<String, String> userInfo = null;
  public ProfileGatewayInterface gateway;

  public ProfileReviewInteractor(ProfileGatewayInterface gateway){
    this.gateway = gateway;
  }

  public Response execute(ProfileViewerRequest request) {
    userName = request.userName;
    try{
      Profile profile = gateway.getProfile(userName);
      ProfileViewer profileInfo = new ProfileViewer(profile);
      userInfo = profileInfo.createHashMap();
      ProfileViewerResponse response = new ProfileViewerResponse(userInfo);
      return response;
    }
    catch (Exception e){
      return new ErrorResponse("No profile found!");
    }
  }
  public class NoProfileException extends RuntimeException {
    public String noProfile;
    public NoProfileException(String s) {
      noProfile = s;
    }
  }

  public class ErrorResponse implements Response {
    public String response;
    public ErrorResponse(String s) {
      response = s;
    }
  }
}
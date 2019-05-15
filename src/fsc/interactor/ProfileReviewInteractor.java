package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.response.Response;

import java.util.HashMap;

public class ProfileReviewInteractor {
  public String userName;
  public HashMap<String, String> userInfo = null;
  public ProfileGateway gateway;

  public ProfileReviewInteractor(ProfileGateway gateway){
    this.gateway = gateway;
  }

  public Response execute(ProfileViewerRequest request) {
    userName = request.userName;
    try{
      return tryCreateProfileResponse();
    }
    catch (Exception e){
      return new ErrorResponse("No profile found!");
    }
  }

  private Response tryCreateProfileResponse() {
    Profile profile = gateway.getProfileFromUsername(userName);
    ProfileViewer profileInfo = new ProfileViewer(profile);
    return new ProfileViewerResponse(profileInfo.createHashMap());
  }

  public class ErrorResponse implements Response {
    public String response;
    public ErrorResponse(String s) {
      response = s;
    }
  }
}
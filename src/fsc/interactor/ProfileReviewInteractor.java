package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGatewayInterface;
import fsc.response.Response;

public class ProfileReviewInteractor {
  public String userName;
  public ProfileGatewayInterface gateway;

  public ProfileReviewInteractor(ProfileGatewayInterface gateway){
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
    Profile profile = gateway.getProfile(userName);
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
package fsc.interactor;

import fsc.entity.Profile;
import fsc.entity.ProfileToHashMapConverter;
import fsc.gateway.ProfileGateway;
import fsc.request.ProfileViewerRequest;
import fsc.response.ErrorResponse;
import fsc.response.ProfileViewerResponse;
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
    return tryCreateProfileResponse();
  }

  private Response tryCreateProfileResponse() {
    Profile profile;
    try {
      profile = gateway.getProfileFromUsername(userName);
    }
    catch (ProfileGateway.InvalidProfileUsernameException e)
    {
      return new ErrorResponse("No profile found!");
    }

    ProfileToHashMapConverter profileInfo = new ProfileToHashMapConverter(profile);
    return new ProfileViewerResponse(profileInfo.createHashMap());
  }

  public class ErrorResponse implements Response {
    public String response;
    public ErrorResponse(String s) {
      response = s;
    }
  }

}
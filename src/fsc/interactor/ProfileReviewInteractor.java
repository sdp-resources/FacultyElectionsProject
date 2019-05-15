package fsc.interactor;

import fsc.entity.Profile;
import fsc.entity.ProfileToHashMapConverter;
import fsc.gateway.ProfileGateway;
import fsc.response.ErrorResponse;
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
    ProfileToHashMapConverter profileInfo = new ProfileToHashMapConverter(profile);
    return new ProfileViewerResponse(profileInfo.createHashMap());
  }

}
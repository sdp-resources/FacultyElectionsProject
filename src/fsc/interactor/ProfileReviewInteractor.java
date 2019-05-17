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
  private ProfileToHashMapConverter converter = new ProfileToHashMapConverter();

  public void setConverter(ProfileToHashMapConverter converter) {
    this.converter = converter;
  }

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

    return new ProfileViewerResponse(converter.createHashMap(profile));
  }
}
package fsc.interactor;

import fsc.entity.Profile;
import fsc.entity.ProfileToHashMapConverter;
import fsc.gateway.ProfileGateway;
import fsc.request.ProfileViewerRequest;
import fsc.response.ErrorResponse;
import fsc.response.ProfileViewerResponse;
import fsc.response.Response;
import fsc.service.ProfileToViewableProfileConverter;

import java.util.HashMap;

public class ViewProfileInteractor {
  public String userName;
  public ProfileGateway gateway;
  private ProfileToViewableProfileConverter converter = new ProfileToViewableProfileConverter();

  public void setConverter(ProfileToViewableProfileConverter converter) {
    this.converter = converter;
  }

  public ViewProfileInteractor(ProfileGateway gateway){
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

    return new ProfileViewerResponse(converter.convert(profile));
  }
}
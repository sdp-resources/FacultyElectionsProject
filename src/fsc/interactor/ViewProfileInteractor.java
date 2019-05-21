package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.ProfileViewerRequest;
import fsc.response.ErrorResponse;
import fsc.response.ViewProfileResponse;
import fsc.response.Response;
import fsc.service.Context;

public class ViewProfileInteractor {
  public String userName;
  public ProfileGateway gateway;


  public ViewProfileInteractor(ProfileGateway gateway){
    this.gateway = gateway;
  }

  public Response execute(ProfileViewerRequest request) {
    userName = request.username;
    return tryCreateProfileResponse();
  }

  private Response tryCreateProfileResponse() {
    Profile profile;
    try {
      profile = gateway.getProfile(userName);
    }
    catch (ProfileGateway.InvalidProfileUsernameException e)
    {
      return new ErrorResponse(ErrorResponse.NO_PROFILE_FOUND);
    }

    return new ViewProfileResponse(Context.instance.profileToViewableProfileConverter.convert(profile));
  }
}
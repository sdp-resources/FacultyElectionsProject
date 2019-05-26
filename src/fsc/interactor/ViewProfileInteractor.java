package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.Request;
import fsc.request.ViewProfileRequest;
import fsc.response.ErrorResponse;
import fsc.response.ViewResponse;
import fsc.response.Response;
import fsc.service.Context;

public class ViewProfileInteractor implements Interactor {
  public String userName;
  private ProfileGateway gateway;


  public ViewProfileInteractor(ProfileGateway gateway){
    this.gateway = gateway;
  }

  public Response execute(ViewProfileRequest request) {
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
      return ErrorResponse.unknownProfileName();
    }

    return new ViewResponse(Context.instance.viewableEntityConverter.convert(profile));
  }

  public boolean canHandle(Request request) {
    return request instanceof ViewProfileRequest;
  }

  public Response execute(Request request) {
    return execute((ViewProfileRequest) request);
  }
}
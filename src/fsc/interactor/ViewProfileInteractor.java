package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.Request;
import fsc.request.ViewProfileRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.ViewResponse;

public class ViewProfileInteractor extends Interactor {
  public String userName;
  private ProfileGateway gateway;

  public ViewProfileInteractor(ProfileGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(ViewProfileRequest request) {
    userName = request.username;
    return tryCreateProfileResponse();
  }

  private Response tryCreateProfileResponse() {
    try {
      Profile profile = gateway.getProfile(userName);
      return ViewResponse.ofProfile(profile);
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ErrorResponse.unknownProfileName();
    }

  }

  public boolean canHandle(Request request) {
    return request instanceof ViewProfileRequest;
  }

  public Response execute(Request request) {
    return execute((ViewProfileRequest) request);
  }
}
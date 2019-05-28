package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.CreateProfileRequest;
import fsc.request.Request;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class CreateProfileInteractor extends Interactor {

  private ProfileGateway gateway;

  public CreateProfileInteractor(ProfileGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(CreateProfileRequest request) {
    if (gateway.hasProfile(request.username)) {
      return ErrorResponse.resourceExists();
    }
    gateway.addProfile(makeProfileFromRequest(request));
    gateway.save();
    return new SuccessResponse();
  }

  private Profile makeProfileFromRequest(CreateProfileRequest request) {
    return new Profile(request.name, request.username, request.division, request.contract);
  }

  public boolean canHandle(Request request) {
    return request instanceof CreateProfileRequest;
  }

  public Response execute(Request request) {
    return execute ((CreateProfileRequest) request);
  }
}

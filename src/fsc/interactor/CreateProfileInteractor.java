package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.CreateProfileRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class CreateProfileInteractor {

  private ProfileGateway gateway;

  public CreateProfileInteractor(ProfileGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(
        CreateProfileRequest request
  ) {
    try { gateway.getProfile(request.username);} catch (Exception e) {
      gateway.addProfile(makeProfileFromRequest(request));
      return new SuccessResponse();
    }
    return ErrorResponse.resourceExists();
  }

  private Profile makeProfileFromRequest(CreateProfileRequest request) {
    return new Profile(request.name, request.username, request.division, request.contract);
  }

}

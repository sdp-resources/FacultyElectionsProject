package fsc.interactor;

import fsc.Main;
import fsc.Response.ResponseInterface;
import fsc.gateway.ProfileGatewayInterface;
import fsc.request.CreateProfileRequest;
import fsc.entity.Profile;
import fsc.response.FailedAddedProfileResponse;
import fsc.response.SuccessfullyAddedProfileResponse;
import gateway.InMemoryGateway;
import fsc.response.Response;

import java.util.ArrayList;

public class CreateProfileInteractor {

  private static ProfileGatewayInterface gateway;


  public CreateProfileInteractor(ProfileGatewayInterface gateway) {
    this.gateway = gateway;
  }

  public Response execute(
        CreateProfileRequest request) throws Exception {
    if (gateway.getProfileWithUsername(request.username) != null) {
      return new FailedAddedProfileResponse();
    }
    Profile profile = new Profile(request.name, request.username, request.department,
                                  request.contract);
    gateway.addProfile(profile);
    return new SuccessfullyAddedProfileResponse();
  }

}

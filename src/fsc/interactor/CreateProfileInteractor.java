package fsc.interactor;

import fsc.gateway.ProfileGatewayInterface;
import fsc.request.CreateProfileRequest;
import fsc.entity.Profile;
import fsc.response.FailedAddedProfileResponse;
import fsc.response.SuccessfullyAddedProfileResponse;
import fsc.response.Response;

public class CreateProfileInteractor {

  private static ProfileGatewayInterface gateway;


  public CreateProfileInteractor(ProfileGatewayInterface gateway) {
    this.gateway = gateway;
  }

  public Response execute(
        CreateProfileRequest request) throws Exception {
    try{ gateway.getProfileWithUsername(request.username);}
    catch (Exception e) {
      if (gateway.isValidDivision(request.division)) {
        gateway.addProfile(makeProfileFromRequest(request));
        return new SuccessfullyAddedProfileResponse();
      }
    }
    return new FailedAddedProfileResponse();
  }

  private Profile makeProfileFromRequest(CreateProfileRequest request) {
    return new Profile(request.name, request.username, request.division,
                                        request.contract);
  }

}

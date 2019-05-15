package fsc.interactor;

import fsc.gateway.ProfileGateway;
import fsc.request.CreateProfileRequest;
import fsc.entity.Profile;
import fsc.response.FailedAddedProfileResponse;
import fsc.response.SuccessfullyAddedProfileResponse;
import fsc.response.Response;

public class CreateProfileInteractor {

  private static ProfileGateway gateway;


  public CreateProfileInteractor(ProfileGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(
        CreateProfileRequest request) throws Exception {
    try{ gateway.getProfileFromUsername(request.username);}
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

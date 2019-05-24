package fsc.interactor;

import fsc.gateway.ProfileGateway;
import fsc.request.CreateProfileRequest;
import fsc.entity.Profile;
import fsc.response.*;

public class CreateProfileInteractor {

  private ProfileGateway gateway;


  public CreateProfileInteractor(ProfileGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(
        CreateProfileRequest request) {
    try{ gateway.getProfile(request.username);}
    catch (Exception e) {
      gateway.addProfile(makeProfileFromRequest(request));
      return new SuccessResponse();
      }
    return new ErrorResponse("Profile with that username already exists");
  }

  private Profile makeProfileFromRequest(CreateProfileRequest request) {
    return new Profile(request.name, request.username, request.division,
                                        request.contract);
  }

}

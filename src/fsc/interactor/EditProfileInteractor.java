package fsc.interactor;

import fsc.gateway.ProfileGateway;
import fsc.request.EditProfileRequest;
import fsc.response.FailedSearchResponse;
import fsc.response.Response;
import fsc.response.SuccessfullyEditedResponse;

public class EditProfileInteractor {
  private ProfileGateway gateway;

  public EditProfileInteractor(ProfileGateway gateway) {
    this.gateway = gateway;
  }

  private boolean usernameExists(String username) throws Exception {
    try {gateway.getProfileFromUsername(username);}
    catch (Exception e) {
      return true;
    }
    return false;
  }

  public Response execute(EditProfileRequest request) throws Exception {
    if(usernameExists(request.username)){
      return new SuccessfullyEditedResponse();
    }
    return new FailedSearchResponse();
  }
}

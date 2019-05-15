package fsc.interactor;

import fsc.gateway.ProfileGatewayInterface;
import fsc.request.EditProfileRequest;
import fsc.response.FailedSearchResponse;
import fsc.response.Response;
import fsc.response.SuccessfullyEditedResponse;

public class EditProfileInteractor {
  private ProfileGatewayInterface gateway;

  public EditProfileInteractor(ProfileGatewayInterface gateway) {
    this.gateway = gateway;
  }

  private boolean usernameExists(String username){
      if (gateway.getProfileWithUsername(username) != null){
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

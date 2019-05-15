package fsc.interactor;

import fsc.gateway.Gateway;
import fsc.request.EditProfileRequest;
import fsc.response.FailedSearchResponse;
import fsc.response.Response;

public class EditProfileInteractor {
  private Gateway gateway;

  public EditProfileInteractor(Gateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(EditProfileRequest request)
    throws RuntimeException {
      if(gateway.getAllProfiles() == null) {
        throw new RuntimeException("UsernameNotHere");
      }
      Response response = new FailedSearchResponse();
      return response;
  }
}

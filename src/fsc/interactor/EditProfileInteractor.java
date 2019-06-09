package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.EditProfileRequest;
import fsc.request.Request;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class EditProfileInteractor extends Interactor<EditProfileRequest> {
  private ProfileGateway gateway;

  public EditProfileInteractor(ProfileGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(EditProfileRequest request) {
    try {
      Profile profile = gateway.getProfile(request.username);
      request.applyChangesTo(profile);
      gateway.save();
      return new SuccessResponse();
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ErrorResponse.unknownProfileName();
    }
  }

  public boolean canHandle(Request request) {
    return request instanceof EditProfileRequest;
  }
}

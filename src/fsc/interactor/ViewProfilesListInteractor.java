package fsc.interactor;

import fsc.gateway.ProfileGateway;
import fsc.request.ViewProfilesListRequest;
import fsc.response.Response;
import fsc.response.ViewResponse;

public class ViewProfilesListInteractor {
  private ProfileGateway profileGateway;

  public ViewProfilesListInteractor(ProfileGateway profileGateway) {
    this.profileGateway = profileGateway;
  }

  public Response execute(ViewProfilesListRequest request) {
    return ViewResponse.ofProfileList(profileGateway.getAllProfiles());
  }

}

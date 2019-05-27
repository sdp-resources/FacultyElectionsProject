package fsc.interactor;

import fsc.gateway.ProfileGateway;
import fsc.request.ViewProfilesListRequest;
import fsc.response.ViewResponse;
import fsc.viewable.ViewableProfile;

import java.util.List;

public class ViewProfilesListInteractor {
  private ProfileGateway profileGateway;

  public ViewProfilesListInteractor(ProfileGateway profileGateway) {
    this.profileGateway = profileGateway;
  }

  public ViewResponse<List<ViewableProfile>> execute(ViewProfilesListRequest request) {
    return ViewResponse.ofProfileList(profileGateway.getAllProfiles());
  }

}

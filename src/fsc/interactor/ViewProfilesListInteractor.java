package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.ViewProfilesListRequest;
import fsc.response.*;
import fsc.viewable.ViewableProfile;

import java.util.ArrayList;

public class ViewProfilesListInteractor {
  private ProfileGateway profileGateway;

  public ViewProfilesListInteractor(ProfileGateway profileGateway) {
    this.profileGateway = profileGateway;
  }

  public Response execute(ViewProfilesListRequest request) {
    profileGateway.getAllProfiles();
    return new ViewProfilesListResponse();
  }
}

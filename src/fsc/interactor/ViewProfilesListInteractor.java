package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.ViewProfilesListRequest;
import fsc.response.*;
import fsc.service.Context;
import fsc.viewable.ViewableProfile;

import java.util.ArrayList;
import java.util.Collection;

public class ViewProfilesListInteractor {
  private final ProfileGateway profileGateway;

  public ViewProfilesListInteractor(ProfileGateway profileGateway) {
    this.profileGateway = profileGateway;
  }

  public Response execute(ViewProfilesListRequest request) {
    Collection<ViewableProfile> viewableProfiles = new ArrayList<>();
    for(Profile profile : profileGateway.getAllProfiles())
    {
      viewableProfiles.add(Context.instance.profileToViewableProfileConverter.convert(profile));
    }

    return new ViewProfilesListResponse(viewableProfiles);
  }
}

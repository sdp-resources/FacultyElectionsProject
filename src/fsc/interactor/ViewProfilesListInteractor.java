package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.ViewProfilesListRequest;
import fsc.response.ViewResponse;
import fsc.service.query.QueryStringConverter;
import fsc.viewable.ViewableProfile;

import java.util.List;

public class ViewProfilesListInteractor extends Interactor<ViewProfilesListRequest> {
  private ProfileGateway profileGateway;

  public ViewProfilesListInteractor(ProfileGateway profileGateway) {
    this.profileGateway = profileGateway;
  }

  public ViewResponse<List<ViewableProfile>> execute(ViewProfilesListRequest request) {
    List<Profile> profiles = getProfiles(request.query);

    return ViewResponse.ofProfileList(profiles);
  }

  private List<Profile> getProfiles(String which) {
    return profileGateway.getProfilesMatching(new QueryStringConverter().fromString(which));
  }
}

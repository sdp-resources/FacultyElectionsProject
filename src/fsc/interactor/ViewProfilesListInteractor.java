package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.Request;
import fsc.request.ViewProfilesListRequest;
import fsc.response.Response;
import fsc.response.ViewResponse;
import fsc.viewable.ViewableProfile;

import java.util.List;

public class ViewProfilesListInteractor extends Interactor {
  private ProfileGateway profileGateway;

  public ViewProfilesListInteractor(ProfileGateway profileGateway) {
    this.profileGateway = profileGateway;
  }

  public ViewResponse<List<ViewableProfile>> execute(ViewProfilesListRequest request) {
    List<Profile> profiles = getProfiles(request.which);

    return ViewResponse.ofProfileList(profiles);
  }

  private List<Profile> getProfiles(String which) {
    if (which.equals("active")) {
      return profileGateway.getActiveProfiles();
    } else {
      return profileGateway.getAllProfiles();
    }
  }

  public boolean canHandle(Request request) {
    return request instanceof ViewProfilesListRequest;
  }

  public Response execute(Request request) {
    return execute((ViewProfilesListRequest) request);
  }
}

package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.*;
import fsc.response.*;
import fsc.service.query.QueryStringConverter;
import fsc.viewable.ViewableProfile;

import java.util.List;

public class ProfileInteractor extends Interactor {

  private ProfileGateway gateway;

  public ProfileInteractor(ProfileGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(CreateProfileRequest request) {
    if (gateway.hasProfile(request.username)) {
      return ErrorResponse.resourceExists();
    }
    gateway.addProfile(makeProfileFromRequest(request));
    gateway.save();
    return new SuccessResponse();
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

  public Response execute(ViewProfileRequest request) {
    try {
      Profile profile = gateway.getProfile(request.username);
      return ViewResponse.ofProfile(profile);
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ErrorResponse.unknownProfileName();
    }
  }

  public ViewResponse<List<ViewableProfile>> execute(ViewProfilesListRequest request) {
    List<Profile> profiles = gateway.getProfilesMatching(
          new QueryStringConverter().fromString(request.query));

    return ViewResponse.ofProfileList(profiles);
  }

  private Profile makeProfileFromRequest(CreateProfileRequest request) {
    return new Profile(request.name, request.username, request.division, request.contract);
  }

}

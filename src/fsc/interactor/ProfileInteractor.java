package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.*;
import fsc.response.*;
import fsc.service.query.QueryStringConverter;
import fsc.service.query.QueryStringParser;

import java.util.List;

public class ProfileInteractor extends Interactor {

  private ProfileGateway gateway;

  public ProfileInteractor(ProfileGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(CreateProfileRequest request) {
    if (gateway.hasProfile(request.username)) {
      return ResponseFactory.resourceExists();
    }
    gateway.addProfile(makeProfileFromRequest(request));
    gateway.save();
    return ResponseFactory.success();
  }

  public Response execute(EditProfileRequest request) {
    try {
      Profile profile = gateway.getProfile(request.username);
      request.applyChangesTo(profile);
      gateway.save();
      return ResponseFactory.success();
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ResponseFactory.unknownProfileName();
    }
  }

  public Response execute(ViewProfileRequest request) {
    try {
      Profile profile = gateway.getProfile(request.username);
      return ResponseFactory.ofProfile(profile);
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ResponseFactory.unknownProfileName();
    }
  }

  public Response execute(ViewProfilesListRequest request) {
    try {
      List<Profile> profiles = gateway.getProfilesMatching(
            new QueryStringConverter().fromString(request.query));
      return ResponseFactory.ofProfileList(profiles);
    } catch (QueryStringParser.QueryParseException e) {
      // TODO: Check for this
      return ResponseFactory.invalidQuery(e.getMessage());
    }
  }

  private Profile makeProfileFromRequest(CreateProfileRequest request) {
    return new Profile(request.name, request.username, request.division, request.contract);
  }

}

package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.EditProfileRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

import java.util.Map;

public class EditProfileInteractor {
  private ProfileGateway gateway;

  EditProfileInteractor(ProfileGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(EditProfileRequest request) {
    try {
      Profile profile = editProfileWithRequest(request);
      updateProfileInGateway(profile);
      return new SuccessResponse();
    } catch (Exception e) {
      return ErrorResponse.unknownProfileName();
    }
  }

  private Profile editProfileWithRequest(EditProfileRequest request) throws Exception {
    Profile profile = gateway.getProfile(request.username);
    Map changesMap = request.changes;
    for (Object key : changesMap.keySet()) {
      updateProfileField(profile, changesMap, key);
    }
    return profile;
  }

  private void updateProfileField(Profile profile, Map changesMap, Object key) {
    String changeKey = key.toString();
    String changeField = changesMap.get(key).toString();
    updateProfileWithChanges(changeKey, changeField, profile);
  }

  private void updateProfileWithChanges(String changeKey, String changeField, Profile profile) {
    switch (changeKey) {
      case "Contract":
        profile.setContract(changeField);
        break;
      case "Name":
        profile.setName(changeField);
        break;
      case "Division":
        profile.setDivision(changeField);
        break;
      case "Username":
        profile.setUsername(changeField);
        break;
      case "Active":
        if (isTrue(changeField)) {
          profile.setActive();
        }
        break;
      case "Inactive":
        if (isTrue(changeField)) {
          profile.setInactive();
        }
        break;
    }
  }

  private boolean isTrue(String changeField) {
    return Boolean.parseBoolean(changeField);
  }

  private boolean ifUsernameExists(String username) throws Exception {

    return gateway.getProfile(username) != null;
  }

  private void updateProfileInGateway(Profile profile) {
    gateway.save();
  }
}

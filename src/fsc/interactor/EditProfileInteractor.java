package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.EditProfileRequest;
import fsc.response.FailedSearchResponse;
import fsc.response.Response;
import fsc.response.SuccessfullyEditedResponse;

import java.util.Map;

public class EditProfileInteractor {
  private ProfileGateway gateway;

  public EditProfileInteractor(ProfileGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(EditProfileRequest request) throws Exception {
    if(ifUsernameExists(request.username)){
      Profile profile = editProfileWithRequest(request);
      updateProfileInGateway(profile);
      return new SuccessfullyEditedResponse();
    }
    return new FailedSearchResponse();
  }

  private void updateProfileInGateway(Profile profile) {
    gateway.saveProfile(profile);
  }

  private boolean ifUsernameExists(String username) throws Exception {
      if (gateway.getProfileFromUsername(username) != null){
        return true;
    }
    return false;
  }

  private Profile editProfileWithRequest(EditProfileRequest request) throws Exception {
    Profile profile = gateway.getProfileFromUsername(request.username);
    String changeKey = getChangeKey(request.changes);
    String changeField = request.changes.get(changeKey);
    profile = updateProfileWithChanges(changeKey,changeField,profile);
    return profile;
  }

  private Profile updateProfileWithChanges(String changeKey, String changeField, Profile profile) {
    switch(changeKey){
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
      case "Inactive":
        profile.setActive();
        break;
      case "Active":
        profile.setInactive();
        break;
    }
    return profile;
  }

  private String getChangeKey(Map<String, String> changes) {
    Object key = changes.keySet().toArray()[0];
    return key.toString();
  }


}

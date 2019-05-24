package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.EditProfileRequest;
import fsc.response.*;

import java.util.Map;

public class EditProfileInteractor {
  private ProfileGateway gateway;

  public EditProfileInteractor(ProfileGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(EditProfileRequest request) throws Exception {
    try{
      Profile profile = editProfileWithRequest(request);
      updateProfileInGateway(profile);
      return new SuccessResponse();
    } catch (Exception e){
      return new ErrorResponse("No profile with that username exists");
    }
  }

  private Profile editProfileWithRequest(EditProfileRequest request) throws Exception {
    Profile profile = gateway.getProfile(request.username);
    Map changesMap = request.changes;
    for (Object key : changesMap.keySet()) {
      profile = updateProfileField(profile, changesMap, key);
    }
    return profile;
  }

  private Profile updateProfileField(Profile profile, Map changesMap, Object key) {
    String changeKey = key.toString();
    String changeField = changesMap.get(key).toString();
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
      case "Active":
        if(isTrue(changeField)){
          profile.setActive();
        }
        break;
      case "Inactive":
        if(isTrue(changeField)){
          profile.setInactive();
        }
        break;
    }
    return profile;
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

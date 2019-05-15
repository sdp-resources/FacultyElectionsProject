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
    if(usernameExists(request.username)){
      editUsernameWithRequest(request);
      return new SuccessfullyEditedResponse();
    }
    return new FailedSearchResponse();
  }

  private boolean usernameExists(String username) throws Exception {
      if (gateway.getProfileFromUsername(username) != null){
        System.out.println(username);
        return true;
    }
    return false;
  }

  private Profile editUsernameWithRequest(EditProfileRequest request) throws Exception {
    Profile profile = gateway.getProfileFromUsername(request.username);
    String change = getCorrectChangeString(request.changes);
    updateProfileBasedOnChangeString(change,profile);
    return profile;
  }

  private void updateProfileBasedOnChangeString(String change, Profile profile) {
    switch(change){
      case "Contract":
        profile.setContract(change);
      case "Name":
        profile.setName(change);
      case "Division":
        profile.setDivision(change);
    }
  }

  private String getCorrectChangeString(Map<String, String> changes) {
    return changes.get(changes.keySet().toArray()[0]);
  }


}

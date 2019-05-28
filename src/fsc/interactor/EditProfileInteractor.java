package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.request.EditProfileRequest;
import fsc.request.Request;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

import java.util.Map;

import static fsc.request.EditProfileRequest.*;

public class EditProfileInteractor extends Interactor {
  private ProfileGateway gateway;

  public EditProfileInteractor(ProfileGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(EditProfileRequest request) {
    try {
      Profile profile = gateway.getProfile(request.username);
      makeChanges(profile, request.changes);
      gateway.save();
      return new SuccessResponse();
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ErrorResponse.unknownProfileName();
    }
  }

  private void makeChanges(Profile profile, Map<String, Object> changes) {
    for (String key : changes.keySet()) {
      updateProfileField(profile, changes, key);
    }
  }

  private void updateProfileField(Profile profile, Map<String, Object> changesMap, String key) {
    String changeField = changesMap.get(key).toString();
    updateProfileWithChanges(profile, key, changeField);
  }

  private void updateProfileWithChanges(Profile profile, String key, Object field) {
    switch (key) {
      case CHANGE_CONTRACT_TYPE:
        profile.setContract((String) field);
        break;
      case CHANGE_NAME:
        profile.setName((String) field);
        break;
      case CHANGE_DIVISION:
        profile.setDivision((String) field);
        break;
      case CHANGE_ACTIVE:
        if (isTrue(field.toString())) {
          profile.setActive();
        } else {
          profile.setInactive();
        }
        break;
    }
  }

  private boolean isTrue(String changeField) {
    return Boolean.parseBoolean(changeField);
  }

  public boolean canHandle(Request request) {
    return request instanceof EditProfileRequest;
  }

  public Response execute(Request request) {
    return execute((EditProfileRequest) request);
  }
}

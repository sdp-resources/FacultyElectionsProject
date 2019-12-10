package fsc.mock.gateway.profile;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

public class InvalidProfileGatewaySpy extends ProfileGatewayStub {
  public String submittedUsername;
  public boolean hasSaved = false;
  public Profile addedProfile = null;

  @Override
  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    submittedUsername = username;
    throw new ProfileGateway.InvalidProfileUsernameException();
  }

  public void addProfile(Profile profile) {
    addedProfile = profile;
    super.addProfile(profile);
  }

  public boolean hasProfile(String username) {
    submittedUsername = username;
    return false;
  }

  public void save() {
    if (addedProfile != null) { hasSaved = true; }
  }
}

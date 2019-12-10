package fsc.mock.gateway.profile;

import fsc.entity.Profile;

public class ExistingProfileGatewaySpy extends ProfileGatewayStub {
  public String providedUsername;
  static public Boolean profileHasBeenEdited = false;

  public ExistingProfileGatewaySpy(Profile... profiles) {
    super(profiles);
  }

  public Profile getProfile(String username) {
    providedUsername = username;
    return getAProfile();
  }

  public boolean hasProfile(String username) {
    providedUsername = username;
    return true;
  }

  public void save() {
    profileHasBeenEdited = true;
  }

}


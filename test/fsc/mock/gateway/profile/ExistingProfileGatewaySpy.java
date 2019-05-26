package fsc.mock.gateway.profile;

import fsc.entity.Profile;

import java.util.ArrayList;

public class ExistingProfileGatewaySpy extends ProfileGatewayStub {
  public String providedUsername;
  static public Boolean profileHasBeenEdited = false;

  public ExistingProfileGatewaySpy(Profile... profiles) {
    super(profiles);
  }

  public ArrayList<Profile> getAllProfiles() {
    return null;
  }

  public Profile getProfile(String username) {
    providedUsername = username;
    return getAProfile();
  }

  public void addProfile(Profile profile) {

  }

  public void save() {
    profileHasBeenEdited = true;
  }

}


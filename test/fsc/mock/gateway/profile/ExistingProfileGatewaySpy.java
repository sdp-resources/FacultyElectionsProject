package fsc.mock.gateway.profile;

import fsc.entity.Profile;

import java.util.List;

public class ExistingProfileGatewaySpy extends ProfileGatewayStub {
  public String providedUsername;
  static public Boolean profileHasBeenEdited = false;

  public ExistingProfileGatewaySpy(Profile... profiles) {
    super(profiles);
  }

  public List<Profile> getAllProfiles() {
    return super.getAllProfiles();
  }

  public Profile getProfile(String username) {
    providedUsername = username;
    return getAProfile();
  }

  public void addProfile(Profile profile) {

  }

  public boolean hasProfile(String username) {
    providedUsername = username;
    return true;
  }


  public void save() {
    profileHasBeenEdited = true;
  }

}


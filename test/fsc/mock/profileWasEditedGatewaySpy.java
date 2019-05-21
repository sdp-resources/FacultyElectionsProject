package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.List;

public class profileWasEditedGatewaySpy implements ProfileGateway {
  static public String providedUsername;
  static public Boolean profileHasBeenEdited = false;

  Profile providedProfile = new Profile("Bob Ross", "rossB12","Arts and Letters", "Tenured");

  public Profile getProfile(String username) {
    providedUsername = username;
    return providedProfile;
  }

  public List<Profile> getAllProfiles() {
    return null;
  }

  public void addProfile(Profile profile) { }

  public boolean isValidDivision(String division) {
    return false;
  }

  public void save()
  {
    profileHasBeenEdited = true;
  }

  public void saveProfile(Profile profile) {

  }
}

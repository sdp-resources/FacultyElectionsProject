package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.List;

public class profileWasEditedGatewaySpy implements ProfileGateway {
  String providedUsername;
  Profile providedProfile = new Profile("Bob Ross", "rossB12","Arts and Letters", "Tenured");

  public Profile getProfileFromUsername(String username) {
    providedUsername = username;
    return providedProfile;
  }

  public List<Profile> getAllProfiles() {
    return null;
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public boolean isValidDivision(String division) {
    return false;
  }

  public void saveProfile(Profile profile) {
    providedProfile = profile;
  }
}

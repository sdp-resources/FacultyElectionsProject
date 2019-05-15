package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.List;

public class profileWasEditedGatewaySpy implements ProfileGateway {
  public Profile getProfileFromUsername(String username) {
    return new Profile("Bob Ross", "rossB12","Arts and Letters", "Tenured");
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
}

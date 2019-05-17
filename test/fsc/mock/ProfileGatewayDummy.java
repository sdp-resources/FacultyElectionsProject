package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.List;

public class ProfileGatewayDummy implements ProfileGateway {
  public Profile getProfileFromUsername(String username) {
    return null;
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

  public void updateProfile(Profile profile) {

  }
}

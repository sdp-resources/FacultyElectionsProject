package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.List;

public class correctProfileGatewayMock implements ProfileGateway {
  Profile testProfile = new Profile("boogie","BoogieA14", "ART","sabbatical");
  public Profile getProfile(){
    return testProfile;
  }

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
}

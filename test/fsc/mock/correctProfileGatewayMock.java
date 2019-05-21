package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.List;

public class correctProfileGatewayMock implements ProfileGateway {
  Profile testProfile = new Profile("boogie","BoogieA14", "ART","sabbatical");
  public Profile getProfile(){
    return testProfile;
  }
  public static String submittedUsername;

  public Profile getProfile(String username) {
    submittedUsername = username;
    return testProfile;
  }

  public List<Profile> getAllProfiles() {
    return null;
  }

  public void addProfile(Profile profile) { }

  public boolean isValidDivision(String division) {
    return false;
  }

  public void save() {

  }
}

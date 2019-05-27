package fixtures;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

public class CanFindProfiles {
  private Profile profile;

  public void setUsername(String username) throws ProfileGateway.InvalidProfileUsernameException {
    profile = TestContext.getProfile(username);
  }

  public String fullname() {
    return profile.getName();
  }

  public String division() {
    return profile.getDivision();
  }

  public String contractType() {
    return profile.getContract();
  }
}


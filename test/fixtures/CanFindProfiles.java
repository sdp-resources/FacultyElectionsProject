package fixtures;

import fsc.gateway.ProfileGateway;
import fsc.viewable.ViewableProfile;

public class CanFindProfiles {
  private ViewableProfile profile;

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


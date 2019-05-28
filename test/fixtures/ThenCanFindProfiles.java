package fixtures;

import fsc.viewable.ViewableProfile;

public class ThenCanFindProfiles {
  private ViewableProfile profile;

  public void setUsername(String username) {
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


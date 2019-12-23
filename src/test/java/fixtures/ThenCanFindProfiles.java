package fixtures;

import fsc.viewable.ViewableProfile;

public class ThenCanFindProfiles {
  private ViewableProfile profile;

  public void setUsername(String username) {
    profile = TestContext.app.getProfile(username);
  }

  public String fullname() {
    return profile.name;
  }

  public String division() {
    return profile.division;
  }

  public String contractType() {
    return profile.contract;
  }
}


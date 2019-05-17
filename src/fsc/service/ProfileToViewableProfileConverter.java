package fsc.service;

import fsc.entity.Profile;
import fsc.viewable.ViewableProfile;

public class ProfileToViewableProfileConverter {

  public ViewableProfile convert(Profile profile) {
    return new ViewableProfile(profile.getName(), profile.getUsername(),
                               profile.getDivision(), profile.getContract());
  }
}

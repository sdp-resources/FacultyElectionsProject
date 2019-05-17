package fsc.mock;

import fsc.entity.Profile;
import fsc.service.ProfileToViewableProfileConverter;
import fsc.viewable.ViewableProfile;

public class ProfileToViewableProfileConverterSpy
      extends ProfileToViewableProfileConverter {
  public Profile profileReceived;

  @Override
  public ViewableProfile convert(Profile profile) {
    profileReceived = profile;
    return super.convert(profile);
  }
}

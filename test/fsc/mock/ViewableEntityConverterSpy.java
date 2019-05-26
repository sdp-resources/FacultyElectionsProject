package fsc.mock;

import fsc.entity.Profile;
import fsc.service.ViewableEntityConverter;
import fsc.viewable.ViewableProfile;

public class ViewableEntityConverterSpy
      extends ViewableEntityConverter {
  public Profile profileReceived;

  @Override
  public ViewableProfile convert(Profile profile) {
    profileReceived = profile;
    return super.convert(profile);
  }
}

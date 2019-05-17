package fsc.service;

import fsc.entity.Profile;
import fsc.viewable.ViewableProfile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProfileToViewableProfileConverterTest {
  @Test
  public void viewableProfileMatchesInformationInProfile()
  {
    String name = "Joe Basic";
    String username = "basicj";
    String division = "Art";
    String contract = "Tenured";

    ProfileToViewableProfileConverter converter = new ProfileToViewableProfileConverter();
    Profile profile = new Profile(name, username, division, contract);
    ViewableProfile convertedViewableProfile = converter.convert(profile);
    ViewableProfile manualViewableProfile = new ViewableProfile(name, username, division, contract);

    assertEquals(manualViewableProfile, convertedViewableProfile);
  }
}

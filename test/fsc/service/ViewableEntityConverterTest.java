package fsc.service;

import fsc.entity.EntityFactory;
import fsc.entity.Profile;
import fsc.entity.SimpleEntityFactory;
import fsc.viewable.ViewableProfile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewableEntityConverterTest {

  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Test
  public void viewableProfileMatchesInformationInProfile() {
    String name = "Joe Basic";
    String username = "basicj";
    String division = "Art";
    String contract = "Tenured";

    ViewableEntityConverter converter = new ViewableEntityConverter();
    Profile profile = entityFactory.createProfile(name, username, division, contract);
    ViewableProfile convertedViewableProfile = converter.convert(profile);
    ViewableProfile manualViewableProfile = new ViewableProfile(name, username, division, contract);

    assertEquals(manualViewableProfile, convertedViewableProfile);
  }
}

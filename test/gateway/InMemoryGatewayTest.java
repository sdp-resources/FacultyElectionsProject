package gateway;

import fsc.entity.Profile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InMemoryGatewayTest {

  private static final String USERNAME = "username";
  private static final String NAME = "name";
  private static final String DIVISION = "division";
  private static final String CONTRACT = "contract";
  private static final String OTHER_USERNAME = "otherUsername";
  private Profile profile;
  private InMemoryGateway gateway;

  @Before
  public void setUp() throws Exception {
    profile = new Profile(NAME, USERNAME, DIVISION, CONTRACT);
    gateway = new InMemoryGateway();
  }

  @Test
  public void addedProfile_canBeFound() {
    gateway.addProfile(profile);
    Profile returnedProfile = gateway.getProfileFromUsername(USERNAME);
    assertEquals(profile, returnedProfile);
  }

  @Test(expected=RuntimeException.class)
  public void missingProfile_cannotBeFound() {
    gateway.addProfile(profile);
    gateway.getProfileFromUsername(OTHER_USERNAME);
  }
}

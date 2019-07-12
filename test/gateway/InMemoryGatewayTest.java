package gateway;

import fsc.entity.*;
import fsc.gateway.Gateway;
import fsc.gateway.ProfileGateway.InvalidProfileUsernameException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class InMemoryGatewayTest {

  private static final String USERNAME = "username";
  private static final String NAME = "name";
  private static final String DIVISION = "division";
  private static final String CONTRACT = "contract";
  private static final String OTHER_USERNAME = "otherUsername";
  private static final String A_CONTRACT_TYPE = "aContractType";
  private static final String NATURAL_SCIENCES_DIVISION = "NatSci";
  private static final Division NAT_SCI_DIVISION = new Division(NATURAL_SCIENCES_DIVISION);
  private Profile profile;
  private Gateway gateway;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setUp() {
    profile = entityFactory.createProfile(NAME, USERNAME, DIVISION, CONTRACT);
    gateway = new InMemoryGateway();
  }

  @Test
  public void addedProfile_canBeFound() throws InvalidProfileUsernameException {
    gateway.addProfile(profile);
    Profile returnedProfile = gateway.getProfile(USERNAME);
    assertEquals(profile, returnedProfile);
  }

  @Test(expected = InvalidProfileUsernameException.class)
  public void missingProfile_cannotBeFound() throws InvalidProfileUsernameException {
    gateway.addProfile(profile);
    gateway.getProfile(OTHER_USERNAME);
  }

  @Test
  public void addedProfile_appearsInAllProfiles() {
    gateway.addProfile(profile);
    assertThat(gateway.getAllProfiles(), hasItem(profile));
  }

  @Test
  public void addedContractTypes_appearInContractTypeList() {
    gateway.addContractType(A_CONTRACT_TYPE);
    assertThat(gateway.getAvailableContractTypes(), hasItem(A_CONTRACT_TYPE));
  }

  @Test
  public void addedDivisions_appearInDivisionList() {
    gateway.addDivision(NAT_SCI_DIVISION);
    assertThat(gateway.getAvailableDivisions(), hasItem(NAT_SCI_DIVISION));
    assertThat(gateway.hasDivision(NATURAL_SCIENCES_DIVISION), is(true));
  }
}

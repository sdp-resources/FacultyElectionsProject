package fsc.interactor;

import fsc.entity.Profile;
import fsc.entity.ProfileToHashMapConverter;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class ProfileToHashMapConverterTests {
  ProfileToHashMapConverter profileToHashMapConverter;

  @Before
  public void setup() {
    Profile profile = new Profile("Frank", "admin", "CS", "inactive");
    profileToHashMapConverter = new ProfileToHashMapConverter(profile);
  }

  @Test
  public void ableToGetNameFromMap() {
    assertEquals("Frank", profileToHashMapConverter.getValueFromMap("Name"));
  }

  @Test
  public void abletoGetUsernameFromMap() {
    assertEquals("admin", profileToHashMapConverter.getValueFromMap("Username"));
  }

  @Test
  public void ableToGetDepartmentFromMap() {
    assertEquals("CS", profileToHashMapConverter.getValueFromMap("Department"));
  }

  @Test
  public void ableToGetContractFromMap() {
    assertEquals("inactive", profileToHashMapConverter.getValueFromMap("Contract"));
  }
}
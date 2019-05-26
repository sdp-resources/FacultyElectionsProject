package fsc.service;

import fsc.entity.Profile;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ProfileToHashMapConverterTests {
  private Map<String, String> map;

  @Before
  public void setup() {
    Profile profile = new Profile("Frank", "admin", "CS", "inactive");
    ProfileToHashMapConverter profileToHashMapConverter = new ProfileToHashMapConverter();
    map = profileToHashMapConverter.createHashMap(profile);
  }

  @Test
  public void ableToGetNameFromMap() {
    assertEquals("Frank", map.get("Name"));
  }

  @Test
  public void abletoGetUsernameFromMap() {
    assertEquals("admin", map.get("Username"));
  }

  @Test
  public void ableToGetDepartmentFromMap() {
    assertEquals("CS", map.get("Department"));
  }

  @Test
  public void ableToGetContractFromMap() {
    assertEquals("inactive", map.get("Contract"));
  }
}
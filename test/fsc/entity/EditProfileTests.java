package fsc.entity;

import fsc.app.AppContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EditProfileTests {

  public static final String PROFILE_NAME = "Adam Jones";
  public static final String PROFILE_USERNAME = "jonesa";
  public static final String DIVISION = "SCI";
  public static final String CONTRACT = "Tenured";
  private Profile profile;

  @Before
  public void setup() {
    profile = AppContext.getEntityFactory()
                        .createProfile(PROFILE_NAME, PROFILE_USERNAME, DIVISION, CONTRACT);
  }

  @Test
  public void readProfileName() {
    assertEquals(PROFILE_NAME, profile.getName());
  }

  @Test
  public void readProfileUsername() {
    assertEquals(PROFILE_USERNAME, profile.getUsername());
  }

  @Test
  public void readDivision() {
    assertEquals(DIVISION, profile.getDivision());
  }

  @Test
  public void readAndSetContractType() {
    String contractStatus = CONTRACT;
    profile.setContract(contractStatus);
    assertEquals(contractStatus, profile.getContract());
  }

  @Test
  public void canSetProfileName() {
    String newName = "Bill Mill";
    profile.setName(newName);
    assertEquals(newName, profile.getName());
  }

  @Test
  public void canSetDivision() {
    String newDepartment = "PLS";
    profile.setDivision(newDepartment);
    assertEquals(newDepartment, profile.getDivision());
  }

  @Test
  public void canSetUsername() {
    String newUsername = "millb";
    profile.setUsername(newUsername);
    assertEquals(newUsername, profile.getUsername());
  }

  @Test
  public void isProfileActive() {
    assertTrue(profile.isActive());
  }

  @Test
  public void canSetInactive() {
    profile.setInactive();
    assertFalse(profile.isActive());
  }

  @Test
  public void canSetActive() {
    profile.setActive();
    assertTrue(profile.isActive());
  }

}

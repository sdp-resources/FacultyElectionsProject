package fsc.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EditProfileTests {

  private Profile profile;

  @Before
  public void setup() {
    profile = new Profile("Adam Jones", "jonesa", "SCI", "Tenured");
  }

  @Test
  public void readProfileName() {
    assertEquals("Adam Jones", profile.getName());
  }

  @Test
  public void readProfileUsername() {
    assertEquals("jonesa", profile.getUsername());
  }

  @Test
  public void readDivision() {
    assertEquals("SCI", profile.getDivision());
  }

  @Test
  public void readAndSetContractType() {
    String contractStatus = "Tenured";
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

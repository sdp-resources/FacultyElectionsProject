package fsc.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EditProfileTests {

  Profile profile;

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
  public void readDepartment() {
    assertEquals("SCI", profile.getDepartment());
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
  public void canSetDepartment() {
    String newDepartment = "PLS";
    profile.setDepartment(newDepartment);
    assertEquals(newDepartment, profile.getDepartment());
  }

  @Test
  public void canSetUsername() {
    String newUsername = "millb";
    profile.setUsername(newUsername);
    assertEquals(newUsername, profile.getUsername());
  }

}

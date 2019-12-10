package dbGateway;

import fsc.entity.Profile;
import fsc.entity.query.Query;
import fsc.gateway.ProfileGateway;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DatabaseProfileTest extends BasicDatabaseTest {
  private Profile profile;

  @Test
  public void canCreateAProfile() throws ProfileGateway.InvalidProfileUsernameException {
    saveTheProfile();
    Profile profile2 = anotherGateway.getProfile("skiadas");
    assertEquals(profile, profile2);
  }

  @Test
  public void canEditAProfile() throws ProfileGateway.InvalidProfileUsernameException {
    saveTheProfile();
    Profile profile2 = anotherGateway.getProfile("skiadas");
    profile2.setName("a new name");
    anotherGateway.commit();
    gateway.refresh(profile);
    assertEquals("a new name", profile.getName());
  }

  @Test
  public void canChangeProfileActiveState() throws ProfileGateway.InvalidProfileUsernameException {
    saveTheProfile();
    Profile profile2 = anotherGateway.getProfile("skiadas");
    profile2.setInactive();
    anotherGateway.commit();
    gateway.refresh(profile);
    assertFalse(profile.isActive());
  }

  @Test
  public void retrieveAllProfiles() {
    saveTwoProfiles();
    List<Profile> allProfiles = anotherGateway.getAllProfiles();
    assertEquals(2, allProfiles.size());
    assertEquals("skiadas", allProfiles.get(0).getUsername());
    assertEquals("wilsont", allProfiles.get(1).getUsername());
  }

  @Test
  public void retrieveProfilesMatchingQuery() {
    saveTwoProfiles();
    Query query = Query.has("contract", "tenured");
    List<Profile> allProfiles = anotherGateway.getProfilesMatching(query);
    assertEquals(1, allProfiles.size());
    assertEquals("skiadas", allProfiles.get(0).getUsername());
  }

  @Test
  public void hasProfile() {
    saveTwoProfiles();
    assertTrue(anotherGateway.hasProfile("skiadas"));
    assertTrue(anotherGateway.hasProfile("wilsont"));
    assertFalse(anotherGateway.hasProfile("another"));
  }

  public void saveTwoProfiles() {
    gateway.addProfile(
          gateway.getEntityFactory()
                 .createProfile("Haris Skiadas", "skiadas",
                                "Natural Sciences", "tenured"));
    gateway.addProfile(
          gateway.getEntityFactory()
                 .createProfile("Theresa Wilson", "wilsont",
                                "Natural Sciences", "tenure-track"));
    gateway.commit();
  }

  private void saveTheProfile() {
    profile = gateway.getEntityFactory()
                     .createProfile("Haris Skiadas", "skiadas", "Natural Sciences", "tenured");
    gateway.addProfile(profile);
    gateway.commit();
  }

}
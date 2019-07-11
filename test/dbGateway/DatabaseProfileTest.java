package dbGateway;

import fsc.entity.Profile;
import fsc.entity.query.Query;
import fsc.gateway.ProfileGateway;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DatabaseProfileTest extends BasicDatabaseTest {
  private Profile profile;

  @Test
  public void canCreateAProfile() throws ProfileGateway.InvalidProfileUsernameException {
    saveTheProfile();
    DatabaseBackedGateway gateway2 = gatewayFactory.beginSession();
    Profile profile2 = gateway2.getProfile("skiadas");
    assertEquals(profile, profile2);
    gateway2.commitAndClose();
  }

  @Test
  public void canEditAProfile() throws ProfileGateway.InvalidProfileUsernameException {
    saveTheProfile();
    DatabaseBackedGateway gateway2 = gatewayFactory.beginSession();
    Profile profile2 = gateway2.getProfile("skiadas");
    profile2.setName("a new name");
    gateway2.commitAndClose();
    gateway.refresh(profile);
    assertEquals("a new name", profile.getName());
  }

  @Test
  public void canChangeProfileActiveState() throws ProfileGateway.InvalidProfileUsernameException {
    saveTheProfile();
    DatabaseBackedGateway gateway2 = gatewayFactory.beginSession();
    Profile profile2 = gateway2.getProfile("skiadas");
    profile2.setInactive();
    gateway2.commitAndClose();
    gateway.refresh(profile);
    assertFalse(profile.isActive());
  }

  @Test
  public void retrieveAllProfiles() {
    saveTwoProfiles();
    DatabaseBackedGateway gateway2 = gatewayFactory.beginSession();
    List<Profile> allProfiles = gateway2.getAllProfiles();
    assertEquals(2, allProfiles.size());
    assertEquals("skiadas", allProfiles.get(0).getUsername());
    assertEquals("wilsont", allProfiles.get(1).getUsername());
    gateway2.commitAndClose();
  }

  @Test
  public void retrieveProfilesMatchingQuery() {
    saveTwoProfiles();
    DatabaseBackedGateway gateway2 = gatewayFactory.beginSession();
    Query query = Query.has("contract", "tenured");
    List<Profile> allProfiles = gateway2.getProfilesMatching(query);
    assertEquals(1, allProfiles.size());
    assertEquals("skiadas", allProfiles.get(0).getUsername());
    gateway2.commitAndClose();
  }

  public void saveTwoProfiles() {
    gateway.getEntityFactory()
           .createProfile("Haris Skiadas", "skiadas", "Natural Sciences", "tenured");
    gateway.getEntityFactory()
           .createProfile("Theresa Wilson", "wilsont", "Natural Sciences", "tenure-track");
    gateway.commit();
  }

  private void saveTheProfile() {
    profile = gateway.getEntityFactory()
                     .createProfile("Haris Skiadas", "skiadas", "Natural Sciences", "tenured");
    gateway.commit();
  }

}
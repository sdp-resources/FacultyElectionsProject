package dbGateway;

import fsc.entity.Profile;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DatabaseBackedGatewayTest {
  private final DatabaseBackedGatewayFactory gatewayFactory = new DatabaseBackedGatewayFactory();
  private Profile profile;
  private DatabaseBackedGateway gateway;

  @Before
  public void setUp() {
    gateway = gatewayFactory.beginSession();
  }

  @After
  public void tearDown() {
    gateway.close();
  }

  @Test
  public void canCreateAProfile() {
    saveTheProfile();
    DatabaseBackedGateway gateway2 = gatewayFactory.beginSession();
    Profile profile2 = gateway2.findProfile("skiadas");
    assertEquals(profile, profile2);
    gateway2.commitAndClose();
  }

  @Test
  public void canEditAProfile() {
    saveTheProfile();
    DatabaseBackedGateway gateway2 = gatewayFactory.beginSession();
    Profile profile2 = gateway2.findProfile("skiadas");
    profile2.setName("a new name");
    gateway2.commitAndClose();
    gateway.refresh(profile);
    assertEquals("a new name", profile.getName());
  }

  @Test
  public void canChangeProfileActiveState() {
    saveTheProfile();
    DatabaseBackedGateway gateway2 = gatewayFactory.beginSession();
    Profile profile2 = gateway2.findProfile("skiadas");
    profile2.setInactive();
    gateway2.commitAndClose();
    gateway.refresh(profile);
    assertFalse(profile.isActive());
  }

  public void saveTheProfile() {
    profile = gateway.createProfile("Haris Skiadas", "skiadas", "Natural Sciences", "tenured");
    gateway.commit();
  }

}
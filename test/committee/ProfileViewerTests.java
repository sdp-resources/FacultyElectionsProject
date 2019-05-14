package committee;

import committee.Profile;
import committee.ProfileViewer;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class ProfileViewerTests {
  ProfileViewer profileViewer;

  @Before
  public void setup() {
    Profile profile = new Profile("Frank", "admin", "CS", "inactive");
    profileViewer = new ProfileViewer(profile);
  }

  @Test
  public void ableToGetNameFromMap() {
    assertEquals("Frank", profileViewer.getValueFromMap("Name"));
  }

  @Test
  public void abletoGetUsernameFromMap() {
    assertEquals("admin",profileViewer.getValueFromMap("Username"));
  }

  @Test
  public void ableToGetDepartmentFromMap() {
    assertEquals("CS", profileViewer.getValueFromMap("Department"));
  }

  @Test
  public void ableToGetContractFromMap() {
    assertEquals("inactive", profileViewer.getValueFromMap("Contract"));
  }
}
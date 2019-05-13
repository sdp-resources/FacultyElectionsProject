import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class ProfileViewerTests {
  ProfileViewer profileView;

  @Before
  public void setup() {
    Profile profile = new Profile("Frank","CS");
    profile.setContract("inactive");
    profileView = new ProfileViewer(profile);
  }

  @Test
  public void ableToGetNameFromProfile() {
    assertEquals("Frank", profileView.name);
  }

  @Test
  public void ableToGetStatusFromProfile() {
    assertFalse(profileView.status);
  }

  @Test
  public void ableToGetDepartmentFromProfile() {
    assertEquals("CS", profileView.department);
  }
}





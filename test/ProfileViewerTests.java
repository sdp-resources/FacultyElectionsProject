import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ProfileViewerTests {
  @Test
  public void ableToGetNameFromProfile() {
    Profile inactiveProfileNamedFrank = new Profile("Frank", "inactive");
    ProfileViewer view = new ProfileViewer(inactiveProfileNamedFrank);
    assertEquals(view.name, inactiveProfileNamedFrank.getName());
  }

  @Test
  public void ableToGetStatusFromProfile() {
    Profile profile = new Profile("Frank", "sabbatical");
    ProfileViewer view = new ProfileViewer(profile);
    assertFalse(view.isActive);
  }
}





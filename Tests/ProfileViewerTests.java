import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProfileViewerTests {
  @Test
  public void ableToGetNameFromProfile() {
    MockProfile mockProfile = new MockProfile();
    ProfileViewer view = new ProfileViewer(mockProfile);
    assertEquals(view.name, mockProfile.getName());
  }
}

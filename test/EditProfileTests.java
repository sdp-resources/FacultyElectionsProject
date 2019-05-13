import org.junit.Before;
import org.junit.Test;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class EditProfileTests {

  Profile profile;

  @Before
  public void setup() {
    profile = new Profile("Adam Jones", "SCI");
  }

  @Test
  public void readProfileName() {
    assertEquals("Adam Jones", profile.getName());
  }

  @Test
  public void readDepartment() {
    assertEquals("SCI", profile.getDepartment());
  }



}

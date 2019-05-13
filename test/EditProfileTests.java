import org.junit.Before;
import org.junit.Test;

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

  @Test
  public void readAndSetContractType() {
    profile.setContract("Tenured");
    assertEquals("Tenured",profile.getContract());
  }

  @Test
  public void canSetProfileName() {
    profile.setName("Bill Mill");
    assertEquals("Bill Mill", profile.getName());
  }

  @Test
  public void canSetDepartment() {
    profile.setDepartment("PLS");
    assertEquals("PLS",profile.getDepartment());
  }

}

package fsc.viewable;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ViewableProfileTest {
  @Test
  public void constructorPopulatesFields()
  {
    String name = "Joe Basic";
    String username = "basicj";
    String division = "Art";
    String contract = "Tenured";

    ViewableProfile viewableProfile = new ViewableProfile(name, username, division, contract);

    assertEquals(name, viewableProfile.Name);
    assertEquals(username, viewableProfile.Username);
    assertEquals(division, viewableProfile.Division);
    assertEquals(contract, viewableProfile.Contract);
  }

  @Test
  public void sameFieldsAreEqual()
  {
    String name = "Joe Basic";
    String username = "basicj";
    String division = "Art";
    String contract = "Tenured";

    ViewableProfile a = new ViewableProfile(name, username, division, contract);
    ViewableProfile b = new ViewableProfile(name, username, division, contract);

    assertTrue(a.equals(b));
    assertTrue(a.hashCode() == b.hashCode());
  }

  @Test
  public void differntFieldsAreNotEqual()
  {
    String name = "Joe Basic";
    String username = "basicj";
    String divisionA = "Art";
    String divisionB = "English";
    String contract = "Tenured";

    ViewableProfile a = new ViewableProfile(name, username, divisionA, contract);
    ViewableProfile b = new ViewableProfile(name, username, divisionB, contract);

    assertFalse(a.equals(b));
    assertFalse(a.hashCode() == b.hashCode());
  }
}

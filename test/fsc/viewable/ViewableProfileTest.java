package fsc.viewable;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ViewableProfileTest {
  @Test
  public void constructorPopulatesFields() {
    String name = "Joe Basic";
    String username = "basicj";
    String division = "Art";
    String contract = "Tenured";

    ViewableProfile viewableProfile = new ViewableProfile(name, username, division, contract);

    assertEquals(name, viewableProfile.name);
    assertEquals(username, viewableProfile.username);
    assertEquals(division, viewableProfile.division);
    assertEquals(contract, viewableProfile.contract);
  }

  @Test
  public void sameFieldsAreEqual() {
    String name = "Joe Basic";
    String username = "basicj";
    String division = "Art";
    String contract = "Tenured";

    ViewableProfile a = new ViewableProfile(name, username, division, contract);
    ViewableProfile b = new ViewableProfile(name, username, division, contract);

    TestCase.assertEquals(a, b);
    TestCase.assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  public void differntFieldsAreNotEqual() {
    String name = "Joe Basic";
    String username = "basicj";
    String divisionA = "Art";
    String divisionB = "English";
    String contract = "Tenured";

    ViewableProfile a = new ViewableProfile(name, username, divisionA, contract);
    ViewableProfile b = new ViewableProfile(name, username, divisionB, contract);

    assertNotEquals(a, b);
    assertNotEquals(a.hashCode(), b.hashCode());
  }
}

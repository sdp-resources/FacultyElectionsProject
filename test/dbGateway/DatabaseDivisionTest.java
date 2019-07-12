package dbGateway;

import fsc.entity.Division;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DatabaseDivisionTest extends BasicDatabaseTest {
  private Division division;

  @Test
  public void canCreateADivision() {
    saveTheDivision();
    List<Division> divisions = anotherGateway.getAvailableDivisions();
    assertEquals(1, divisions.size());
    assertEquals(division, divisions.get(0));
    assertEquals(true, anotherGateway.hasDivision(division.getName()));
  }

  private void saveTheDivision() {
    division = gateway.getEntityFactory()
                     .createDivision("Natural Sciences");
    gateway.addDivision(division);
    gateway.commit();
  }

}

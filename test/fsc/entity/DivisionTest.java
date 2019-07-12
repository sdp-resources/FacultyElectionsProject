package fsc.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DivisionTest {

  private Division testDivision;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setup() {
    testDivision = entityFactory.createDivision("SCI");
  }

  @Test
  public void getDivisionNameTest() {
    assertEquals("SCI", testDivision.getName());
  }

  @Test
  public void setDivisionNameTest() {
    testDivision.setName("ART");
    assertEquals("ART", testDivision.getName());
  }

}

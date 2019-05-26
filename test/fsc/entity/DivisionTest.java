package fsc.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DivisionTest {

  private Division testDivision;

  @Before
  public void setup() {
    testDivision = new Division("SCI");
  }

  @Test
  public void getDivisionNameTest() {
    assertEquals("SCI", testDivision.getDivisionName());
  }

  @Test
  public void setDivisionNameTest() {
    testDivision.setDivisionName("ART");
    assertEquals("ART", testDivision.getDivisionName());
  }

}

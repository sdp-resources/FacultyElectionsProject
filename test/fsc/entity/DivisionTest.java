package fsc.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DivisionTest {

  Division testDivision;

  @Before
  public void setup() {
    testDivision = new Division("SCI");
  }


  @Test
  public void getDivisionName() {
    assertEquals("SCI", testDivision.getDivisionName());
  }


}

package fsc.interactor.election;

import fsc.entity.EntityFactory;
import fsc.entity.SimpleEntityFactory;

import static org.junit.Assert.assertEquals;

public class ElectionTest {
  protected EntityFactory entityFactory = new SimpleEntityFactory();

  public void assertElectionIdEquals(Long electionId, long expected) {
    assertEquals(expected, electionId.longValue());
  }

}

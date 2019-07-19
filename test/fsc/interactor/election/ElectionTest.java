package fsc.interactor.election;

import static org.junit.Assert.assertEquals;

public class ElectionTest {
  public void assertElectionIdEquals(Long electionId, long expected) {
    assertEquals(expected, electionId.longValue());
  }
}

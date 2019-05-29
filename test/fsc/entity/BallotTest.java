package fsc.entity;

import fsc.mock.EntityStub;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BallotTest {

  private Ballot ballot;
  private Profile testCandidate1;
  private Profile testCandidate2;
  private Profile testCandidate3;

  @Before
  public void setUp() {
    ballot = new Ballot();
    testCandidate1 = EntityStub.getProfile(0);
    testCandidate2 = EntityStub.getProfile(1);
    testCandidate3 = EntityStub.getProfile(2);
  }

  @Test
  public void isCreatedBallotEmpty() {
    assertTrue(ballot.isEmpty());
  }

  @Test
  public void addingCandidateResultInNonEmptyBallot() {
    ballot.add(testCandidate1);
    assertFalse(ballot.isEmpty());
  }

  @Test
  public void addingOneCandidateIncreasesSizeByOne() {
    ballot.add(testCandidate1);
    assertEquals(1, ballot.size());
  }

  @Test
  public void canGetCandidateFromBallotByIndexing() {
    ballot.add(testCandidate1);
    assertEquals(testCandidate1, ballot.get(0));
  }

  @Test
  public void addingMultipleCandidateIncreasesSize() {
    ballot.add(testCandidate1);
    ballot.add(testCandidate2);
    ballot.add(testCandidate3);
    assertEquals(3, ballot.size());
  }

  @Test
  public void checkingMultipleCandidateOfBallot() {
    ballot.add(testCandidate1);
    ballot.add(testCandidate2);
    ballot.add(testCandidate3);
    assertEquals(testCandidate1, ballot.get(0));
    assertEquals(testCandidate2, ballot.get(1));
    assertEquals(testCandidate3, ballot.get(2));
  }

  @Test
  public void removeOneCandidateMakesBallotEmpty() throws Ballot.NoProfileInBallotException {
    ballot.add(testCandidate1);
    ballot.remove(testCandidate1);
    assertTrue(ballot.isEmpty());
  }

  @Test
  public void candidateIsRemovedFromBallot() throws Ballot.NoProfileInBallotException {
    ballot.add(testCandidate1);
    ballot.add(testCandidate2);
    ballot.remove(testCandidate1);
    assertEquals(testCandidate2, ballot.get(0));
  }

  @Test
  public void removeOneCandidateDecreasesSizeByOne() throws Ballot.NoProfileInBallotException {
    ballot.add(testCandidate1);
    ballot.remove(testCandidate1);
    assertEquals(0, ballot.size());
    assertEquals(0, ballot.sizeCandidates());
  }

}

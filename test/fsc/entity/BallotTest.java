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
    ballot.addCandidate(testCandidate1);
    assertFalse(ballot.isEmpty());
  }

  @Test
  public void addingOneCandidateIncreasesSizeByOne() {
    ballot.addCandidate(testCandidate1);
    assertEquals(1, ballot.size());
  }

  @Test
  public void canGetCandidateFromBallotByIndexing() {
    ballot.addCandidate(testCandidate1);
    assertTrue(ballot.get(0).matchesProfile(testCandidate1));
  }

  @Test
  public void addingMultipleCandidateIncreasesSize() {
    ballot.addCandidate(testCandidate1);
    ballot.addCandidate(testCandidate2);
    ballot.addCandidate(testCandidate3);
    assertEquals(3, ballot.size());
  }

  @Test
  public void checkingMultipleCandidateOfBallot() {
    ballot.addCandidate(testCandidate1);
    ballot.addCandidate(testCandidate2);
    ballot.addCandidate(testCandidate3);
    assertTrue(ballot.get(0).matchesProfile(testCandidate1));
    assertTrue(ballot.get(1).matchesProfile(testCandidate2));
    assertTrue(ballot.get(2).matchesProfile(testCandidate3));
  }

  @Test
  public void removeOneCandidateMakesBallotEmpty() throws Ballot.NoProfileInBallotException {
    ballot.addCandidate(testCandidate1);
    ballot.remove(testCandidate1);
    assertTrue(ballot.isEmpty());
  }

  @Test
  public void candidateIsRemovedFromBallot() throws Ballot.NoProfileInBallotException {
    ballot.addCandidate(testCandidate1);
    ballot.addCandidate(testCandidate2);
    ballot.remove(testCandidate1);
    assertTrue(ballot.get(0).matchesProfile(testCandidate2));
  }

  @Test
  public void removeOneCandidateDecreasesSizeByOne() throws Ballot.NoProfileInBallotException {
    ballot.addCandidate(testCandidate1);
    ballot.remove(testCandidate1);
    assertEquals(0, ballot.size());
  }

}

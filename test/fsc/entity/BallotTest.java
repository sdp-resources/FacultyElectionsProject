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
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setUp() {
    ballot = entityFactory.createBallot();
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
    ballot.add(entityFactory.createCandidate(testCandidate1, ballot));
    assertFalse(ballot.isEmpty());
  }

  @Test
  public void addingOneCandidateIncreasesSizeByOne() {
    ballot.add(entityFactory.createCandidate(testCandidate1, ballot));
    assertEquals(1, ballot.size());
  }

  @Test
  public void canGetCandidateFromBallotByIndexing() {
    ballot.add(entityFactory.createCandidate(testCandidate1, ballot));
    assertTrue(ballot.get(0).matchesProfile(testCandidate1));
  }

  @Test
  public void addingMultipleCandidateIncreasesSize() {
    ballot.add(entityFactory.createCandidate(testCandidate1, ballot));
    ballot.add(entityFactory.createCandidate(testCandidate2, ballot));
    ballot.add(entityFactory.createCandidate(testCandidate3, ballot));
    assertEquals(3, ballot.size());
  }

  @Test
  public void checkingMultipleCandidateOfBallot() {
    ballot.add(entityFactory.createCandidate(testCandidate1, ballot));
    ballot.add(entityFactory.createCandidate(testCandidate2, ballot));
    ballot.add(entityFactory.createCandidate(testCandidate3, ballot));
    assertTrue(ballot.get(0).matchesProfile(testCandidate1));
    assertTrue(ballot.get(1).matchesProfile(testCandidate2));
    assertTrue(ballot.get(2).matchesProfile(testCandidate3));
  }

  @Test
  public void removeOneCandidateMakesBallotEmpty() throws Ballot.NoProfileInBallotException {
    ballot.add(entityFactory.createCandidate(testCandidate1, ballot));
    ballot.remove(testCandidate1);
    assertTrue(ballot.isEmpty());
  }

  @Test
  public void candidateIsRemovedFromBallot() throws Ballot.NoProfileInBallotException {
    ballot.add(entityFactory.createCandidate(testCandidate1, ballot));
    ballot.add(entityFactory.createCandidate(testCandidate2, ballot));
    ballot.remove(testCandidate1);
    assertTrue(ballot.get(0).matchesProfile(testCandidate2));
  }

  @Test
  public void removeOneCandidateDecreasesSizeByOne() throws Ballot.NoProfileInBallotException {
    ballot.add(entityFactory.createCandidate(testCandidate1, ballot));
    ballot.remove(testCandidate1);
    assertEquals(0, ballot.size());
  }

}

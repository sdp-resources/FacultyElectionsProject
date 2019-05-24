package fsc.entity;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BallotTest {

  private Ballot ballot;

  @Before
  public void setUp() {
    ballot = new Ballot();
  }

  @Test
  public void isCreatedBallotEmpty (){
    assertTrue(ballot.isEmpty());
  }

  @Test
  public void addingCandidateResultInNonEmptyBallot() throws Ballot.NoProfileInBallotException {
    Profile testCandidate = createAndAddOneCandidate();
    assertFalse(ballot.isEmpty());
  }

  @Test
  public void addingOneCandidateIncreasesSizeByOne() throws Ballot.NoProfileInBallotException {
    Profile testCandidate = createAndAddOneCandidate();
    assertEquals(1, ballot.size());
  }

  @Test
  public void canGetCandidateFromBallotByIndexing() throws Ballot.NoProfileInBallotException {
    Profile testCandidate = createAndAddOneCandidate();
    assertEquals(testCandidate, ballot.get(0));
  }

  @Test
  public void addingMultipleCandidateIncreasesSize() throws Ballot.NoProfileInBallotException {
    createAndAddManyCandidates();
    assertEquals(3, ballot.size());
  }

  @Test
  public void checkingMultipleCandidateOfBallot(){
    Profile testCandidate1 = new Profile("name1", "username1", "department1",
                                                   "contract1");
    Profile testCandidate2 = new Profile("name2", "username2","department2",
                                                         "contract2");
    Profile testCandidate3 = new Profile("name3", "username3","department3",
                                                         "contract3");
    ballot.add(testCandidate1);
    ballot.add(testCandidate2);
    ballot.add(testCandidate3);
    assertEquals(testCandidate1, ballot.get(0));
    assertEquals(testCandidate2, ballot.get(1));
    assertEquals(testCandidate3, ballot.get(2));
  }

  @Test
  public void removeOneCandidateMakesBallotEmpty() throws Ballot.NoProfileInBallotException{
    Profile testCandidate = new Profile("name", "username", "division", "contract");
    ballot.add(testCandidate);
    ballot.remove(testCandidate);
    assertTrue(ballot.isEmpty());
  }

  @Test
  public void candidateIsRemovedFromBallot() throws Ballot.NoProfileInBallotException{
    Profile testCandidate1 = new Profile("name1", "username1", "department1",
                                                         "contract1");
    Profile testCandidate2 = new Profile("name2", "username2","department2",
                                                         "contract2");
    ballot.add(testCandidate1);
    ballot.add(testCandidate2);
    ballot.remove(testCandidate1);
    assertEquals(testCandidate2, ballot.get(0));
  }


  @Test
  public void removeOneCandidateDecreasesSizeByOne() throws Ballot.NoProfileInBallotException{
    Profile testCandidate = new Profile("name", "username", "division", "contract");
    ballot.add(testCandidate);
    ballot.remove(testCandidate);
    assertEquals(0, ballot.size());
    assertEquals(0, ballot.sizeCandidates());
  }

  private Profile createAndAddOneCandidate() {
    Profile testCandidate = new Profile("name", "username", "division",
                                                        "contract");
    ballot.add(testCandidate);
    return testCandidate;
  }

  private void createAndAddManyCandidates() {
    Profile testCandidate1 = new Profile("name1", "username1","department1",
                                             "contract1");
    Profile testCandidate2 = new Profile("name2", "username2","department2",
                                                         "contract2");
    Profile testCandidate3 =new Profile("name3", "username3","department3",
                                                         "contract3");
    ballot.add(testCandidate1);
    ballot.add(testCandidate2);
    ballot.add(testCandidate3);
  }


}

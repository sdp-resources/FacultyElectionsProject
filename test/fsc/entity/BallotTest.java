package fsc.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BallotTest {

  private Ballot ballot;

  @Before
  public void setUp() throws Exception {
    ballot = new Ballot();
  }

  @Test
  public void isCreatedBallotEmpty (){
    assertEquals(true, ballot.isEmpty());
  }

  @Test
  public void addingCandidateResultInNonEmptyBallot() {
    Profile testProfile = createAndAddOneProfile();
    assertEquals(false, ballot.isEmpty());
  }

  @Test
  public void addingOneCandidateIncreasesSizeByOne(){
    Profile testProfile = createAndAddOneProfile();
    assertEquals(1, ballot.size());
  }

  @Test
  public void canGetCandidateFromBallotByIndexing(){
    Profile testProfile = createAndAddOneProfile();
    assertEquals(testProfile, ballot.get(0));
  }

  @Test
  public void addingMultipleCandidateIncreasesSize(){
    createAndAddManyProfiles();
    assertEquals(3, ballot.size());
  }

  @Test
  public void checkingMultipleCandidateOfBallot(){
    Profile testProfile1 = new Profile("name1", "username1", "department1", "contract1");
    Profile testProfile2 = new Profile("name2", "username2","department2", "contract2");
    Profile testProfile3 = new Profile("name3", "username3","department3", "contract3");
    ballot.add(testProfile1);
    ballot.add(testProfile2);
    ballot.add(testProfile3);
    assertEquals(testProfile1, ballot.get(0));
    assertEquals(testProfile2, ballot.get(1));
    assertEquals(testProfile3, ballot.get(2));
  }

  @Test
  public void removeOneCandidateMakesBallotEmpty(){
    Profile testProfile = new Profile("name", "username", "division", "contract");
    ballot.add(testProfile);
    ballot.remove(testProfile);
    assertEquals(true, ballot.isEmpty());
  }

  @Test
  public void candidateIsRemovedFromBallot(){
    Profile testProfile1 = new Profile("name1", "username1","department1", "contract1");
    Profile testProfile2 = new Profile("name2", "username2", "department2", "contract2");
    ballot.add(testProfile1);
    ballot.add(testProfile2);
    ballot.remove(testProfile1);
    assertEquals(testProfile2, ballot.get(0));
  }


  @Test
  public void removeOneCandidateDecreasesSizeByOne(){
    Profile testProfile = new Profile("name", "username", "division", "contract");
    ballot.add(testProfile);
    ballot.remove(testProfile);
    assertEquals(0, ballot.size());
  }

  private Profile createAndAddOneProfile() {
    Profile testProfile = new Profile("name", "username", "division", "contract");
    ballot.add(testProfile);
    return testProfile;
  }

  private void createAndAddManyProfiles() {
    Profile testProfile1 = new Profile("name1", "username1","department1", "contract1");
    Profile testProfile2 = new Profile("name2", "username2", "department2", "contract2");
    Profile testProfile3 = new Profile("name3", "username3", "department3", "contract3");
    ballot.add(testProfile1);
    ballot.add(testProfile2);
    ballot.add(testProfile3);
  }

  @Test
  public void ballotHasID() {
    String id = ballot.getID();
    assertEquals(ballot.getID(), id);
  }

}

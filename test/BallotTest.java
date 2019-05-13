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
    Profile testProfile = new Profile("name", "department");
    ballot.add(testProfile);
    assertEquals(false, ballot.isEmpty());
  }

  @Test
  public void candidateAddedToBallotList(){
    Profile testProfile = new Profile("name","department");
    ballot.add(testProfile);

  }
}

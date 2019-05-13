import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BallotCreatorTest {

  private BallotCreator ballotCreator;

  @Before
  public void setUp() throws Exception {
    ballotCreator = new BallotCreator();
  }

  @Test
  public void EmptyBallot(){
    Ballot ballot = ballotCreator.getBallot(new AlwaysFalseQuery());
    assertTrue(ballot.isEmpty());
  }



}

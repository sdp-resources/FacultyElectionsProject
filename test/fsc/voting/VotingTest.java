package fsc.voting;

import fsc.entity.Profile;
import fsc.entity.Vote;
import fsc.mock.EntityStub;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class VotingTest {
  ElectionRecord record = new ElectionRecord();
  private Profile cA = EntityStub.getProfile("A");
  private Profile cB = EntityStub.getProfile("B");
  private Profile cC = EntityStub.getProfile("C");

  @Ignore
  @Test
  public void candidateWithMajorityWinsRightAway() {
    List<Vote> votes = List.of(Vote.of(cA, cB, cC),
                               Vote.of(cA, cC, cB),
                               Vote.of(cB, cA, cC));
    for (Vote vote : votes) {
      record.add(vote);
    }
    record.runElection();
    assertEquals(1, record.numberOfRounds());
    assertEquals(VotingRoundResult.win(cA), record.getRound(1).getResult());
  }
}

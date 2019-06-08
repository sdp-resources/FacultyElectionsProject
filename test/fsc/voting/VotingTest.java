package fsc.voting;

import fsc.entity.Profile;
import fsc.entity.Vote;
import fsc.mock.EntityStub;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VotingTest {
  private ElectionRecord record;

  private Profile cA = EntityStub.getProfile("A");
  private Profile cB = EntityStub.getProfile("B");
  private Profile cC = EntityStub.getProfile("C");

  @Test
  public void candidateWithMajorityWinsRightAway() {
    assertVotes_produceResults(List.of(Vote.of(cA, cB, cC),
                                       Vote.of(cA, cC, cB),
                                       Vote.of(cB, cA, cC)),
                               List.of(VotingRoundResult.win(cA)));
    assertVotes_produceResults(List.of(Vote.of(cA),
                                       Vote.of(),
                                       Vote.of()),
                               List.of(VotingRoundResult.win(cA)));
  }

  @Test
  public void candidateWithFewestFirstPlacesIsEliminatedFirst() {
    assertVotes_produceResults(
          List.of(Vote.of(cA, cB, cC),
                  Vote.of(cC, cA, cB),
                  Vote.of(cB, cA, cC),
                  Vote.of(cA, cB, cC),
                  Vote.of(cB, cA, cC)),
          List.of(VotingRoundResult.eliminate(cC),
                  VotingRoundResult.win(cA)));

  }

  @Test
  public void whenTiedForLastOneOfTheCandidatesIsRemoved() {
    assertVotes_produceResults(
          List.of(Vote.of(cA, cB, cC),
                  Vote.of(cC, cB, cA),
                  Vote.of(cB, cC, cA),
                  Vote.of(cA, cC, cB)),
          List.of(VotingRoundResult.tied(cB, cC),
                  VotingRoundResult.tied(cA, cC),
                  VotingRoundResult.win(cC)));

  }

  private void assertVotes_produceResults(List<Vote> votes, List<VotingRoundResult> results) {
    record = new ElectionRecord(votes);
    record.runElection();
    assertEquals(results.size(), record.numberOfRounds());
    Iterator<VotingRoundResult> expected = results.iterator();
    for (VotingRound round : record) {
      assertTrue(expected.hasNext());
      assertEquals(expected.next(), round.getResult());

    }
  }
}

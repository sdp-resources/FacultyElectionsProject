package fsc.voting;

import fsc.app.AppContext;
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
    assertVotes_produceResults(List.of(AppContext.getEntityFactory().createVote(cA, cB, cC),
                                       AppContext.getEntityFactory().createVote(cA, cC, cB),
                                       AppContext.getEntityFactory().createVote(cB, cA, cC)),
                               List.of(VotingRoundResult.win(cA)));
    assertVotes_produceResults(List.of(AppContext.getEntityFactory().createVote(cA),
                                       AppContext.getEntityFactory().createVote(),
                                       AppContext.getEntityFactory().createVote()),
                               List.of(VotingRoundResult.win(cA)));
  }

  @Test
  public void candidateWithFewestFirstPlacesIsEliminatedFirst() {
    assertVotes_produceResults(
          List.of(AppContext.getEntityFactory().createVote(cA, cB, cC),
                  AppContext.getEntityFactory().createVote(cC, cA, cB),
                  AppContext.getEntityFactory().createVote(cB, cA, cC),
                  AppContext.getEntityFactory().createVote(cA, cB, cC),
                  AppContext.getEntityFactory().createVote(cB, cA, cC)),
          List.of(VotingRoundResult.eliminate(cC),
                  VotingRoundResult.win(cA)));

  }

  @Test
  public void whenTiedForLastOneOfTheCandidatesIsRemoved() {
    assertVotes_produceResults(
          List.of(AppContext.getEntityFactory().createVote(cA, cB, cC),
                  AppContext.getEntityFactory().createVote(cC, cB, cA),
                  AppContext.getEntityFactory().createVote(cB, cC, cA),
                  AppContext.getEntityFactory().createVote(cA, cC, cB)),
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

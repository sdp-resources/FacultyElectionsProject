package fsc.voting;

import fsc.entity.*;
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
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Test
  public void candidateWithMajorityWinsRightAway() {
    assertVotes_produceResults(List.of(entityFactory.createVote(cA, cB, cC),
                                       entityFactory.createVote(cA, cC, cB),
                                       entityFactory.createVote(cB, cA, cC)),
                               List.of(VotingRoundResult.win(cA)));
    assertVotes_produceResults(List.of(entityFactory.createVote(cA),
                                       entityFactory.createVote(),
                                       entityFactory.createVote()),
                               List.of(VotingRoundResult.win(cA)));
  }

  @Test
  public void candidateWithFewestFirstPlacesIsEliminatedFirst() {
    assertVotes_produceResults(
          List.of(entityFactory.createVote(cA, cB, cC),
                  entityFactory.createVote(cC, cA, cB),
                  entityFactory.createVote(cB, cA, cC),
                  entityFactory.createVote(cA, cB, cC),
                  entityFactory.createVote(cB, cA, cC)),
          List.of(VotingRoundResult.eliminate(cC),
                  VotingRoundResult.win(cA)));

  }

  @Test
  public void whenTiedForLastOneOfTheCandidatesIsRemoved() {
    assertVotes_produceResults(
          List.of(entityFactory.createVote(cA, cB, cC),
                  entityFactory.createVote(cC, cB, cA),
                  entityFactory.createVote(cB, cC, cA),
                  entityFactory.createVote(cA, cC, cB)),
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

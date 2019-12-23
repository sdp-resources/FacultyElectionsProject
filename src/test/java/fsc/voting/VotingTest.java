package fsc.voting;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VotingTest {

  private VoteTarget cA = VoteTarget.from("A");
  private VoteTarget cB = VoteTarget.from("B");
  private VoteTarget cC = VoteTarget.from("C");

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
  public void tiesForFirstPlaceReturnSpecialResult() {
    assertVotes_produceResults(
          List.of(Vote.of(cA, cB),
                  Vote.of(cB, cA)),
          List.of(VotingRoundResult.tiedForFirst(cA, cB)));
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
                  VotingRoundResult.win(cA)));

  }

  @Test
  public void MultipleRoundsWorkProperly() {
    assertVotes_produceTotalResults(
          List.of(Vote.of(cA, cB, cC),
                  Vote.of(cC, cB, cA),
                  Vote.of(cB, cC, cA),
                  Vote.of(cB, cC, cA),
                  Vote.of(cA, cC, cB)),
          List.of(
                List.of(VotingRoundResult.eliminate(cC),
                        VotingRoundResult.win(cB)),
                List.of(VotingRoundResult.win(cC)),
                List.of(VotingRoundResult.win(cA))));
    assertVotes_produceTotalResults(
          List.of(Vote.of(cA, cB, cC),
                  Vote.of(cC, cB, cA),
                  Vote.of(cB, cC, cA),
                  Vote.of(cA, cC, cB)),
          List.of(
                List.of(VotingRoundResult.tied(cB, cC),
                        VotingRoundResult.win(cA)),
                List.of(VotingRoundResult.tiedForFirst(cB, cC))));
    assertVotes_produceTotalResults(
          List.of(Vote.of(cA, cB, cC),
                  Vote.of(cB, cA, cC),
                  Vote.of(cB, cC, cA),
                  Vote.of(cA, cC, cB)),
          List.of(
                List.of(VotingRoundResult.eliminate(cC),
                        VotingRoundResult.tiedForFirst(cA, cB)),
                List.of(VotingRoundResult.win(cC))));
  }

  private void assertVotes_produceTotalResults(
        List<Vote> votes, List<List<VotingRoundResult>> results
  ) {
    FullElectionRecord record = new FullElectionRecord(Vote.snapshot(votes));
    record.runElection();
    List<ElectionRecord> electionRecords = record.getElectionRecords();
    assertEquals(results.size(), electionRecords.size());
    Iterator<List<VotingRoundResult>> expected = results.iterator();
    for (ElectionRecord electionRecord : electionRecords) {
      assertTrue(expected.hasNext());
      Iterator<VotingRoundResult> nextRound = expected.next().iterator();
      for (VotingRound round : electionRecord) {
        assertTrue(nextRound.hasNext());
        assertEquals(nextRound.next(), round.getResult());
      }
    }
  }

  private void assertVotes_produceResults(List<Vote> votes, List<VotingRoundResult> results) {
    ElectionRecord record = new ElectionRecord(Vote.snapshot(votes));
    record.runElection();
    assertEquals(results.size(), record.numberOfRounds());
    Iterator<VotingRoundResult> expected = results.iterator();
    for (VotingRound round : record) {
      assertTrue(expected.hasNext());
      assertEquals(expected.next(), round.getResult());
    }
  }
}

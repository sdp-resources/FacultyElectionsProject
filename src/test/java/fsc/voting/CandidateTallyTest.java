package fsc.voting;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CandidateTallyTest {
  private VoteTarget cA = VoteTarget.from("A");
  private VoteTarget cB = VoteTarget.from("B");
  private VoteTarget cC = VoteTarget.from("C");
  private VoteTarget cD = VoteTarget.from("D");

  @Test
  public void candidateTalliesCanBeCompared() {
    assertTrue(new CandidateTally(cA, 2)
                     .isMorePreferredThan(new CandidateTally(cB, 1)));
    assertFalse(new CandidateTally(cA, 1)
                      .isMorePreferredThan(new CandidateTally(cB, 2)));
    assertFalse(new CandidateTally(cA, 1)
                      .isMorePreferredThan(new CandidateTally(cB, 1)));
    assertTrue(new CandidateTally(cA, 2, 1)
                     .isMorePreferredThan(new CandidateTally(cB, 2, 0, 1)));
    assertTrue(new CandidateTally(cA, 2, 1, 1)
                     .isMorePreferredThan(new CandidateTally(cB, 2, 1)));

    assertTrue(new CandidateTally(cA, 1)
                     .isEqualRankTo(new CandidateTally(cB, 1)));
    assertFalse(new CandidateTally(cA, 1)
                      .isEqualRankTo(new CandidateTally(cB, 2)));
  }

  @Test
  public void talliesPerformedCorrectly() {
    assertVotesResultInTallies(
          List.of(Vote.of(cA)),
          List.of(new CandidateTally(cA, 1)),
          VotingRoundResult.win(cA));
    assertVotesResultInTallies(
          List.of(Vote.of(cA, cB)),
          List.of(
                new CandidateTally(cA, 1),
                new CandidateTally(cB, 0, 1)),
          VotingRoundResult.win(cA));
    assertVotesResultInTallies(
          List.of(Vote.of(cA, cB, cC),
                  Vote.of(cA, cC, cB),
                  Vote.of(cB, cA, cC)),
          List.of(
                new CandidateTally(cA, 2, 1),
                new CandidateTally(cB, 1, 1, 1),
                new CandidateTally(cC, 0, 1, 2)),
          VotingRoundResult.win(cA));
    assertVotesResultInTallies(
          List.of(Vote.of(cA, cB, cC),
                  Vote.of(cC, cB),
                  Vote.of(cB, cA, cC)),
          List.of(
                new CandidateTally(cB, 1, 2),
                new CandidateTally(cA, 1, 1),
                new CandidateTally(cC, 1, 0, 2)),
          VotingRoundResult.eliminate(cC));
    assertVotesResultInTallies(
          List.of(Vote.of(cA, cB, cC),
                  Vote.of(cC, cB),
                  Vote.of(cB, cD)),
          List.of(
                new CandidateTally(cB, 1, 2),
                new CandidateTally(cC, 1, 0, 1),
                new CandidateTally(cA, 1),
                new CandidateTally(cD, 0, 1)),
          VotingRoundResult.eliminate(cD));
    assertVotesResultInTallies(
          List.of(Vote.of(cA, cB),
                  Vote.of(cB, cC),
                  Vote.of(cC, cD),
                  Vote.of(cD, cA, cB)),
          List.of(
                new CandidateTally(cB, 1, 1, 1),
                new CandidateTally(cA, 1, 1),
                new CandidateTally(cC, 1, 1),
                new CandidateTally(cD, 1, 1)),
          VotingRoundResult.tied(cA, cC, cD));
  }

  private void assertVotesResultInTallies(
        List<Vote> votes,
        List<CandidateTally> expectedTallies,
        VotingRoundResult expectedResult
  ) {
    VotingRound round = new VotingRound(votes);
    assertEquals(expectedTallies, round.getSortedCandidateTallies());
    assertEquals(expectedResult, round.getResult());
  }
}
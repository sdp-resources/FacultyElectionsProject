package fsc.voting;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CandidateTallyTest {
  @Test
  public void candidateTalliesCanBecompared() {
    assertTrue(new CandidateTally("A", 2)
                     .isMorePreferredThan(new CandidateTally("B", 1)));
    assertFalse(new CandidateTally("A", 1)
                      .isMorePreferredThan(new CandidateTally("B", 2)));
    assertFalse(new CandidateTally("A", 1)
                      .isMorePreferredThan(new CandidateTally("B", 1)));
    assertTrue(new CandidateTally("A", 2, 1)
                     .isMorePreferredThan(new CandidateTally("B", 2, 0, 1)));
    assertTrue(new CandidateTally("A", 2, 1, 1)
                     .isMorePreferredThan(new CandidateTally("B", 2, 1)));

    assertTrue(new CandidateTally("A", 1)
                     .isEqualRankTo(new CandidateTally("B", 1)));
    assertFalse(new CandidateTally("A", 1)
                      .isEqualRankTo(new CandidateTally("B", 2)));

  }

  @Test
  public void talliesPerformedCorrectly() {
    assertVotesResultInTallies(
          List.of(List.of("A")),
          List.of(new CandidateTally("A", 1)),
          VotingRoundResult.win("A"));
    assertVotesResultInTallies(
          List.of(List.of("A", "B")),
          List.of(
                new CandidateTally("A", 1),
                new CandidateTally("B", 0, 1)),
          VotingRoundResult.win("A"));
    assertVotesResultInTallies(
          List.of(List.of("A", "B", "C"),
                  List.of("A", "C", "B"),
                  List.of("B", "A", "C")),
          List.of(
                new CandidateTally("A", 2, 1),
                new CandidateTally("B", 1, 1, 1),
                new CandidateTally("C", 0, 1, 2)),
          VotingRoundResult.win("A"));
    assertVotesResultInTallies(
          List.of(List.of("A", "B", "C"),
                  List.of("C", "B"),
                  List.of("B", "A", "C")),
          List.of(
                new CandidateTally("B", 1, 2),
                new CandidateTally("A", 1, 1),
                new CandidateTally("C", 1, 0, 2)),
          VotingRoundResult.eliminate("C"));
    assertVotesResultInTallies(
          List.of(List.of("A", "B", "C"),
                  List.of("C", "B"),
                  List.of("B", "D")),
          List.of(
                new CandidateTally("B", 1, 2),
                new CandidateTally("C", 1, 0, 1),
                new CandidateTally("A", 1),
                new CandidateTally("D", 0, 1)),
          VotingRoundResult.eliminate("D"));
    assertVotesResultInTallies(
          List.of(List.of("A", "B"),
                  List.of("B", "C"),
                  List.of("C", "D"),
                  List.of("D", "A", "B")),
          List.of(
                new CandidateTally("B", 1, 1, 1),
                new CandidateTally("A", 1, 1),
                new CandidateTally("C", 1, 1),
                new CandidateTally("D", 1, 1)),
          VotingRoundResult.tied("A", "C", "D"));
  }

  private void assertVotesResultInTallies(
        List<List<String>> votes, List<CandidateTally> expectedTallies,
        VotingRoundResult expectedResult
  ) {
    VotingRound round = new VotingRound(votes);
    round.determineResults();
    assertEquals(expectedTallies, round.getSortedCandidateTallies());
    assertEquals(expectedResult, round.getResult());
  }

}
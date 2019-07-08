package fsc.voting;

import fsc.app.AppContext;
import fsc.entity.Profile;
import fsc.entity.Vote;
import fsc.mock.EntityStub;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CandidateTallyTest {
  private Profile cA = EntityStub.getProfile("A");
  private Profile cB = EntityStub.getProfile("B");
  private Profile cC = EntityStub.getProfile("C");
  private Profile cD = EntityStub.getProfile("D");

  @Test
  public void candidateTalliesCanBecompared() {
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
          List.of(AppContext.getEntityFactory().createVote(cA)),
          List.of(new CandidateTally(cA, 1)),
          VotingRoundResult.win(cA));
    assertVotesResultInTallies(
          List.of(AppContext.getEntityFactory().createVote(cA, cB)),
          List.of(
                new CandidateTally(cA, 1),
                new CandidateTally(cB, 0, 1)),
          VotingRoundResult.win(cA));
    assertVotesResultInTallies(
          List.of(AppContext.getEntityFactory().createVote(cA, cB, cC),
                  AppContext.getEntityFactory().createVote(cA, cC, cB),
                  AppContext.getEntityFactory().createVote(cB, cA, cC)),
          List.of(
                new CandidateTally(cA, 2, 1),
                new CandidateTally(cB, 1, 1, 1),
                new CandidateTally(cC, 0, 1, 2)),
          VotingRoundResult.win(cA));
    assertVotesResultInTallies(
          List.of(AppContext.getEntityFactory().createVote(cA, cB, cC),
                  AppContext.getEntityFactory().createVote(cC, cB),
                  AppContext.getEntityFactory().createVote(cB, cA, cC)),
          List.of(
                new CandidateTally(cB, 1, 2),
                new CandidateTally(cA, 1, 1),
                new CandidateTally(cC, 1, 0, 2)),
          VotingRoundResult.eliminate(cC));
    assertVotesResultInTallies(
          List.of(AppContext.getEntityFactory().createVote(cA, cB, cC),
                  AppContext.getEntityFactory().createVote(cC, cB),
                  AppContext.getEntityFactory().createVote(cB, cD)),
          List.of(
                new CandidateTally(cB, 1, 2),
                new CandidateTally(cC, 1, 0, 1),
                new CandidateTally(cA, 1),
                new CandidateTally(cD, 0, 1)),
          VotingRoundResult.eliminate(cD));
    assertVotesResultInTallies(
          List.of(AppContext.getEntityFactory().createVote(cA, cB),
                  AppContext.getEntityFactory().createVote(cB, cC),
                  AppContext.getEntityFactory().createVote(cC, cD),
                  AppContext.getEntityFactory().createVote(cD, cA, cB)),
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
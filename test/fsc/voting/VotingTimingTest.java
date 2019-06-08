package fsc.voting;

import fsc.entity.Profile;
import fsc.entity.Vote;
import fsc.mock.EntityStub;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.ChronoUnit.MILLIS;

public class VotingTimingTest {
  private static final int CANDIDATES = 50;
  private static final int VOTES = 300;

  @Test
  public void electionOnManyCandidatesWithManyVotesRunsInReasonableTime() {
    List<Profile> candidates = new ArrayList<>();
    for (int i = 0; i < CANDIDATES; i++) {
      candidates.add(EntityStub.getProfile(i));
    }
    List<Vote> votes = new ArrayList<>();
    for (int i = 0; i < VOTES; i++) {
      Collections.shuffle(candidates);
      votes.add(Vote.of(candidates.toArray(new Profile[]{})));
    }
    LocalDateTime now = LocalDateTime.now();
    ElectionRecord electionRecord = new ElectionRecord(votes);
    electionRecord.runElection();
    long elapsedTime = now.until(LocalDateTime.now(), MILLIS);
    System.out.println(electionRecord.numberOfRounds() + " rounds");
    System.out.println(elapsedTime + " milliseconds");
  }
}

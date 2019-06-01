package fsc.voting;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class VotingTest {
  ElectionRecord record = new ElectionRecord();

  @Ignore
  @Test
  public void candidateWithMajorityWinsRightAway() {
    List<List<String>> votes = List.of(List.of("A", "B", "C"),
                                       List.of("A", "C", "B"),
                                       List.of("B", "A", "C"));
    for (List<String> vote : votes) {
      record.add(vote);
    }
    record.runElection();
    assertEquals(1, record.numberOfRounds());
    assertEquals(new VotingRoundResult.WinVotingRoundResult("A"), record.getRound(1).getResult());
  }
}

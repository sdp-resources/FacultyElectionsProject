package fsc.voting;

import java.util.ArrayList;
import java.util.List;

public class FullElectionRecord {
  private List<Vote> votes;
  private List<ElectionRecord> electionRecords;

  public FullElectionRecord(List<Vote> votes) {
    this.votes = votes;
    electionRecords = new ArrayList<>();
  }

  public List<ElectionRecord> getElectionRecords() {
    return electionRecords;
  }

  public void runElection() {
    List<Vote> localVotes = Vote.snapshot(votes);
    while(localVotes.size() > 0) {
      ElectionRecord electionRecord = new ElectionRecord(Vote.snapshot(localVotes));
      electionRecord.runElection();
      electionRecords.add(electionRecord);
      VoteTarget lastRoundWinner = electionRecord.getWinner();
      localVotes.forEach(vote -> vote.remove(lastRoundWinner));
      localVotes = Vote.snapshot(localVotes);
    }
  }
}

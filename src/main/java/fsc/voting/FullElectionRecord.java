package fsc.voting;

import java.util.ArrayList;
import java.util.Collection;
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
    continueElectionWithVotes(Vote.snapshot(votes));
  }

  private void continueElectionWithVotes(List<Vote> localVotes) {
    if (localVotes.size() == 0) return;
    for (VoteTarget winner : addNewRecordAndGetWinners(localVotes)) {
      localVotes.forEach(vote -> vote.remove(winner));
    }
    continueElectionWithVotes(Vote.snapshot(localVotes));
  }

  private Collection<VoteTarget> addNewRecordAndGetWinners(List<Vote> localVotes) {
    ElectionRecord electionRecord = new ElectionRecord(localVotes);
    electionRecord.runElection();
    electionRecords.add(electionRecord);
    return electionRecord.getFinalWinners();
  }

}

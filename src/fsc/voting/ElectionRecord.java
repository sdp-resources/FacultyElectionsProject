package fsc.voting;

import fsc.entity.Profile;
import fsc.entity.Vote;
import fsc.voting.VotingRoundResult.WinVotingRoundResult;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

class ElectionRecord extends AbstractList<VotingRound> {
  private List<VotingRound> rounds = new ArrayList<>();
  private List<Vote> votes;

  public ElectionRecord(List<Vote> votes) {
    this.votes = Vote.createVoteSnapshot(votes);
  }

  public void runElection() {
    computeNextRound();
    while (!lastRoundHadWinner()) {
      eliminateACandidate();
      computeNextRound();
    }
  }

  private void eliminateACandidate() {
    Profile candidate = getLastResult().getCandidate();
    for (Vote vote : votes) {
      vote.remove(candidate);
    }
  }

  private boolean lastRoundHadWinner() {
    return getLastResult() instanceof WinVotingRoundResult;

  }

  private VotingRoundResult getLastResult() {
    VotingRound votingRound = lastRound();
    return votingRound.getResult();
  }

  private VotingRound lastRound() {
    return rounds.get(rounds.size() - 1);
  }

  private void computeNextRound() {
    rounds.add(new VotingRound(votes));
  }

  public int numberOfRounds() {
    return rounds.size();
  }

  public VotingRound getRound(int i) {
    return rounds.get(i - 1);
  }

  public VotingRound get(int index) {
    return getRound(index + 1);
  }

  public int size() {
    return 0;
  }
}

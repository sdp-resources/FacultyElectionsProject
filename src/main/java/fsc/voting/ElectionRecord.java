package fsc.voting;

import fsc.voting.VotingRoundResult.WinVotingRoundResult;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class ElectionRecord extends AbstractList<VotingRound> {
  private List<VotingRound> rounds = new ArrayList<>();
  private List<Vote> votes;

  public ElectionRecord(List<Vote> votes) {
    this.votes = votes;
  }

  public void runElection() {
    if (votes.size() == 0) { return; }
    computeNextRound();
    while (!lastRoundHadWinner()) {
      eliminateACandidate();
      computeNextRound();
    }
  }

  public VoteTarget getWinner() {
    VotingRoundResult lastResult = getLastResult();
    if (lastResult instanceof WinVotingRoundResult) { return lastResult.getCandidate(); }
    throw new RuntimeException("Last round should have had a winner, but did not!");
  }

  private void eliminateACandidate() {
    VoteTarget candidate = getLastResult().getCandidate();
    votes.forEach(vote -> vote.remove(candidate));
  }

  private boolean lastRoundHadWinner() {
    return getLastResult() instanceof WinVotingRoundResult;
  }

  private VotingRoundResult getLastResult() {
    return lastRound().getResult();
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
    return rounds.size();
  }
}

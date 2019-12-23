package fsc.voting;

import java.util.*;

public class ElectionRecord extends AbstractList<VotingRound> {
  private List<VotingRound> rounds = new ArrayList<>();
  private List<Vote> votes;

  public ElectionRecord(List<Vote> votes) {
    this.votes = Vote.snapshot(votes);
  }

  public void runElection() {
    if (votes.size() == 0) { return; }
    computeNextRound();
    while (!lastRoundHadWinner()) {
      eliminateACandidate();
      computeNextRound();
    }
  }

  public Collection<VoteTarget> getFinalWinners() {
    Collection<VoteTarget> winners = getLastResult().getWinners();
    if (winners.size() == 0) {
      throw new RuntimeException("Last round should have had a winner, but did not!");
    }
    return winners;
  }

  private void eliminateACandidate() {
    for (VoteTarget candidate : getLastResult().getCandidatesToEliminate()) {
      votes.forEach(vote -> vote.remove(candidate));
    }
  }

  private boolean lastRoundHadWinner() {
    return getLastResult().getWinners().size() > 0;
  }

  public VotingRoundResult getLastResult() {
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

package fsc.voting;

import fsc.entity.Vote;

import java.util.ArrayList;
import java.util.List;

class ElectionRecord {
  private List<VotingRound> rounds = new ArrayList<>();
  private List<Vote> votes = new ArrayList<>();

  public void runElection() {
    computeNextRound();
    while (!lastRoundHadWinner()) {
      computeNextRound();
    }
  }

  private boolean lastRoundHadWinner() {
    return false;
  }

  private void computeNextRound() {
    VotingRound round = new VotingRound(votes);
    rounds.add(round);
  }

  public void add(Vote vote) {
    votes.add(vote);
  }

  public int numberOfRounds() {
    return 0;
  }

  public VotingRound getRound(int i) {
    return null;
  }
}

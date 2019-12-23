package fsc.voting;

import java.util.List;

class VotingRound {
  private final List<Vote> votes;
  private List<CandidateTally> rankedTallies;
  private VotingRoundResult result;

  public VotingRound(List<Vote> votes) {
    this.votes = Vote.snapshot(votes);
    rankedTallies = new VoteTallier().tallyVotes(this.votes);
    result = determineResultForRound();
  }

  private VotingRoundResult determineResultForRound() {
    return topCandidateHasMajority() ? topCandidateWins()
                                     : resultFromTiedForLast(tiedForLast());
  }

  private boolean topCandidateHasMajority() {
    return firstCandidate().hasTopRanksAtLeast(1 + votes.size() / 2);
  }

  private VotingRoundResult topCandidateWins() {
    return VotingRoundResult.win(firstCandidate().target);
  }

  private VotingRoundResult resultFromTiedForLast(VoteTarget[] candidates) {
    if (candidates.length == 1) {
      return lastCandidateEliminated();
    } else if (candidates.length == rankedTallies.size()) {
      return VotingRoundResult.tiedForFirst(candidates);
    } else {
      return VotingRoundResult.tied(candidates);

    }
  }

  private VotingRoundResult lastCandidateEliminated() {
    return VotingRoundResult.eliminate(lastCandidate().target);
  }

  private CandidateTally firstCandidate() {
    return rankedTallies.get(0);
  }

  private VoteTarget[] tiedForLast() {
    return rankedTallies.stream().filter(lastCandidate()::equals)
                        .map(candidateTally -> candidateTally.target)
                        .toArray(VoteTarget[]::new);
  }

  private CandidateTally lastCandidate() {
    return rankedTallies.get(rankedTallies.size() - 1);
  }

  public VotingRoundResult getResult() {
    return result;
  }

  public List<CandidateTally> getSortedCandidateTallies() {
    return rankedTallies;
  }

}

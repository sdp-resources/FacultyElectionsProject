package fsc.voting;

import java.util.List;

class VotingRound {
  public final List<List<String>> votes;
  private List<CandidateTally> rankedTallies;
  private VotingRoundResult result = null;

  public VotingRound(List<List<String>> votes) {
    this.votes = cloneVotes(votes);
  }

  public void determineResults() {
    rankedTallies = new VoteTallier().tallyVotes(votes);
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
    return VotingRoundResult.win(firstCandidate().getName());
  }

  private VotingRoundResult resultFromTiedForLast(String[] candidates) {
    return candidates.length == 1 ? lastCandidateEliminated() : VotingRoundResult.tied(candidates);
  }

  private VotingRoundResult lastCandidateEliminated() {
    return VotingRoundResult.eliminate(lastCandidate().getName());
  }

  private CandidateTally firstCandidate() {
    return rankedTallies.get(0);
  }

  private String[] tiedForLast() {
    return rankedTallies.stream().filter(lastCandidate()::equals)
                        .map(CandidateTally::getName)
                        .toArray(String[]::new);
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

  private List<List<String>> cloneVotes(List<List<String>> votes) {
    // TODO
    return votes;
  }

}

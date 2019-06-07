package fsc.voting;

import fsc.entity.Profile;
import fsc.entity.Vote;

import java.util.List;
import java.util.stream.Collectors;

class VotingRound {
  private final List<Vote> votes;
  private List<CandidateTally> rankedTallies;
  private VotingRoundResult result = null;

  public VotingRound(List<Vote> votes) {
    this.votes = createVoteSnapshot(votes);
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
    return VotingRoundResult.win(firstCandidate().getProfile());
  }

  private VotingRoundResult resultFromTiedForLast(Profile[] candidates) {
    return candidates.length == 1 ? lastCandidateEliminated() : VotingRoundResult.tied(candidates);
  }

  private VotingRoundResult lastCandidateEliminated() {
    return VotingRoundResult.eliminate(lastCandidate().getProfile());
  }

  private CandidateTally firstCandidate() {
    return rankedTallies.get(0);
  }

  private Profile[] tiedForLast() {
    return rankedTallies.stream().filter(lastCandidate()::equals)
                        .map(CandidateTally::getProfile)
                        .toArray(Profile[]::new);
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

  private List<Vote> createVoteSnapshot(List<Vote> votes) {
    return votes.stream().map(Vote::clone).collect(Collectors.toList());
  }

}

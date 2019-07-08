package fsc.voting;

import fsc.app.AppContext;
import fsc.entity.Profile;
import fsc.entity.Vote;

import java.util.List;

class VotingRound {
  private final List<Vote> votes;
  private List<CandidateTally> rankedTallies;
  private VotingRoundResult result;

  public VotingRound(List<Vote> votes) {
    this.votes = AppContext.getEntityFactory().createVoteSnapshot(votes);
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
    return VotingRoundResult.win(firstCandidate().profile);
  }

  private VotingRoundResult resultFromTiedForLast(Profile[] candidates) {
    return candidates.length == 1 ? lastCandidateEliminated() : VotingRoundResult.tied(candidates);
  }

  private VotingRoundResult lastCandidateEliminated() {
    return VotingRoundResult.eliminate(lastCandidate().profile);
  }

  private CandidateTally firstCandidate() {
    return rankedTallies.get(0);
  }

  private Profile[] tiedForLast() {
    return rankedTallies.stream().filter(lastCandidate()::equals)
                        .map(candidateTally -> candidateTally.profile)
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

}

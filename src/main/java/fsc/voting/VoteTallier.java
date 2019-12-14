package fsc.voting;

import java.util.*;

class VoteTallier {
  private Map<VoteTarget, CandidateTally> tallies = new HashMap<>();

  List<CandidateTally> tallyVotes(List<Vote> votes) {
    votes.forEach(this::countVote);
    return computeTallies(tallies);
  }

  private void countVote(Vote vote) {
    for (int i = 0; i < vote.size(); i++) {
      getTally(vote.get(i)).addVote(i);
    }
  }

  private CandidateTally getTally(VoteTarget candidate) {
    if (!tallies.containsKey(candidate)) {
      tallies.put(candidate, new CandidateTally(candidate));
    }
    return tallies.get(candidate);
  }

  private List<CandidateTally> computeTallies(Map<VoteTarget, CandidateTally> targetTallies) {
    List<CandidateTally> tallyList = new ArrayList<>(targetTallies.values());
    tallyList.sort(CandidateTally.rankComparator);
    return tallyList;
  }
}
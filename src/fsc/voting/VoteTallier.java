package fsc.voting;

import java.util.*;

class VoteTallier {
  private Map<String, CandidateTally> tallies = new HashMap<>();

  List<CandidateTally> tallyVotes(List<List<String>> votes) {
    votes.forEach(this::countVote);
    return computeTallies(tallies);
  }

  private void countVote(List<String> vote) {
    for (int i = 0; i < vote.size(); i++) {
      String candidate = vote.get(i);
      getTally(candidate).addVote(i);
    }
  }

  private CandidateTally getTally(String candidate) {
    if (!tallies.containsKey(candidate)) {
      tallies.put(candidate, new CandidateTally(candidate));
    }
    return tallies.get(candidate);
  }

  private List<CandidateTally> computeTallies(Map<String, CandidateTally> tallies) {
    List<CandidateTally> tallyList = new ArrayList<>(tallies.values());
    tallyList.sort(CandidateTally.rankComparator);
    return tallyList;
  }
}
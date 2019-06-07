package fsc.voting;

import fsc.entity.Profile;
import fsc.entity.Vote;

import java.util.*;

class VoteTallier {
  private Map<Profile, CandidateTally> tallies = new HashMap<>();

  List<CandidateTally> tallyVotes(List<Vote> votes) {
    votes.forEach(this::countVote);
    return computeTallies(tallies);
  }

  private void countVote(Vote vote) {
    for (int i = 0; i < vote.size(); i++) {
      getTally(vote.get(i)).addVote(i);
    }
  }

  private CandidateTally getTally(Profile candidate) {
    if (!tallies.containsKey(candidate)) {
      tallies.put(candidate, new CandidateTally(candidate));
    }
    return tallies.get(candidate);
  }

  private List<CandidateTally> computeTallies(Map<Profile, CandidateTally> profileTallies) {
    List<CandidateTally> tallyList = new ArrayList<>(profileTallies.values());
    tallyList.sort(CandidateTally.rankComparator);
    return tallyList;
  }
}
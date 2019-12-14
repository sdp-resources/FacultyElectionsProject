package fsc.voting;

import java.util.*;

public class CandidateTally implements Comparable<CandidateTally> {
  public static final Comparator<CandidateTally> rankComparator = new ReverseRankComparator();

  public final VoteTarget target;
  private final List<Integer> rankCounts;

  public CandidateTally(VoteTarget target, Integer... rankCounts) {
    this.target = target;
    this.rankCounts = new ArrayList<>(Arrays.asList(rankCounts));
  }

  public void addVote(int rank) {
    for (int i = rankCounts.size(); i <= rank; i++) {
      rankCounts.add(0);
    }
    rankCounts.set(rank, rankCounts.get(rank) + 1);
  }

  public boolean isMorePreferredThan(CandidateTally other) {
    return compareTo(other) < 0;
  }

  public boolean isEqualRankTo(CandidateTally other) {
    return compareTo(other) == 0;
  }

  public boolean hasTopRanksAtLeast(int votes) {
    return rankCounts.get(0) >= votes;
  }

  public int compareTo(CandidateTally o) {
    return rankComparator.compare(this, o);
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CandidateTally that = (CandidateTally) o;
    return Objects.equals(rankCounts, that.rankCounts);
  }

  public int hashCode() {
    return Objects.hash(rankCounts);
  }

  public String toString() {
    return "CandidateTally{" + rankCounts + '}';
  }

  private static class ReverseRankComparator implements Comparator<CandidateTally> {
    public int compare(CandidateTally o1, CandidateTally o2) {
      return compareFromRank(o1, o2, 0);
    }

    private int compareFromRank(CandidateTally o1, CandidateTally o2, int rank) {
      return oneTallyWasExhausted(o1, o2, rank)
             ? tallySizeDifference(o1, o2)
             : differenceAtCurrentRankOrMoveOn(o1, o2, rank,
                                               differenceAtRank(o1, o2, rank));
    }

    private int differenceAtCurrentRankOrMoveOn(
          CandidateTally o1, CandidateTally o2, int rank, int difference
    ) {
      return difference != 0 ? difference
                             : compareFromRank(o1, o2, rank + 1);
    }

    private boolean oneTallyWasExhausted(CandidateTally o1, CandidateTally o2, int rank) {
      return rank >= o1.rankCounts.size() || rank >= o2.rankCounts.size();
    }

    private int differenceAtRank(CandidateTally o1, CandidateTally o2, int rank) {
      return o2.rankCounts.get(rank) - o1.rankCounts.get(rank);
    }

    private int tallySizeDifference(CandidateTally o1, CandidateTally o2) {
      return o2.rankCounts.size() - o1.rankCounts.size();
    }
  }

}

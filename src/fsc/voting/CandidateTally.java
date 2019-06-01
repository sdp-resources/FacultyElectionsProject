package fsc.voting;

import java.util.*;

public class CandidateTally implements Comparable<CandidateTally> {
  public static final Comparator<CandidateTally> alphaComparator =
        Comparator.comparing(o -> o.name);
  public static final Comparator<CandidateTally> rankComparator = new ReverseRankComparator();

  private final String name;
  private final List<Integer> rankCounts;

  public CandidateTally(String name, Integer... rankCounts) {
    this.name = name;
    this.rankCounts = new ArrayList<>(Arrays.asList(rankCounts));
  }

  public void addVote(int rank) {
    for (int i = rankCounts.size(); i <= rank; i++) {
      rankCounts.add(0);
    }
    rankCounts.set(rank, rankCounts.get(rank) + 1);
  }

  public String getName() {
    return name;
  }

  public int compareTo(CandidateTally o) {
    int i = 0;
    while (true) {
      if (i >= rankCounts.size() || i >= o.rankCounts.size()) {
        return o.rankCounts.size() - rankCounts.size();
      }
      int countDifference = o.rankCounts.get(i) - rankCounts.get(i);
      if (countDifference != 0) { return countDifference; }
      i++;
    }
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

  public boolean isMorePreferredThan(CandidateTally other) {
    return compareTo(other) < 0;
  }

  public boolean isEqualRankTo(CandidateTally other) {
    return compareTo(other) == 0;
  }

  public boolean hasTopRanksAtLeast(int votes) {
    return rankCounts.get(0) >= votes;
  }

  private static class ReverseRankComparator implements Comparator<CandidateTally> {
    public int compare(CandidateTally o1, CandidateTally o2) {
      return o1.compareTo(o2);
    }
  }

}

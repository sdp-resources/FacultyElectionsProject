package fsc.voting;

import java.util.*;

interface VotingRoundResult {
  static VotingRoundResult win(String candidate) {
    return new WinVotingRoundResult(candidate);
  }
  static VotingRoundResult eliminate(String candidate) {
    return new EliminationVotingRoundResult(candidate);
  }
  static VotingRoundResult tied(String... candidates) {
    return new DrawVotingResult(candidates);
  }

  class EliminationVotingRoundResult implements VotingRoundResult {
  public final String candidate;

  public EliminationVotingRoundResult(String candidate) {
    this.candidate = candidate;
  }

    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      EliminationVotingRoundResult that = (EliminationVotingRoundResult) o;
      return Objects.equals(candidate, that.candidate);
    }

    public int hashCode() {
      return Objects.hash(candidate);
    }

    public String toString() {
      return "Eliminated{" + candidate + '}';
    }
  }

  class WinVotingRoundResult implements VotingRoundResult {
    public final String candidate;

    public WinVotingRoundResult(String candidate) {this.candidate = candidate;}

    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      WinVotingRoundResult winResult = (WinVotingRoundResult) o;
      return Objects.equals(candidate, winResult.candidate);
    }

    public int hashCode() {
      return Objects.hash(candidate);
    }

    public String toString() {
      return "Winner{" + candidate + '}';
    }
  }

  class DrawVotingResult implements VotingRoundResult {
    public final Set<String> candidates;

    public DrawVotingResult(String[] candidates) {
      this.candidates = new HashSet<>(Arrays.asList(candidates));
    }

    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      DrawVotingResult that = (DrawVotingResult) o;
      return Objects.equals(candidates, that.candidates);
    }

    public int hashCode() {
      return Objects.hash(candidates);
    }

    public String toString() {
      return "Draw{" + candidates + '}';
    }
  }
}

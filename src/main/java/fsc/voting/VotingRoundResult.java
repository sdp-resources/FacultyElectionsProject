package fsc.voting;

import java.util.*;

public interface VotingRoundResult {
  static VotingRoundResult win(VoteTarget cA) {
    return new WinVotingRoundResult(cA);
  }
  static VotingRoundResult eliminate(VoteTarget candidate) {
    return new EliminationVotingRoundResult(candidate);
  }
  static VotingRoundResult tied(VoteTarget... candidates) {
    return new DrawVotingResult(candidates);
  }
  VoteTarget getCandidate();

  class EliminationVotingRoundResult implements VotingRoundResult {
    public final VoteTarget candidate;

    public EliminationVotingRoundResult(VoteTarget candidate) {
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

    public VoteTarget getCandidate() {
      return candidate;
    }
  }

  class WinVotingRoundResult implements VotingRoundResult {
    public final VoteTarget candidate;

    public WinVotingRoundResult(VoteTarget candidate) { this.candidate = candidate; }

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

    public VoteTarget getCandidate() {
      return candidate;
    }
  }

  class DrawVotingResult implements VotingRoundResult {
    public final Set<VoteTarget> candidates;

    public DrawVotingResult(VoteTarget[] candidates) {
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

    public VoteTarget getCandidate() {
      Iterator<VoteTarget> iterator = candidates.iterator();
      if (iterator.hasNext()) { return iterator.next(); }
      throw new RuntimeException("Should not have NO candidates tied to last");
    }
  }
}

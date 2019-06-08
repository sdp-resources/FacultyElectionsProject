package fsc.voting;

import fsc.entity.Profile;

import java.util.*;

interface VotingRoundResult {
  static VotingRoundResult win(Profile cA) {
    return new WinVotingRoundResult(cA);
  }
  static VotingRoundResult eliminate(Profile candidate) {
    return new EliminationVotingRoundResult(candidate);
  }
  static VotingRoundResult tied(Profile... candidates) {
    return new DrawVotingResult(candidates);
  }
  Profile getCandidate();

  class EliminationVotingRoundResult implements VotingRoundResult {
    public final Profile candidate;

    public EliminationVotingRoundResult(Profile candidate) {
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

    public Profile getCandidate() {
      return candidate;
    }
  }

  class WinVotingRoundResult implements VotingRoundResult {
    public final Profile candidate;

    public WinVotingRoundResult(Profile candidate) { this.candidate = candidate; }

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

    public Profile getCandidate() {
      return candidate;
    }
  }

  class DrawVotingResult implements VotingRoundResult {
    public final Set<Profile> candidates;

    public DrawVotingResult(Profile[] candidates) {
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

    public Profile getCandidate() {
      Iterator<Profile> iterator = candidates.iterator();
      if (iterator.hasNext()) { return iterator.next(); }
      throw new RuntimeException("Should not have NO candidates tied to last");
    }
  }
}

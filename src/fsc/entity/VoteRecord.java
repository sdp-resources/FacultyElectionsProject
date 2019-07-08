package fsc.entity;

import java.time.LocalDateTime;
import java.util.List;

public class VoteRecord {

  private final List<Profile> votes;
  private final LocalDateTime date;
  private Voter voter;

  VoteRecord(Voter voter, List<Profile> votes) {
    this(voter, LocalDateTime.now(), votes);
  }

  public VoteRecord(Voter voter, LocalDateTime date, List<Profile> votes) {
    this.voter = voter;
    this.votes = votes;
    this.date = date;
  }

  public Profile getVoter() {
    return voter.getVoter();
  }

  public List<Profile> getVotes() {
    return votes;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public Election getElection() {
    return voter.getElection();
  }

  public boolean isRecordFor(Voter aVoter) {
    return voter.equals(aVoter);
  }

  public boolean someProfilesAreNotCandidates() {
    for (Profile vote : getVotes()) {
      if (!getElection().hasCandidate(vote)) { return true; }
    }
    return false;
  }

  public String toString() {
    return "VoteRecord{" + voter.getVoter().getUsername() + ", " + votes + '}';
  }
}
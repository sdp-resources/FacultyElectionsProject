package fsc.entity;

import java.util.Objects;

public class Voter {
  private long voterId;
  private Profile profile;
  private Election election;
  private boolean voted;

  public Voter() {}

  public Voter(Profile profile, Election election) {
    this.profile = profile;
    setElection(election);
  }

  public long getVoterId() {
    return voterId;
  }

  public void setVoterId(long voterId) {
    this.voterId = voterId;
  }

  public void setElection(Election election) {
    this.election = election;
  }

  public boolean equals(Voter other) {
    return profile.equals(other.profile) && election.equals(other.election);
  }

  public Profile getProfile() {
    return profile;
  }

  public Election getElection() {
    return election;
  }

  public boolean hasVoted() {
    return voted;
  }

  public boolean canVote() {
    return election.getState().isVote();
  }

  public void setVoted(boolean voted) {
    this.voted = voted;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Voter voter = (Voter) o;
    return voterId == voter.voterId &&
                 voted == voter.voted &&
                 profile.equals(voter.profile) &&
                 election.equals(voter.election);
  }

  public int hashCode() {
    return Objects.hash(voterId, profile, election, voted);
  }

  public String toString() {
    return "Voter{" + profile.getUsername() + ", " + election.getID() + '}';
  }
}

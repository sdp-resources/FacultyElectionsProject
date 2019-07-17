package fsc.entity;

public class Voter {
  private long voterId;
  private Profile profile;
  private Election election;
  private boolean voted;

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

  public String toString() {
    return "Voter{" + profile.getUsername() + ", " + election.getID() + '}';
  }

  public boolean hasVoted() {
    return voted;
  }

  public void setVoted(boolean voted) {
    this.voted = voted;
  }
}

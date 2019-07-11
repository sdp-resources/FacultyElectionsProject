package fsc.entity;

public class Candidate {



  public enum Status {NoAnswer, Declined, Accepted;}
  private Profile profile;

  private Ballot ballot;
  private Status status;

  Candidate(Profile profile, Ballot ballot) {
    this.profile = profile;
    this.ballot = ballot;
    status = Status.NoAnswer;
  }

  boolean matchesUsername(String username) {
    return profile.getUsername().equals(username);
  }

  public boolean matchesProfile(Profile profile) {
    return this.profile.equals(profile);
  }

  public boolean hasNotDeclined() {
    return !status.equals(Status.Declined);
  }

  public Profile getProfile() {
    return profile;
  }

  public Ballot getBallot() { return ballot; }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

}

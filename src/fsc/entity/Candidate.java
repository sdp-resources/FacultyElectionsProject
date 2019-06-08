package fsc.entity;

public class Candidate {

  public enum Status {NoAnswer, Declined, Accepted}

  private Profile profile;
  private Status status;

  public Candidate(Profile profile) {
    this.profile = profile;
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

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

}

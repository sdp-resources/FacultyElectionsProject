package fsc.entity;

public class Candidate {
  public enum Status {NoAnswer, Declined, Accepted;}

  private Profile profile;
  private Status status;

  public Candidate(Profile profile) {
    this.profile = profile;
    status = Status.NoAnswer;
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

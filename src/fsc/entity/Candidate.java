package fsc.entity;

public class Candidate {
  Profile profile;

  public enum Status {NoAnswer, Declined, Accepted}

  Status status;

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

  public void setDeclinded() {
    status = Status.Declined;
  }

  public void setAccepted() {
    status = Status.Accepted;
  }
}

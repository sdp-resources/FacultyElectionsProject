package fsc.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Candidate {
  public Candidate() {}

  public CandidateId getCandidateId() {
    return candidateId;
  }

  public void setCandidateId(CandidateId candidateId) {
    this.candidateId = candidateId;
  }

  public enum Status {
    NoAnswer, Declined, Willing;

    public String getString() { return this.toString(); }
  }

  private CandidateId candidateId;

  private Profile profile;

  private Election election;
  private Status status;

  public Candidate(Profile profile, Election election) {
    setCandidateId(new CandidateId(profile.getUsername(), election.getID()));
    this.profile = profile;
    setElection(election);

    status = Status.NoAnswer;
  }

  boolean matchesUsername(String username) {
    return profile.getUsername().equals(username);
  }

  public boolean matchesProfile(Profile profile) {
    return this.profile.equals(profile);
  }

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public Election getElection() {
    return election;
  }

  public void setElection(Election election) {
    this.election = election;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public void setStatusFromString(String status) {
    this.status = Status.valueOf(status);
  }

  // TODO: Should be happening from Status internally??
  public boolean hasAccepted() {
    return status.equals(Status.Willing);
  }

  public boolean hasDeclined() {
    return status.equals(Status.Declined);
  }

  public boolean hasNotDeclined() {
    return !status.equals(Status.Declined);
  }

  public boolean hasNotResponded() {
    return status.equals(Status.NoAnswer);
  }

  public void setStatusAccepted() {
    setStatus(Status.Willing);
  }

  public void setStatusDeclined() {
    setStatus(Status.Declined);
  }

  public static List<Profile> toProfiles(Collection<Candidate> candidates) {
    return candidates.stream().filter(Candidate::hasNotDeclined)
                     .map(Candidate::getProfile).collect(Collectors.toList());
  }

  public String toString() {
    return "Candidate{" +
                 "candidateId=" + candidateId +
                 ", profile=" + profile.getUsername() +
                 ", election=" + election.getID() +
                 ", status=" + status +
                 '}';
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Candidate candidate = (Candidate) o;
    return candidateId.equals(candidate.candidateId) &&
                 profile.equals(candidate.profile) &&
                 election.equals(candidate.election) &&
                 status == candidate.status;
  }

  public int hashCode() {
    return Objects.hash(candidateId, profile, election, status);
  }
  private static class CandidateId implements Serializable {


    private String username;
    private Long electionId;

    public CandidateId() {}

    CandidateId(String username, Long electionId) {
      this.username = username;
      this.electionId = electionId;
    }

    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CandidateId that = (CandidateId) o;
      return username.equals(that.username) &&
                   electionId.equals(that.electionId);
    }

    public int hashCode() {
      return Objects.hash(username, electionId);
    }

    public String toString() {
      return "CandidateId{" +
                   "username='" + username + '\'' +
                   ", electionId=" + electionId +
                   '}';
    }
  }
}

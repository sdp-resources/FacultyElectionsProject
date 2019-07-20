package fsc.entity;

import java.io.Serializable;
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

  public enum Status {NoAnswer, Declined, Accepted;}

  private CandidateId candidateId;

  private Profile profile;

  private Election election;

  private Status status;
  Candidate(Profile profile, Election election) {
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

  public boolean hasNotDeclined() {
    return !status.equals(Status.Declined);
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

  public static List<Profile> toProfiles(List<Candidate> candidates) {
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

package fsc.entity;

import java.util.List;
import java.util.stream.Collectors;

public class Candidate {
  public static List<Profile> toProfiles(List<Candidate> candidates) {
    return candidates.stream().filter(Candidate::hasNotDeclined)
                     .map(Candidate::getProfile).collect(Collectors.toList());
  }

  public enum Status {NoAnswer, Declined, Accepted;}

  private Profile profile;
  private Election election;
  private Status status;

  Candidate(Profile profile, Election election) {
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

}

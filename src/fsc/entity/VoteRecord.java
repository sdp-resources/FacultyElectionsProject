package fsc.entity;

import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VoteRecord {

  @Transient   // TODO: Remove
  private List<String> votes;
  private LocalDateTime date;
  private Election election;
  private Long recordId;

  public VoteRecord() {}

  VoteRecord(Election election, List<Profile> votes) {
    this(election, LocalDateTime.now(), votes);
  }

  public VoteRecord(Election election, LocalDateTime date, List<Profile> voteProfiles) {
    this.votes = convertToUsernames(voteProfiles);
    this.date = date;
    setElection(election);
  }

  public Long getRecordId() {
    return recordId;
  }

  public void setRecordId(Long recordId) {
    this.recordId = recordId;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public Election getElection() {
    return election;
  }

  public boolean someProfilesAreNotCandidates() {
    for (String username: votes) {
      if (!getElection().hasCandidate(username)) { return true; }
    }
    return false;
  }

  public void setElection(Election election) {
    this.election = election;
  }

  public List<String> getVotes() {
    return votes;
  }

  public void setVotes(List<String> votes) {
    this.votes = votes;
  }

  private List<String> convertToUsernames(List<Profile> votes) {
    return votes.stream().map(Profile::getUsername).collect(Collectors.toList());
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    VoteRecord that = (VoteRecord) o;
    return recordId.equals(that.recordId) &&
                 Arrays.equals(votes.toArray(), that.votes.toArray()) &&
                 date.equals(that.date) &&
                 election.equals(that.election);
  }

  public int hashCode() {
    return Objects.hash(votes, date, election, recordId);
  }

  public String toString() {
    return "VoteRecord{" + recordId +
                 " election " + election.getID() +
                 ", " + votes + '}';
  }
}
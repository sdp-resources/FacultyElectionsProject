package fsc.entity;

import java.time.LocalDateTime;
import java.util.List;

public class VoteRecord {

  private List<Profile> votes;
  private LocalDateTime date;
  private Election election;
  private Long recordId;

  public VoteRecord() {}

  VoteRecord(Election election, List<Profile> votes) {
    this(election, LocalDateTime.now(), votes);
  }

  public VoteRecord(Election election, LocalDateTime date, List<Profile> votes) {
    this.votes = votes;
    this.date = date;
    setElection(election);
  }

  public Long getRecordId() {
    return recordId;
  }

  public void setRecordId(Long recordId) {
    this.recordId = recordId;
  }

  public List<Profile> getVotes() {
    return votes;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public Election getElection() {
    return election;
  }

  public boolean someProfilesAreNotCandidates() {
    for (Profile vote : getVotes()) {
      if (!getElection().hasCandidate(vote)) { return true; }
    }
    return false;
  }

  public String toString() {
    return "VoteRecord{" + recordId +
                 " election " + election.getID() +
                 ", " + votes + '}';
  }

  public void setElection(Election election) {
    this.election = election;
  }
}
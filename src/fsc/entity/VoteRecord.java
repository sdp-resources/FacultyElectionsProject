package fsc.entity;

import java.time.LocalDateTime;
import java.util.List;

public class VoteRecord {

  private final Profile voter;
  private final List<Profile> votes;
  private final Election election;
  private final LocalDateTime date;

  public VoteRecord(Profile voter, List<Profile> votes, Election election) {
    this(voter, LocalDateTime.now(), votes, election);
  }

  public VoteRecord(Profile voter, LocalDateTime date, List<Profile> votes, Election election) {
    this.voter = voter;
    this.votes = votes;
    this.date = date;
    this.election = election;
  }

  public Profile getVoter() {
    return voter;
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

  public boolean isRecordFor(Profile voter, Election election) {
    return this.voter.equals(voter) && this.election.equals(election);
  }
}
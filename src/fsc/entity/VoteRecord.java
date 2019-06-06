package fsc.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VoteRecord {

  private final Profile voter;
  private final List<Profile> votes;
  private final Election election;
  private final Date date;

  public VoteRecord(Profile voter, List<Profile> votes, Election election) {
    this(voter, Calendar.getInstance().getTime(), votes, election);
  }

  public VoteRecord(Profile voter, Date date, List<Profile> votes, Election election) {
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

  public Date getDate() {
    return date;
  }

  public Election getElection() {
    return election;
  }

}
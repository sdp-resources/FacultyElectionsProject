package fsc.entity;

import java.util.Calendar;
import java.util.Date;

public class VoteRecord {

  private final Profile profile;
  private final Vote vote;
  private final int electionID;
  private Date date;

  public VoteRecord(Profile profile, Vote vote, int electionID) {
    this(profile, Calendar.getInstance().getTime(), vote, electionID);
  }

  public VoteRecord(Profile profile, Date date, Vote vote, int electionID){
    this.profile = profile;
    this.vote = vote;
    this.date = date;
    this.electionID = electionID;
  }

  public Profile getProfile() {
    return profile;
  }

  public Vote getVote() {
    return vote;
  }

  public Date getDate() {
    return date;
  }

  public int getElectionID() {
    return electionID;
  }
}
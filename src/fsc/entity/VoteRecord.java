package fsc.entity;

import java.util.Calendar;
import java.util.Date;

public class VoteRecord {

  private final Profile profile;
  private final String vote;
  private final int electionID;
  private final Date date;

  public VoteRecord(Profile profile, String vote, int electionID) {
    this(profile, Calendar.getInstance().getTime(), vote, electionID);
  }

  public VoteRecord(Profile profile, Date date, String vote, int electionID) {
    this.profile = profile;
    this.vote = vote;
    this.date = date;
    this.electionID = electionID;
  }

  public Profile getProfile() {
    return profile;
  }

  public String getVote() {
    return vote;
  }

  public Date getDate() {
    return date;
  }

  public int getElectionID() {
    return electionID;
  }

}
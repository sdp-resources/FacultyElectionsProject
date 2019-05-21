package fsc.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class VoteRecord {

  private final Profile profile;
  private final String vote;
  private final int electionID;
  private Date date;

  public VoteRecord(Profile profile, String vote, int electionID) {
    this(profile, Calendar.getInstance().getTime(), vote, electionID);
  }

  public VoteRecord(Profile profile, Date date, String vote, int electionID){
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

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    VoteRecord that = (VoteRecord) o;
    return electionID == that.electionID && Objects.equals(profile, that.profile) && Objects.equals(
          vote, that.vote) && Objects.equals(date, that.date);
  }

  public int hashCode() {
    return Objects.hash(profile, vote, electionID, date);
  }
}
package fsc.entity;

import java.util.Calendar;
import java.util.Date;

public class VoteRecorder {

  private final Profile profile;
  private final Vote vote;
  private Date date;

  public VoteRecorder(Profile profile, Vote vote) {
    this(profile, Calendar.getInstance().getTime(), vote);
  }

  public VoteRecorder(Profile profile, Date date, Vote vote){
    this.profile = profile;
    this.vote = vote;
    this.date = date;
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
}
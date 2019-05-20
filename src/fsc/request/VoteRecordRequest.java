package fsc.request;

import fsc.entity.Profile;
import fsc.entity.Vote;

import java.util.Date;

public class VoteRecordRequest {
  public final String username;
  public final Date date;
  public final Vote vote;
  public final int electionID;

  public VoteRecordRequest(
        String username, Date date, Vote vote, int electionID) {
    this.username = username;
    this.date = date;
    this.vote = vote;
    this.electionID = electionID;
  }
}

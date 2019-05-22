package fsc.request;

import java.util.Date;

public class VoteRecordRequest extends Request {
  public final String username;
  public final Date date;
  public final String vote;
  public final int electionID;

  public VoteRecordRequest(
        String username, Date date, String vote, int electionID) {
    this.username = username;
    this.date = date;
    this.vote = vote;
    this.electionID = electionID;
  }
}

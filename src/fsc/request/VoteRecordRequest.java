package fsc.request;

import java.util.List;

public class VoteRecordRequest extends Request {
  public final String username;
  public final List<String> vote;
  public final String electionID;

  public VoteRecordRequest(String username, List<String> vote, String electionID) {
    this.username = username;
    this.vote = vote;
    this.electionID = electionID;
  }
}

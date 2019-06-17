package fsc.request;

import java.util.List;

public class SubmitVoteRecordRequest extends Request {
  public final String username;
  public final List<String> vote;
  public final String electionID;

  public SubmitVoteRecordRequest(String username, List<String> vote, String electionID) {
    this.username = username;
    this.vote = vote;
    this.electionID = electionID;
  }
}

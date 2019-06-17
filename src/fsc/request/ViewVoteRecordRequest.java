package fsc.request;

public class ViewVoteRecordRequest extends Request {
  public final String username;
  public final String electionID;

  public ViewVoteRecordRequest(String username, String electionID) {
    this.username = username;
    this.electionID = electionID;
  }
}

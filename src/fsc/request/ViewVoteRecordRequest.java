package fsc.request;

public class ViewVoteRecordRequest extends Request {
  public final long recordId;
  public final String electionID;

  public ViewVoteRecordRequest(long recordId, String electionID) {
    this.recordId = recordId;
    this.electionID = electionID;
  }
}

package fsc.request;

public class ViewAllVotesRequest extends Request {
  public final long electionID;

  public ViewAllVotesRequest(long electionID) {
    this.electionID = electionID;
  }
}

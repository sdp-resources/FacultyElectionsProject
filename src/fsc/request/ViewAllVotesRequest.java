package fsc.request;

public class ViewAllVotesRequest extends Request {
  public final String electionID;

  public ViewAllVotesRequest(String electionID) {
    this.electionID = electionID;
  }
}

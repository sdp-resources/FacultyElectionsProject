package fsc.request;

public class ViewAllVotesRequest {
  public final String electionID;

  public ViewAllVotesRequest(String electionID) {
    this.electionID = electionID;
  }
}

package fsc.request;

public class ViewElectionRequest extends Request {
  public final String electionID;

  public ViewElectionRequest(String electionId) {
    this.electionID = electionId;
  }
}

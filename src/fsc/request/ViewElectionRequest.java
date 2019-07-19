package fsc.request;

public class ViewElectionRequest extends Request {
  public final Long electionID;

  public ViewElectionRequest(Long electionId) {
    this.electionID = electionId;
  }
}

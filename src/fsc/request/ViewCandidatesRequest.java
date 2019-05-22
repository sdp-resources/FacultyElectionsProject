package fsc.request;

public class ViewCandidatesRequest extends Request {
  public String electionID;

  public ViewCandidatesRequest(String electionID) {
    this.electionID = electionID;
  }
}

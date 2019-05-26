package fsc.request;

public class ViewDTSRequest extends Request {
  public final String username;
  public final String electionID;

  public ViewDTSRequest(String username, String electionID) {
    this.username = username;
    this.electionID = electionID;
  }
}

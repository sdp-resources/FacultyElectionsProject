package fsc.request;

public class ViewDTSRequest extends Request {
  public String username;
  public String electionID;

  public ViewDTSRequest(String username, String electionID){
    this.username = username;
    this.electionID = electionID;
  }
}

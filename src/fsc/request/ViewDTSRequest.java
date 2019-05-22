package fsc.request;

public class ViewDTSRequest extends Request {
  public String username;
  public Object electionID;

  public ViewDTSRequest(String username, Object electionID){
    this.username = username;
    this.electionID = electionID;
  }
}

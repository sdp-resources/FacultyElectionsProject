package fsc.request;

public class viewDTSRequest extends Request {
  public String username;
  public Object electionID;

  public viewDTSRequest(String username, Object electionID){
    this.username = username;
    this.electionID = electionID;
  }
}

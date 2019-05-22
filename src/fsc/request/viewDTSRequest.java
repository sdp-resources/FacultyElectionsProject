package fsc.request;

public class viewDTSRequest {
  public String username;
  public Integer electionID;

  public viewDTSRequest(String username, Integer electionID){
    this.username = username;
    this.electionID = electionID;
  }
}

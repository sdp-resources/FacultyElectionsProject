package fsc.request;

public class AddToBallotRequest extends Request {
  public final String username;
  public final String electionID;

  public AddToBallotRequest(String electionID, String username) {
    this.electionID = electionID;
    this.username = username;
  }

}

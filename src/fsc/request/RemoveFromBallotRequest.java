package fsc.request;

public class RemoveFromBallotRequest extends Request {
  public final String username;
  public final String electionID;

  public RemoveFromBallotRequest(String electionID, String username) {
    this.electionID = electionID;
    this.username = username;
  }
}

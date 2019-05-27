package fsc.request;

public class RemoveFromBallotRequest extends Request {
  public final String username;
  public final String ballotID;

  public RemoveFromBallotRequest(String ballotID, String username) {
    this.ballotID = ballotID;
    this.username = username;
  }
}

package fsc.request;

public class RemoveFromBallotRequest extends Request {
  public final String username;
  public final Long electionID;

  public RemoveFromBallotRequest(Long electionID, String username) {
    this.electionID = electionID;
    this.username = username;
  }
}

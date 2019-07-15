package fsc.request;

public class EditElectionStateRequest extends Request {
  public final String electionID;
  public final String state;

  public EditElectionStateRequest(String electionId, String newState) {
    this.electionID = electionId;
    this.state = newState;
  }
}

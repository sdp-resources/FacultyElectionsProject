package fsc.request;

public class EditElectionStateRequest extends Request {
  public final Long electionID;
  public final String state;

  public EditElectionStateRequest(Long electionId, String newState) {
    this.electionID = electionId;
    this.state = newState;
  }
}

package fsc.request;

import fsc.interactor.Interactor;

public class EditElectionStateRequest extends Request {
  public final Long electionID;
  public final String state;

  public EditElectionStateRequest(Long electionId, String newState) {
    this.electionID = electionId;
    this.state = newState;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

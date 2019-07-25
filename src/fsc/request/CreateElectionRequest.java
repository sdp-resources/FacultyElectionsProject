package fsc.request;

import fsc.interactor.Interactor;

public class CreateElectionRequest extends Request {
  public final String seatName;
  public final String committeeName;

  public CreateElectionRequest(String seat, String committee) {
    this.seatName = seat;
    this.committeeName = committee;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

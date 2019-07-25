package fsc.request;

import fsc.interactor.Interactor;

public class ViewElectionRequest extends Request {
  public final Long electionID;

  public ViewElectionRequest(Long electionId) {
    this.electionID = electionId;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

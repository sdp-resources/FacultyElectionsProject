package fsc.request;

import fsc.entity.query.Query;
import fsc.interactor.Interactor;

public class EditBallotQueryRequest extends Request {
  public final long electionID;
  public final Query query;

  public EditBallotQueryRequest(long electionId, Query query) {
    this.electionID = electionId;
    this.query = query;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

package fsc.request;

import fsc.entity.query.Query;

public class EditBallotQueryRequest extends Request {
  public final long electionID;
  public final Query query;

  public EditBallotQueryRequest(long electionId, Query query) {
    this.electionID = electionId;
    this.query = query;
  }
}

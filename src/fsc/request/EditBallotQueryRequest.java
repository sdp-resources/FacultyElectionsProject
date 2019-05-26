package fsc.request;

import fsc.entity.query.Query;

public class EditBallotQueryRequest extends Request {
  public final String electionID;
  public final Query query;

  public EditBallotQueryRequest(String electionId, Query query) {
    this.electionID = electionId;
    this.query = query;
  }
}

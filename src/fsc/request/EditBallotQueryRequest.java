package fsc.request;

import fsc.entity.query.Query;

public class EditBallotQueryRequest extends Request{
  public String electionID;
  public Query query;

  public  EditBallotQueryRequest( String electionId, Query query){
    this.electionID = electionId;
    this.query = query;
  }
}

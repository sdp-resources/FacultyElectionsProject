package fsc.request;

public class GetElectionResultsRequest extends Request {
  public long electionID;

  public GetElectionResultsRequest(long electionID) {
    super();
    this.electionID = electionID;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

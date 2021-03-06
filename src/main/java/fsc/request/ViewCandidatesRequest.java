package fsc.request;

public class ViewCandidatesRequest extends Request {
  public final Long electionID;

  public ViewCandidatesRequest(Long electionID) {
    this.electionID = electionID;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

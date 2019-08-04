package fsc.request;

public class ViewVoterRequest extends Request {
  public final Long electionID;
  public final String username;

  public ViewVoterRequest(Long electionID, String username) {
    this.electionID = electionID;
    this.username = username;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

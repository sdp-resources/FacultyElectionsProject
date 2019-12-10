package fsc.request;

public class AddToBallotRequest extends Request {
  public final String username;
  public final Long electionID;

  public AddToBallotRequest(Long electionID, String username) {
    this.electionID = electionID;
    this.username = username;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

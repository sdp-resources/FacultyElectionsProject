package fsc.request;

public class AddVoterRequest extends Request {
  public final String username;
  public final Long electionId;

  public AddVoterRequest(Long electionId, String username) {
    super();
    this.username = username;
    this.electionId = electionId;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

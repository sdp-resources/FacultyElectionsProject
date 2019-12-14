package fsc.request;

public class RemoveVoterRequest extends Request {
  public final Long electionId;
  public final String username;

  public RemoveVoterRequest(Long electionid, String username) {super();
    this.electionId = electionid;
    this.username = username;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

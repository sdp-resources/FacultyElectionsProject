package fsc.request;

public class SetDTSRequest extends Request {
  public final Long electionID;
  public final String username;
  public final String status;

  public SetDTSRequest(Long electionID, String userName, String status) {
    this.electionID = electionID;
    this.status = status;
    this.username = userName;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

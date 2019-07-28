package fsc.request;

public class ViewDTSRequest extends Request {
  public final String username;
  public final Long electionID;

  public ViewDTSRequest(String username, Long electionID) {
    this.username = username;
    this.electionID = electionID;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

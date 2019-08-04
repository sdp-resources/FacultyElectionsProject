package fsc.request;

import fsc.entity.Candidate;

public class SetDTSRequest extends Request {
  public final Long electionID;
  public final String username;
  public final Candidate.Status status;

  public SetDTSRequest(Long electionID, String userName, Candidate.Status status) {
    this.electionID = electionID;
    this.status = status;
    this.username = userName;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

package fsc.request;

public class ViewVoteRecordRequest extends Request {
  public final long recordId;

  public ViewVoteRecordRequest(long recordId) {
    this.recordId = recordId;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

package fsc.request;

public class CreateElectionRequest extends Request {
  public long seatId;

  public CreateElectionRequest(long seatId) {
    this.seatId = seatId;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

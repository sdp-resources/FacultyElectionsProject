package fsc.request;

public class CreateSeatRequest extends Request {
  public final Long committeeId;
  public final String seatName;
  public final String queryString;

  public CreateSeatRequest(
        Long committeeId, String seatName, String queryString
  ) {
    this.committeeId = committeeId;
    this.seatName = seatName;
    this.queryString = queryString;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

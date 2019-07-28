package fsc.request;

public class CreateSeatRequest extends Request {
  public final String committeeName;
  public final String seatName;
  public final String queryString;

  public CreateSeatRequest(String committeeName, String seatName, String queryString) {
    this.committeeName = committeeName;
    this.seatName = seatName;
    this.queryString = queryString;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

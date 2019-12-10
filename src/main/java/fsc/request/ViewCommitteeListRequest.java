package fsc.request;

public class ViewCommitteeListRequest extends Request {
  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

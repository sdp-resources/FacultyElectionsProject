package fsc.request;

public class ViewNamedQueryListRequest extends Request {
  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

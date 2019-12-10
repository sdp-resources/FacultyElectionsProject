package fsc.request;

public class ViewActiveElectionsRequest extends Request {
  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

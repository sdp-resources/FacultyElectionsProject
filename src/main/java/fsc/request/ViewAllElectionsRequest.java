package fsc.request;

public class ViewAllElectionsRequest extends Request {
  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

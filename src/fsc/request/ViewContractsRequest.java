package fsc.request;

public class ViewContractsRequest extends Request {
  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

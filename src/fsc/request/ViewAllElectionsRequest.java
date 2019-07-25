package fsc.request;

import fsc.interactor.Interactor;

public class ViewAllElectionsRequest extends Request {
  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

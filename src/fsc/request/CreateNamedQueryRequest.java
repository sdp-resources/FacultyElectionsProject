package fsc.request;

import fsc.interactor.Interactor;

public class CreateNamedQueryRequest extends Request {
  public final String name;
  public final String queryString;

  public CreateNamedQueryRequest(String name, String queryString) {
    this.name = name;
    this.queryString = queryString;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

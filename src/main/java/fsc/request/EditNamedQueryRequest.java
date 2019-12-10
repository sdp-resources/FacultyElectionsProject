package fsc.request;

public class EditNamedQueryRequest extends Request {
  public final String name;
  public final String queryString;

  public EditNamedQueryRequest(String name, String queryString) {super();
    this.name = name;
    this.queryString = queryString;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

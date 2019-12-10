package fsc.request;

public class QueryValidationRequest extends Request {
  public final String queryString;

  public QueryValidationRequest(String queryString) {
    this.queryString = queryString;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

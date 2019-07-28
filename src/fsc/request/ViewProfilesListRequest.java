package fsc.request;

public class ViewProfilesListRequest extends Request {
  public final String query;

  public ViewProfilesListRequest(String query) {
    this.query = query;
  }

  public ViewProfilesListRequest() {
    this("all");
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

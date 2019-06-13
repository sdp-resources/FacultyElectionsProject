package fsc.request;

public class CreateNamedQueryRequest extends Request {
  public final String name;
  public final String queryString;

  public CreateNamedQueryRequest(String name, String queryString) {
    this.name = name;
    this.queryString = queryString;
  }
}

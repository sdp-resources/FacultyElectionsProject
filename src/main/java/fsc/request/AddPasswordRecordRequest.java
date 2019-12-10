package fsc.request;

public class AddPasswordRecordRequest extends Request {
  public final String username;
  public final String password;
  public final String role;

  public AddPasswordRecordRequest(String username, String password, String role) {
    super();
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

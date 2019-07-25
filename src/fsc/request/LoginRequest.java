package fsc.request;

import fsc.interactor.Interactor;

public class LoginRequest extends Request {
  public final String username;
  public final String password;

  public LoginRequest(String username, String password) {
    this.username = username;
    this.password = password;

  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

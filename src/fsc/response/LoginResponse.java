package fsc.response;

public class LoginResponse implements Response {
  public final String role;
  public final String token;

  public LoginResponse(String role, String token)
  {
    this.role = role;
    this.token = token;
  }
}

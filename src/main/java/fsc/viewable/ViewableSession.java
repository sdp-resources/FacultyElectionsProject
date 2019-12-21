package fsc.viewable;

public class ViewableSession {
  public final String username;
  public final String role;
  public final String token;
  public final long expires;

  public ViewableSession(String username, String role, String token, long expires) {
    this.username = username;
    this.role = role;
    this.token = token;
    this.expires = expires;
  }

  public String getUsername() {
    return username;
  }
}

package fsc.entity.session;

import java.util.Calendar;

public class AuthorizedSession implements Session {
  private final String role;
  private final String username;
  private final String token;
  private final Calendar expirationTime;

  public AuthorizedSession(String role, String username, String token, Calendar expirationTime) {
    this.role = role;
    this.username = username;
    this.token = token;
    this.expirationTime = expirationTime;
  }

  public String getToken() {
    return token;
  }

  public String getUsername() {
    return username;
  }

  public String getRole() {
    return role;
  }

  public Calendar getExpirationTime() { return expirationTime;}

  public boolean isAuthorized() {
    return true;
  }
}

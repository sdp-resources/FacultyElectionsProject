package fsc.entity.session;

import java.time.LocalDateTime;

public class AuthorizedSession implements Session {
  private final String role;
  private final String username;
  private final String token;
  private final LocalDateTime expirationTime;

  public AuthorizedSession(
        String role, String username, String token, LocalDateTime expirationTime
  ) {
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

  public LocalDateTime getExpirationTime() { return expirationTime; }

  public boolean isAuthorized() {
    return true;
  }
}

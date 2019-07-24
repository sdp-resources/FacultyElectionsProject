package fsc.entity.session;

import fsc.service.Authorizer;

import java.time.LocalDateTime;

public class AuthorizedSession implements Session {
  private final Authorizer.Role role;
  private final String username;
  private final String token;
  private final LocalDateTime expirationTime;

  public AuthorizedSession(
        Authorizer.Role role, String username, String token, LocalDateTime expirationTime
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

  public Authorizer.Role getRole() {
    return role;
  }

  public LocalDateTime getExpirationTime() { return expirationTime; }

  public boolean isAuthorized() {
    return true;
  }

  public boolean isAuthorizedForRole(Authorizer.Role requiredRole) {
    return role.equals(requiredRole);
  }
}

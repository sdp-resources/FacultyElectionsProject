package fsc.entity.session;

import fsc.service.Authorizer;

import java.time.LocalDateTime;
import java.util.Objects;

public class AuthenticatedSession implements Session {
  private Authorizer.Role role;
  private String username;
  private String token;

  private LocalDateTime expirationTime;

  public AuthenticatedSession() {}

  public AuthenticatedSession(
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

  public void setExpirationTime(LocalDateTime expirationTime) {
    this.expirationTime = expirationTime;
  }


  public boolean isAuthenticated() {
    return true;
  }

  public boolean isAuthorizedForRole(Authorizer.Role requiredRole) {
    return role.equals(requiredRole);
  }

  public boolean matchesUser(String username) {
    return this.username.equals(username);
  }

  public boolean hasExpired() {
    return getExpirationTime().isBefore(LocalDateTime.now());
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AuthenticatedSession that = (AuthenticatedSession) o;
    return role == that.role &&
                 username.equals(that.username) &&
                 token.equals(that.token) &&
                 expirationTime.equals(that.expirationTime);
  }

  public int hashCode() {
    return Objects.hash(role, username, token, expirationTime);
  }

  public String toString() {
    return "AuthenticatedSession{" +
                 "role=" + role +
                 ", username='" + username + '\'' +
                 ", token='" + token + '\'' +
                 ", expirationTime=" + expirationTime +
                 '}';
  }
}

package fsc.entity.session;

import fsc.MyTime;
import fsc.service.Authorizer;

import java.util.Objects;

public class AuthenticatedSession implements Session {
  private Authorizer.Role role;
  private String username;
  private String token;

  private long expirationTime;

  public AuthenticatedSession() {}

  public AuthenticatedSession(
        Authorizer.Role role, String username, String token, long expirationTime
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

  public long getExpirationTime() { return expirationTime; }

  public void setExpirationTime(long expirationTime) {
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

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AuthenticatedSession that = (AuthenticatedSession) o;
    return role == that.role &&
                 username.equals(that.username) &&
                 token.equals(that.token) &&
                 expirationTime == that.expirationTime;
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

  public void setStandardExpirationTime() {
    setExpirationTime(MyTime.standardExpirationTime());
  }

  public boolean hasExpired() {
    return MyTime.hasExpired(getExpirationTime());
  }
}

package fsc.entity;

import java.util.Calendar;

public class Session {
  private String role;
  private String username;
  private String token;
  private Calendar expirationTime;

  public Session(String role, String username, String token, Calendar expirationTime) {
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
}

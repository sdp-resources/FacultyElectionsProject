package fsc.entity;

import java.util.Calendar;

public class Authorized implements  Authorization
{
  public boolean isAuthorized() { return true; }
  public final String username;
  public final String token;
  public final String role;
  public final Calendar expirationTime;

  public Authorized(String role, String username, String token, Calendar expirationTime)
  {
    this.role = role;
    this.username = username;
    this.token = token;
    this.expirationTime = expirationTime;
  }
}

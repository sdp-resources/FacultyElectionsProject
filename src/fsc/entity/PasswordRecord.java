package fsc.entity;

import fsc.service.Authorizer;

public class PasswordRecord {
  private String username;
  private String password;
  private Authorizer.Role role;

  public PasswordRecord() { }

  public PasswordRecord(String username, String password, Authorizer.Role role) {

    this.username = username;
    this.password = password;
    this.role = role;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Authorizer.Role getRole() {
    return role;
  }

  public void setRole(Authorizer.Role role) {
    this.role = role;
  }
}

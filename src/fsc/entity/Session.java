package fsc.entity;

public class Session {
  private String role;
  private String username;
  private String token;

  public Session(String role, String username, String token) {
    this.role = role;
    this.username = username;
    this.token = token;
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
}
